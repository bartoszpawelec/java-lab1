package apps.table.dbTable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateTable {
	
	private static Connection connection;
	private static Statement statement;
	
	static String CreatePeople = "CREATE TABLE IF NOT EXISTS people " 	+
			"(id INTEGER PRIMARY KEY AUTOINCREMENT					,"  +
            " name			      	CHAR(50)    				,"  +
            " surname        		CHAR(50)					,"  +
            " email    	    		CHAR(50)					,"  +
            " project				INT					)";
	
	static String CreateTasks = "CREATE TABLE IF NOT EXISTS tasks " 		+
            "(id INTEGER PRIMARY KEY AUTOINCREMENT					,"  +
            " name			      	CHAR(50)    				,"  +
            " due       		    DATE				    ,"  +
            " executor				INT							,"	+ 
            " project				INT					)";
	
	static String CreateProjects = "CREATE TABLE IF NOT EXISTS projects " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT					,"  +
            " name			      	CHAR(50)    		," +
			" projectTasks          INT               ," +
			" executor              INT                )";

	
	
	public static void create() {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:ProjectManager.db");
			System.out.println("Opened database successfully");

			statement = connection.createStatement();
			statement.executeUpdate(CreatePeople);
			statement.executeUpdate(CreateTasks);
			statement.executeUpdate(CreateProjects);

			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		
		}
		System.out.println("Table created successfully");
	}
}
