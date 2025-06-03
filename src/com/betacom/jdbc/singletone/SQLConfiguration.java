package com.betacom.jdbc.singletone;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.betacom.jdbc.process.exceptions.AcademyException;
import com.betacom.jdbc.utilities.SQLManager;

public class SQLConfiguration {
	private static SQLConfiguration instance = null;
	private static Properties prop = new Properties();  // define properties
	private static Properties queries = new Properties();  // define properties
	
	private Connection con = null;
	private SQLConfiguration() {		
	}
	public static SQLConfiguration getInstance() throws AcademyException {
		if (instance == null) {
			instance = new SQLConfiguration();
			loadConfiguration();
		}
		return instance;
	}

	private static void loadConfiguration() throws AcademyException  {
		
		try {
			InputStream input = new FileInputStream("./sql.properties"); // read properties files
			prop.load(input);   // load into object properties
			
			InputStream sql = new FileInputStream("./queries.properties"); // read queries files
			queries.load(sql);
			
		} catch (FileNotFoundException e) {
			System.out.println("SQL properties not found");
			throw new AcademyException();
		} catch (IOException e) {
			System.out.println("Error loading  properties");
			e.printStackTrace();
			throw new AcademyException();
		} 
	}
	
	public String getProperty(String p) {
		return prop.getProperty(p);       // read specific property
	}

	public String getQuery(String p) {
		return queries.getProperty(p).trim();       // read specific query
	}

	public void setTransaction() throws SQLException {
		con.setAutoCommit(false);
	}

	public void setAutoCommit() throws SQLException {
		con.setAutoCommit(true);
	}

	public Connection getConnection() throws AcademyException {
		if (con == null) {
			con = new SQLManager().getConection();
		}
		return con;
	}
	public Connection getConnection(boolean autoCommit) throws AcademyException {
		if (con == null) {
			con = new SQLManager().getConection(autoCommit);
		}
		return con;
	}

}
