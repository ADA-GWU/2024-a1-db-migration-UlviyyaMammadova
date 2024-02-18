import java.sql.*;

public class Migration {

    private static final String CREATE_STUDENTS_TABLE_QUERY =
            "CREATE TABLE STUDENTS (ST_ID SERIAL PRIMARY KEY, ST_NAME VARCHAR(20), ST_LAST VARCHAR(20))";

    private static final String CREATE_INTERESTS_TABLE_QUERY =
            "CREATE TABLE INTERESTS (STUDENT_ID INTEGER, INTEREST VARCHAR(20))";

    private static final String INSERT_STUDENTS_DATA_QUERY =
            "INSERT INTO STUDENTS (ST_NAME, ST_LAST) VALUES (?, ?)";

    private static final String INSERT_INTERESTS_DATA_QUERY =
            "INSERT INTO INTERESTS (STUDENT_ID, INTEREST) VALUES (?, ?)";

    private static final String MIGRATE_STUDENT_ID_COLUMN_QUERY =
            "ALTER TABLE STUDENTS RENAME COLUMN ST_ID TO STUDENT_ID";

    private static final String ALTER_STUDENT_NAME_LENGTH_QUERY =
            "ALTER TABLE STUDENTS ALTER COLUMN ST_NAME TYPE VARCHAR(30)";

    private static final String ALTER_STUDENT_LAST_LENGTH_QUERY =
            "ALTER TABLE STUDENTS ALTER COLUMN ST_LAST TYPE VARCHAR(30)";

    private static final String MIGRATE_INTERESTS_COLUMN_QUERY =
            "ALTER TABLE INTERESTS RENAME COLUMN INTEREST TO INTERESTS";

    private static final String ALTER_INTERESTS_TYPE_QUERY =
            "ALTER TABLE INTERESTS ALTER COLUMN INTERESTS TYPE VARCHAR(255)[] " +
            "USING ARRAY[INTERESTS]";

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/hw1_students";
        String username = "postgres";
        String password = "12345";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            createTables(connection);
            insertSampleData(connection);
            performMigration(connection);
            System.out.println("Migration completed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTables(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_STUDENTS_TABLE_QUERY);
            statement.executeUpdate(CREATE_INTERESTS_TABLE_QUERY);
        }
    }

    private static void insertSampleData(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_STUDENTS_DATA_QUERY)) {
            statement.setString(1, "Konul");
            statement.setString(2, "Gurbanova");
            statement.executeUpdate();

            statement.setString(1, "Shahnur");
            statement.setString(2, "Isgandarli");
            statement.executeUpdate();

            statement.setString(1, "Natavan");
            statement.setString(2, "Mammadova");
            statement.executeUpdate();
        }

        try (PreparedStatement statement = connection.prepareStatement(INSERT_INTERESTS_DATA_QUERY)) {
            statement.setInt(1, 1);
            statement.setString(2, "Tennis");
            statement.executeUpdate();

            statement.setInt(1, 1);
            statement.setString(2, "Literature");
            statement.executeUpdate();

            statement.setInt(1, 2);
            statement.setString(2, "Math");
            statement.executeUpdate();

            statement.setInt(1, 2);
            statement.setString(2, "Tennis");
            statement.executeUpdate();

            statement.setInt(1, 3);
            statement.setString(2, "Math");
            statement.executeUpdate();

            statement.setInt(1, 3);
            statement.setString(2, "Music");
            statement.executeUpdate();

            statement.setInt(1, 2);
            statement.setString(2, "Football");
            statement.executeUpdate();

            statement.setInt(1, 1);
            statement.setString(2, "Chemistry");
            statement.executeUpdate();

            statement.setInt(1, 3);
            statement.setString(2, "Chess");
            statement.executeUpdate();
        }
    }

    private static void performMigration(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(MIGRATE_STUDENT_ID_COLUMN_QUERY);
            statement.executeUpdate(ALTER_STUDENT_NAME_LENGTH_QUERY);
            statement.executeUpdate(ALTER_STUDENT_LAST_LENGTH_QUERY);
            statement.executeUpdate(MIGRATE_INTERESTS_COLUMN_QUERY);
            statement.executeUpdate(ALTER_INTERESTS_TYPE_QUERY);
            updateInterestsData(connection);
        }
    }

    private static void updateInterestsData(Connection connection) throws SQLException {
        String updateQuery = "UPDATE INTERESTS " +
                            "SET INTERESTS = ARRAY(SELECT INTERESTS FROM INTERESTS WHERE STUDENT_ID = INTERESTS.STUDENT_ID)";
        
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(updateQuery);
        }
    }
    

    
}
