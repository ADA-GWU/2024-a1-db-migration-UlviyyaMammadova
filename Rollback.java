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
            // Drop the INTERESTS table
            statement.executeUpdate("DROP TABLE IF EXISTS INTERESTS");
    
            // Recreate the INTERESTS table with original structure
            statement.executeUpdate("CREATE TABLE INTERESTS (STUDENT_ID INTEGER, INTEREST VARCHAR(20))");
    
            // Reinsert data in original format
            statement.executeUpdate("INSERT INTO INTERESTS (STUDENT_ID, INTEREST) VALUES " +
                    "(1, 'Tennis'), (1, 'Literature'), (1, 'Chemistry'), " +
                    "(2, 'Math'), (2, 'Tennis'), (2, 'Football'), " +
                    "(3, 'Math'), (3, 'Music'), (3, 'Chess')");
        }
    }
    
}
