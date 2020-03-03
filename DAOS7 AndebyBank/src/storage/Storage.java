package storage;

import java.sql.Statement;
import java.sql.Types;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Storage {

	public static void opretKunde(String cprNummer, String navn, String adresse, int postNr) {
		try (Connection myConnection = Connector.getConnection();
				Statement myStatement = myConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE)) {
			myStatement.execute(
					"insert into kunde values ('" + cprNummer + "', '" + navn + "', '" + adresse + "', '" + postNr
							+ "');");
		} catch (SQLException e) {
			System.out.println("Error: " + e.getErrorCode());
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void opretKonto(String cprNummer, int regNummer, long kontonummer, String kontotekst, double saldo,
			boolean løn, boolean prioritet, double låneret) {
		try (Connection myConnection = Connector.getConnection();
				Statement myStatement = myConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE)) {
			// opret konto
			myStatement.execute("insert into Konto values ("
					+ regNummer + ", " + kontonummer + ", '" + kontotekst + "', " + saldo
					+ ", " + 1.2 + ", " + 5.2 + ");");

			// opret kundeharkonto
			myStatement.execute(
					"insert into kundeharkonto values ('" + cprNummer + "', " + regNummer + ", " + kontonummer + ");");

			// opret lønkonto
			if (løn) {
				myStatement
						.execute("insert into løn values (" + regNummer + ", " + kontonummer + ", " + låneret + ");");
			}

			// opret prioritetskonto
			if (prioritet) {
				myStatement
						.execute("insert into prioritet values (" + regNummer + ", " + kontonummer + ", 0);");
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getErrorCode());
			System.out.println("Error: " + e.getMessage());
		}

	}

	public static String getBynavn(int postNr) {
		try (Connection myConnection = Connector.getConnection();
				Statement myStatement = myConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE)) {
			ResultSet res = myStatement.executeQuery("select bynavn from PostDistrikt"
					+ " where postNr = " + postNr + ";");
			res.next();
			return res.getString("bynavn");
		} catch (SQLException e) {
			return "";
		}
	}

	public static boolean findesKunde(String cprNummer) {
		try (Connection myConnection = Connector.getConnection();
				Statement myStatement = myConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE)) {
			ResultSet res = myStatement.executeQuery("select cprNr from Kunde"
					+ " where cprNr = '" + cprNummer + "';");
			return res.next();
		} catch (SQLException e) {
			return false;
		}
	}

	public static boolean findesAfdelig(int regNummer) {
		try (Connection myConnection = Connector.getConnection();
				Statement myStatement = myConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE)) {
			ResultSet res = myStatement.executeQuery("select regNr from Afdeling"
					+ " where regNr = " + regNummer + ";");
			return res.next();
		} catch (SQLException e) {
			return false;
		}
	}

	public static boolean findesKontoIAfdeling(int regNummer, long kontonummer) {
		try (Connection myConnection = Connector.getConnection();
				Statement myStatement = myConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE)) {
			ResultSet res = myStatement.executeQuery("select ktoNr from Afdeling"
					+ " where regNr = " + regNummer + ";");
			return res.next();
		} catch (SQLException e) {
			return false;
		}
	}

	public static void sletKunde(String cprNummer) {
		try (Connection myConnection = Connector.getConnection();
				Statement myStatement = myConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE)) {
			myStatement.execute(
					"delete from kunde where cprNr = '" + cprNummer + "';");
		} catch (SQLException e) {
			System.out.println("Error: " + e.getErrorCode());
			System.out.println("Error: " + e.getMessage());
		}

	}

	public static void visTransaktioner(ListView<String> lvw) {
		try (Connection myConnection = Connector.getConnection()) {
			lvw.getItems().clear();
			String proc = "{call sp_SamletSaldo()}";
			CallableStatement cb = myConnection.prepareCall(proc);
			ResultSet rs = cb.executeQuery();
			String titler = String.format("%1$10s %2$20s %3$50s", "Reg.", "Konto", "Transaktionsbeløb");
			lvw.getItems().add(titler);
			while (rs.next()) {
				String regNr = rs.getString("regNr");
				String ktoNr = rs.getString("ktoNr");
				String samletTransaktioner = String.format("%.2f", rs.getDouble(3));
				String data = String.format("%1$10s %2$20s %3$50s kr", regNr, ktoNr, samletTransaktioner);
				lvw.getItems().add(data);
			}

		} catch (SQLException e) {

		}
	}

	public static void visAfdelinger(ListView<String> lvw) {
		try (Connection myConnection = Connector.getConnection();
				Statement myStatement = myConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE)) {
			lvw.getItems().clear();
			ResultSet rs = myStatement.executeQuery(
					"select mv.afdelingsnavn, mv.navn, mv.titel, mv.adresse, mv.postNr, mv.bynavn from viewAfdelingsOplysninger as mv join Afdeling A on A.postNr = mv.postNr order by A.regNr;");
			String titler = String.format("%1$-50.50s %2$-50.50s %3$-50.50s %4$-50.50s %5$-50.50s %6$-50.50s",
					"Afdelingsnavn", "Navn",
					"Titel",
					"Adresse", "Postnr.", "Bynavn");
			lvw.getItems().add(titler);
			while (rs.next()) {
				String afd = rs.getString("afdelingsnavn");
				String navn = rs.getString("navn");
				String titel = rs.getString("titel");
				String adresse = rs.getString("adresse");
				String postnr = Integer.toString(rs.getInt("postNr"));
				String bynavn = rs.getString("bynavn");
				String data = String.format("%1$-50.50s %2$-50.50s %3$-50.50s %4$-50.50s %5$-50.50s %6$-50.50s", afd,
						navn,
						titel,
						adresse, postnr, bynavn);
				lvw.getItems().add(data);
			}
		} catch (Exception e) {
		}

	}

	public static void visKonti(ListView<String> lvw) {
		try (Connection myConnection = Connector.getConnection()) {
			lvw.getItems().clear();
			String proc = "{call sp_KontoPrAfdeling(?)}";
			CallableStatement cb = myConnection.prepareCall(proc);
			cb.registerOutParameter(1, Types.INTEGER);
			ResultSet rs = cb.executeQuery();
			String titler = String.format("%1$10s %2$50s %3$30s", "Reg.", "Navn", "Antal konti");
			lvw.getItems().add(titler);
			while (rs.next()) {
				String regNr = rs.getString("regNr");
				String ktoNr = rs.getString("navn");
				String samletTransaktioner = Integer.toString(rs.getInt(3));
				String data = String.format("%1$10s %2$50s %3$30s", regNr, ktoNr, samletTransaktioner);
				lvw.getItems().add(data);
			}

		} catch (SQLException e) {

		}
	}
}
