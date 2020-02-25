package storage;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Storage {
	private Connection myConnection;
	private Statement myStatement;

	public Storage() {
		myConnection = Connector.getConnection();
		try {
			myStatement = myConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			System.out.println("Error: " + e.getErrorCode());
			System.out.println("Error: " + e.getMessage());
		}
	}

	public void opretKunde(String cprNummer, String navn, String adresse, int postNr) {
		try {
			myStatement.execute(
					"insert into kunde values ('" + cprNummer + "', '" + navn + "', '" + adresse + "', '" + postNr
							+ "');");
		} catch (SQLException e) {
			System.out.println("Error: " + e.getErrorCode());
			System.out.println("Error: " + e.getMessage());
		}
	}

	public String getBynavn(int postNr) {
		try {
			ResultSet res = myStatement.executeQuery("select bynavn from PostDistrikt"
					+ " where postNr = " + postNr + ";");
			res.next();
			return res.getString("bynavn");
		} catch (SQLException e) {
			return "";
		}
	}

	public boolean findesKunde(String cprNummer) {
		try {
			ResultSet res = myStatement.executeQuery("select cprNr from Kunde"
					+ " where cprNr = '" + cprNummer + "';");
			return res.next();
		} catch (SQLException e) {
			return false;
		}
	}
}
