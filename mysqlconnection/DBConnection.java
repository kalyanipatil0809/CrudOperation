package in.sts.excelutility.mysqlconnection;

import org.apache.log4j.Logger;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	final static Logger log = Logger.getLogger(DBConnection.class);
	private static final String url = "jdbc:mysql://localhost:3306/unique_data";
	private static final String userName = "root";
	private static final String Pass = "root";
	private static Connection connection;
	public static Connection connect()  {
		try {
			connection = DriverManager.getConnection(url, userName, Pass);
		}
		catch (SQLException exception) {
			log.error("Cannot create database connection");
			exception.printStackTrace();
		}
		return connection;
	}

}