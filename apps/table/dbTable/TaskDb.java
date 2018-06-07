package apps.table.dbTable;
import java.time.LocalDate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class TaskDb {
    static Connection connection;
    static Statement statement;

    public static boolean insertTask(String name, int executor, LocalDate deadline, int project) {
        try {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:ProjectManager.db");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            statement = connection.createStatement();
            String sql = "insert into tasks (name, executor, deadline, project) values ('" + name + "', '" + executor + "', '" + deadline + "', '" + project +"')" ;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean modifyTask(String name, LocalDate due, int executor, int id_task){
        try {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:ProjectManager.db");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            statement = connection.createStatement();
            String sql = "update tasks set name='"+name+"', due='"+due+"', executor="+executor+" where id="+id_task;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteTask(int id_task){
        try {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:ProjectManager.db");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            statement = connection.createStatement();
            String sql = "delete from tasks where id='id_task'";
            statement.executeUpdate(sql);
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
