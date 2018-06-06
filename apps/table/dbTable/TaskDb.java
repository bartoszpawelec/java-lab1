package apps.table.dbTable;
import java.time.LocalDate;


import java.sql.*;

public class TaskDb {
    static Connection connection;
    static Statement statement;

    public static boolean insertTask(String name, int executor,LocalDate due, int project) {
        try {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:ProjectManager.db");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            String sql = "insert into tasks (name, executor, due, project) values ('" + name + "', '" + executor + "', '"+due+"', " + project +"')" ;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
