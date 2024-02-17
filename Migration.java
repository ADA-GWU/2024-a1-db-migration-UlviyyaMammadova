import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Migration {
    public static void main(String[] args) {
        // Database connection parameters
        String url = "jdbc:postgresql://localhost:5432/hw1_students";
        String username = "postgres";
        String password = "12345";

        try {
            // Connect to the database
            Connection connection = DriverManager.getConnection(url, username, password);

            // Step 1: Rename the STUDENTS.ST_ID to STUDENTS.STUDENT_ID
            String renameQuery = "ALTER TABLE STUDENTS RENAME COLUMN ST_ID TO STUDENT_ID";
            PreparedStatement renameStatement = connection.prepareStatement(renameQuery);
            renameStatement.executeUpdate();

            // Step 2: Change the length of STUDENTS.ST_NAME and STUDENTS.ST_LAST from 20 to 30
            String alterQuery = "ALTER TABLE STUDENTS ALTER COLUMN ST_NAME TYPE VARCHAR(30), " +
                    "ALTER COLUMN ST_LAST TYPE VARCHAR(30)";
            PreparedStatement alterStatement = connection.prepareStatement(alterQuery);
            alterStatement.executeUpdate();

            // Step 3: Change the name of the INTERESTS.INTEREST to INTERESTS and its type to array of strings
            String arrayTypeQuery = "ALTER TABLE INTERESTS ALTER COLUMN INTEREST TYPE VARCHAR(30)[] USING INTEREST::VARCHAR(30)[]";
            PreparedStatement arrayTypeStatement = connection.prepareStatement(arrayTypeQuery);
            arrayTypeStatement.executeUpdate();

            // Step 4: Migrate the data in the INTERESTS table
            String migrationQuery = "UPDATE INTERESTS SET INTEREST = ARRAY[INTEREST]";
            PreparedStatement migrationStatement = connection.prepareStatement(migrationQuery);
            migrationStatement.executeUpdate();

            // Close resources
            renameStatement.close();
            alterStatement.close();
            arrayTypeStatement.close();
            migrationStatement.close();
            connection.close();

            System.out.println("Migration completed successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

