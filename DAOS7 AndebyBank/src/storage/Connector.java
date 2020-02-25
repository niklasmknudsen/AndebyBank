package storage;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class Connector {
	private static String getUser() throws Exception {
		File file = new File("auth.xml");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);
		return document.getElementsByTagName("user").item(0).getTextContent();
	}

	private static String getPass() throws Exception {
		File file = new File("auth.xml");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);
		return document.getElementsByTagName("password").item(0).getTextContent();
	}

	private static String getAddr() throws Exception {
		File file = new File("auth.xml");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);
		return document.getElementsByTagName("address").item(0).getTextContent();
	}

	private static Connection getConnection() {
		Connection c = null;
		try {
			c = DriverManager.getConnection("jdbc:sqlserver://" + getAddr() + ";databaseName=AndeByBank;user="
					+ getUser() + ";password=" + getPass() + ";");
		} catch (Exception e) {
			System.out.println("fejl:  " + e.getMessage());
		}
		return c;
	}
}
