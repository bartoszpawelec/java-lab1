package apps.table.dbTable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateTable {
	
	private static Connection con;
	private static Statement stmt;
	
	static String CreatePeople = "CREATE TABLE IF NOT EXISTS people " 	+
			"(id INTEGER PRIMARY KEY AUTOINCREMENT					,"  +
            " name			      	CHAR(50)    				,"  +
            " surname        		CHAR(50)					,"  +
            " email    	    		CHAR(50)					,"  +
            " project				INT					)";
	
	static String CreateTasks = "CREATE TABLE IF NOT EXISTS tasks " 		+
            "(id INTEGER PRIMARY KEY AUTOINCREMENT					,"  +
            " name			      	CHAR(50)    				,"  +
            " deadline        		CHAR(50)					,"  +
            " executor				INT							,"	+ 
            " project				INT					)";
	
	static String CreateProjects = "CREATE TABLE IF NOT EXISTS projects " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT					,"  +
            " name			      	CHAR(50)    		)";
	
	
	public static void create() {
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:SQLitePM.db");
			System.out.println("Opened database successfully");

			stmt = con.createStatement();
			stmt.executeUpdate(CreatePeople);
			stmt.executeUpdate(CreateTasks);
			stmt.executeUpdate(CreateProjects);

			con.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		
		}
		System.out.println("Table created successfully");
	}
}
