package apps.table.dbTable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PersonDb {

    static Connection connection;
    static Statement statement;

    public static boolean insertPerson(String name, String surname, String email, int project) {
        try {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:ProjectManager.db");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            statement = connection.createStatement();
            String sql = "insert into people (name, surname, email, project) values ('" + name + "', '" + surname + "','"
                    + email + "', " + project + ")";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean modifyPerson(String name, String surname, String email, int id_person) {
        try {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:ProjectManager.db");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            statement = connection.createStatement();
            String sql = "update people set name='name', surname='surname', email='email' where id='id_person'";
            statement.executeUpdate(sql);
            } catch(SQLException e) {
             e.printStackTrace();
             return false;
        }
            return true;
    }

    public static boolean deletePerson(int id_person) {
        try {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:ProjectManager.db");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            statement = connection.createStatement();
            String sql = "delete from people where id='id_person'";
            statement.executeUpdate(sql);
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
