import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Migration {
    public static void main(String[] args) {
        // Database connection
        String url = "jdbc:postgresql://localhost:5432/hw1_students";
        String username = "postgres";
        String password = "12345";

        try {
            // Connect to the database
            Connection connection = DriverManager.getConnection(url, username, password);

            // Step 1: Drop the STUDENTS table if it exists
            String dropStudentsTableQuery = "DROP TABLE IF EXISTS STUDENTS";
            PreparedStatement dropStudentsTableStatement = connection.prepareStatement(dropStudentsTableQuery);
            dropStudentsTableStatement.executeUpdate();
            dropStudentsTableStatement.close();

            // Step 2: Create the STUDENTS table
            String createStudentsTableQuery = "CREATE TABLE STUDENTS (ST_ID SERIAL PRIMARY KEY, ST_NAME VARCHAR(30), ST_LAST VARCHAR(30))";
            PreparedStatement createStudentsTableStatement = connection.prepareStatement(createStudentsTableQuery);
            createStudentsTableStatement.executeUpdate();
            createStudentsTableStatement.close();

            // Step 3: Drop the INTERESTS table if it exists
            String dropInterestsTableQuery = "DROP TABLE IF EXISTS INTERESTS";
            PreparedStatement dropInterestsTableStatement = connection.prepareStatement(dropInterestsTableQuery);
            dropInterestsTableStatement.executeUpdate();
            dropInterestsTableStatement.close();

            // Step 4: Create the INTERESTS table with a VARCHAR column
            String createInterestsTableQuery = "CREATE TABLE INTERESTS (STUDENT_ID INTEGER, INTERESTS VARCHAR(255))";
            PreparedStatement createInterestsTableStatement = connection.prepareStatement(createInterestsTableQuery);
            createInterestsTableStatement.executeUpdate();
            createInterestsTableStatement.close();

            // Step 5: Populate the STUDENTS table with sample data
            String insertStudentsDataQuery = "INSERT INTO STUDENTS (ST_ID, ST_NAME, ST_LAST) VALUES (?, ?, ?)";
            PreparedStatement insertStudentsDataStatement = connection.prepareStatement(insertStudentsDataQuery);
            insertStudentsDataStatement.setInt(1, 1);
            insertStudentsDataStatement.setString(2, "Konul");
            insertStudentsDataStatement.setString(3, "Gurbanova");
            insertStudentsDataStatement.executeUpdate();

            insertStudentsDataStatement.setInt(1, 2);
            insertStudentsDataStatement.setString(2, "Shahnur");
            insertStudentsDataStatement.setString(3, "Isgandarli");
            insertStudentsDataStatement.executeUpdate();

            insertStudentsDataStatement.setInt(1, 3);
            insertStudentsDataStatement.setString(2, "Natavan");
            insertStudentsDataStatement.setString(3, "Mammadova");
            insertStudentsDataStatement.executeUpdate();

            insertStudentsDataStatement.close();

            // Step 6: Populate the INTERESTS table with sample data
            String insertInterestsDataQuery = "INSERT INTO INTERESTS (STUDENT_ID, INTERESTS) VALUES (?, ?)";
            PreparedStatement insertInterestsDataStatement = connection.prepareStatement(insertInterestsDataQuery);
            insertInterestsDataStatement.setInt(1, 1);
            insertInterestsDataStatement.setString(2, "Tennis");
            insertInterestsDataStatement.executeUpdate();

            insertInterestsDataStatement.setInt(1, 1);
            insertInterestsDataStatement.setString(2, "Literature");
            insertInterestsDataStatement.executeUpdate();

            insertInterestsDataStatement.setInt(1, 2);
            insertInterestsDataStatement.setString(2, "Math");
            insertInterestsDataStatement.executeUpdate();

            insertInterestsDataStatement.setInt(1, 2);
            insertInterestsDataStatement.setString(2, "Tennis");
            insertInterestsDataStatement.executeUpdate();

            insertInterestsDataStatement.setInt(1, 3);
            insertInterestsDataStatement.setString(2, "Math");
            insertInterestsDataStatement.executeUpdate();

            insertInterestsDataStatement.setInt(1, 3);
            insertInterestsDataStatement.setString(2, "Music");
            insertInterestsDataStatement.executeUpdate();

            insertInterestsDataStatement.setInt(1, 2);
            insertInterestsDataStatement.setString(2, "Football");
            insertInterestsDataStatement.executeUpdate();

            insertInterestsDataStatement.setInt(1, 1);
            insertInterestsDataStatement.setString(2, "Chemistry");
            insertInterestsDataStatement.executeUpdate();

            insertInterestsDataStatement.setInt(1, 3);
            insertInterestsDataStatement.setString(2, "Chess");
            insertInterestsDataStatement.executeUpdate();

            insertInterestsDataStatement.close();

            // Step 7: Rename the STUDENTS.ST_ID to STUDENTS.STUDENT_ID
            String renameColumnQuery = "ALTER TABLE STUDENTS RENAME COLUMN ST_ID TO STUDENT_ID";
            PreparedStatement renameColumnStatement = connection.prepareStatement(renameColumnQuery);
            renameColumnStatement.executeUpdate();
            renameColumnStatement.close();

            // Step 8: Change the length of STUDENTS.ST_NAME and STUDENTS.ST_LAST from 20 to 30
            String modifyColumnQuery = "ALTER TABLE STUDENTS ALTER COLUMN ST_NAME TYPE VARCHAR(30), ALTER COLUMN ST_LAST TYPE VARCHAR(30)";
            PreparedStatement modifyColumnStatement = connection.prepareStatement(modifyColumnQuery);
            modifyColumnStatement.executeUpdate();
            modifyColumnStatement.close();

            // Close connection
            connection.close();
            System.out.println("Migration completed successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
