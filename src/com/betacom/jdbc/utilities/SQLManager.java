package com.betacom.jdbc.utilities;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.betacom.jdbc.process.exceptions.AcademyException;
import com.betacom.jdbc.singletone.SQLConfiguration;

public class SQLManager {

	public Connection getConection()  throws AcademyException{
		Connection con = null;
		try {
			Class.forName(SQLConfiguration.getInstance().getProperty("driver"));  // Load driver with reflection
			/*
			 * open connection with DriverManager.getConnection
			 * prameters (url 
			 * 		, user,
			 * 		 pwd)
			 */
			con = DriverManager.getConnection(
					SQLConfiguration.getInstance().getProperty("url"),  // db url
					SQLConfiguration.getInstance().getProperty("user"), // user
					SQLConfiguration.getInstance().getProperty("pwd")   // pwd
					);
		
			System.out.println("connection with db for ProcessQuery");
			con.setAutoCommit(true);  // commit automatico
		} catch (Exception e) {
			throw new AcademyException(e.getMessage());
		} 
		
		return con;

	}
	public Connection getConection(boolean autoCommit)  throws AcademyException{
		Connection con = null;
		try {
			Class.forName(SQLConfiguration.getInstance().getProperty("driver"));  // Load driver with reflection
			/*
			 * open connection with DriverManager.getConnection
			 * prameters (url 
			 * 		, user,
			 * 		 pwd)
			 */
			con = DriverManager.getConnection(
					SQLConfiguration.getInstance().getProperty("url"),  // db url
					SQLConfiguration.getInstance().getProperty("user"), // user
					SQLConfiguration.getInstance().getProperty("pwd")   // pwd
					);
		
			System.out.println("connection with db for ProcessQuery");
		
			con.setAutoCommit(autoCommit);   // true -> commit automatico false -> commit in caso di commit rollback
		} catch (Exception e) {
			throw new AcademyException(e.getMessage());
		} 
		
		return con;

	}
	
	/*
	 * commit all sql statements
	 */
	public void commmit() throws AcademyException{
		try {
			SQLConfiguration.getInstance().getConnection().commit();
		} catch (SQLException e) {
			throw new AcademyException(e.getMessage());
		} 
	}
	
	/*
	 * rollback all sql statements
	 */
	public void rollBack() throws AcademyException{
		try {
			SQLConfiguration.getInstance().getConnection().rollback();
		} catch (SQLException e) {
			throw new AcademyException(e.getMessage());
		} 	
	}
	/*
	 * table list
	 */
	public List<String> listOfTable(String dbName) throws AcademyException{
		List<String> lT = new ArrayList<String>();
		try {
			DatabaseMetaData dbMD = SQLConfiguration.getInstance().getConnection().getMetaData();  // retrieve connection metadata
			
			ResultSet res = dbMD.getTables(dbName, null, null, null); // retrieve tables meta data
			/*
			 * resultset : return of all jdbc operations
			 * 			map by row
			 * 				map by column
			 * 		
			 */
			
			
			while (res.next()) {        // rs.next -> legge la prima riga
				lT.add(res.getString("TABLE_NAME"));  // rs.get... legge parametro della riga.
			}
			
			
		} catch (SQLException e) {
			throw new AcademyException(e.getMessage()); // change exception type
		}
		return lT;
	}
	
	/*
	 * execute select in JDBC with parameters
	 */
	public List<Map<String, Object>> list( String qry, Map<Integer, Object> params) 
			throws AcademyException{
		try {
			PreparedStatement cmd = SQLConfiguration.getInstance().getConnection().prepareStatement(qry); // statement compilation
			cmd = createSet(cmd, params);
			
			ResultSet res = cmd.executeQuery();  // execute query
			return resulSetToList(res);  
		} catch (Exception e) {
			throw new AcademyException(e.getMessage());
		}
	}
	/*
	 * execute select in JDBC without parameters
	 */
	public List<Map<String, Object>> list(String qry) 
			throws AcademyException{
		try {
			PreparedStatement cmd = SQLConfiguration.getInstance().getConnection().prepareStatement(qry); // statement compilation
			
			ResultSet res = cmd.executeQuery();  // execute query
			return resulSetToList(res);  
		} catch (Exception e) {
			throw new AcademyException(e.getMessage());
		}
	}

