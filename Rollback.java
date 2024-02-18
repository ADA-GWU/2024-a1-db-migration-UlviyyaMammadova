import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Rollback {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/hw1_students";
        String username = "postgres";
        String password = "12345";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            rollbackMigration(connection);
            System.out.println("Rollback completed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void rollbackMigration(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("ALTER TABLE STUDENTS RENAME COLUMN STUDENT_ID TO ST_ID");
            statement.executeUpdate("ALTER TABLE STUDENTS ALTER COLUMN ST_NAME TYPE VARCHAR(20)");
            statement.executeUpdate("ALTER TABLE STUDENTS ALTER COLUMN ST_LAST TYPE VARCHAR(20)");
            statement.executeUpdate("ALTER TABLE INTERESTS RENAME COLUMN INTERESTS TO INTEREST");
            statement.executeUpdate("ALTER TABLE INTERESTS ALTER COLUMN INTEREST TYPE VARCHAR(255)");
        }
    }
}
