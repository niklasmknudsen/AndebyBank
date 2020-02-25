package E002;

import java.sql.*;
import storage.Connector;

public class Bank {
	public static void main(String[] args) {

		try {
			Connection myConnection = Connector.getConnection();
			Statement myStatement = myConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet res = myStatement.executeQuery("SELECT * FROM afdeling");
			while (res.next()) {
				String regNr = res.getString("regNr");
				String navn = res.getString("navn");
				String adresse = res.getString("adresse");
				String tlfNr = res.getString("tlfNr");
				System.out.println(regNr + " " + navn + ", " + adresse + ", " + tlfNr);
			}
		} catch (SQLException sqlEx) {
			System.out.println("Error: " + sqlEx.getErrorCode());
			System.out.println("Error: " + sqlEx.getMessage());
		}
	}
}