	/*
	 * execute select with result single row in JDBC with parameters
	 */
	public Map<String, Object> get( String qry, Map<Integer, Object> params) 
			throws AcademyException{
		try {
			PreparedStatement cmd = SQLConfiguration.getInstance().getConnection().prepareStatement(qry); // statement compilation
			cmd = createSet(cmd, params);
			
			ResultSet res = cmd.executeQuery();  // execute query

			return resulSetToMap(res);  
		} catch (Exception e) {
			throw new AcademyException(e.getMessage());
		}
	}

	/*
	 * count function
	 */
	public long count(String qry, Map<Integer, Object> params) 
			throws AcademyException {
		try {
			String qrCount = "Select count(*) as numero from ( " + qry + " ) as numero";
			PreparedStatement cmd = SQLConfiguration.getInstance().getConnection().prepareStatement(qrCount); // statement compilation
			cmd = createSet(cmd, params);
			
			ResultSet res = cmd.executeQuery();  // execute query
			res.next();   // read return value
			return (Long)res.getObject("numero");
			
		} catch (Exception e) {
			throw new AcademyException(e.getMessage());
		}
	}

	/*
	 * update function 
	 * 
	 * this function is used for insert
	 * 							 update
	 * 							 delete
	 */
	public int update (String qry, Map<Integer, Object> params) 
			throws AcademyException {
		int rc = 0;
		try {
			PreparedStatement cmd = SQLConfiguration.getInstance().getConnection().prepareStatement(qry); // statement compilation
			cmd = createSet(cmd, params);
	
			rc = cmd.executeUpdate();  // execute update operation 
			                           // rc : rows number implicated..
			
		} catch (Exception e) {
			throw new AcademyException(e.getMessage());
		}
		return rc;
		
	}

	public int update (String qry) 
			throws AcademyException {
		int rc = 0;
		try {
			PreparedStatement cmd = SQLConfiguration.getInstance().getConnection().prepareStatement(qry); // statement compilation
	
			rc = cmd.executeUpdate();  // execute update operation 
			                           // rc : rows number implicated..
			
		} catch (Exception e) {
			throw new AcademyException(e.getMessage());
		}
		return rc;
		
	}

	
	/*
	 * Insert parameters in PreparedStatement Object
	 */
	private PreparedStatement createSet(PreparedStatement cmd, Map<Integer, Object> params) {
		params.entrySet().forEach(s -> {		
			try {
				cmd.setObject(s.getKey(), s.getValue());  // store parameter in PreparedStatement
														  // parameters are store as generic objects		
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		return cmd;
	}
	/*
	 * transform resultSet in List<map>
	 * 				every maps are the row representation
	 * 							key = column name
	 * 							value = column value
	 */
	private List<Map<String,Object>> resulSetToList(ResultSet rs) throws SQLException{
		ResultSetMetaData md = rs.getMetaData();    // retrieve metadato resultSet
		int columns = md.getColumnCount();          // retrieve Query column number
		
		List<Map<String,Object>> rows = new ArrayList<Map<String,Object>>(); // init result
		
		while (rs.next()) {   // read first row
			Map<String,Object> row = new HashMap<String, Object>(); // init map (row)
			for (int i=1; i<= columns; ++i) {   // i begin by 1 because SQL begin by 1
				row.put(md.getColumnName(i), rs.getObject(i));  // load map with every column informations (key, value)
			}
			rows.add(row);
		}
		
		return rows;
	}
	/*
	 * Transform resultset in MAP
	 */
	private Map<String,Object> resulSetToMap(ResultSet rs) throws SQLException{
		ResultSetMetaData md = rs.getMetaData();    // retrieve metadato resultSet
		int columns = md.getColumnCount();          // retrieve Query column number
		rs.next();                                  // call first record

		Map<String,Object> row = new HashMap<String, Object>(); // init map (row)
		for (int i=1; i<= columns; ++i) {   // i begin by 1 because SQL begin by 1
			row.put(md.getColumnName(i), rs.getObject(i));  // load map with every column informations (key, value)
		}
		return row;
	}
	
}
