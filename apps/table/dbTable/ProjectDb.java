package apps.table.dbTable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ProjectDb {

    static Connection connection;
    static Statement statement;

    public static boolean insertProject(String name, int executor, int projectTasks) {
        try {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:ProjectManager.db");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            statement = connection.createStatement();
            String sql = "insert into projects (name, executor, projectTasks) values ('" + name + "', '" + executor + "', '"+ projectTasks+"')";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean modifyProject(int id_project, String name) {
        try {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:ProjectManager.db");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            statement = connection.createStatement();
            String sql = "update people set name='name' where id='id_project'";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteProject(int id_project) {
        try {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:ProjectManager.db");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            statement = connection.createStatement();
            String sql = "delete from projects where id='id_project'";
            statement.executeUpdate(sql);
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
