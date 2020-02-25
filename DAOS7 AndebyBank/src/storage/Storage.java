package storage;

import java.sql.Statement;
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
}
