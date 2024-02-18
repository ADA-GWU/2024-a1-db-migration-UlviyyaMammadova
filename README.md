# Database Migration and Rollback Scripts

## Overview

These scripts facilitate the migration of a PostgreSQL database schema and data to meet specified requirements. Additionally, they provide a rollback mechanism to revert the changes if necessary. The migration script alters the structure of the database tables and transforms the data into the desired format. The rollback script restores the original structure and data of the database.

## Migration Script (`Migration.java`)

The `Migration.java` script performs the following tasks:

1. **Table Creation:** Initializes the `STUDENTS` and `INTERESTS` tables with the required structure.
2. **Data Population:** Inserts sample data into the tables to simulate real-world scenarios.
3. **Migration Process:** Executes the migration steps, including:
    - Renaming the `ST_ID` column to `STUDENT_ID`.
    - Adjusting the length of the `ST_NAME` and `ST_LAST` columns to accommodate longer strings.
    - Renaming the `INTEREST` column in the `INTERESTS` table to `INTERESTS`.
    - Changing the type of the `INTERESTS` column to an array of strings.
    - Updating the data in the `INTERESTS` table to conform to the new schema.
4. **Completion Message:** Prints a success message upon the successful completion of the migration process.

## Rollback Script (`Rollback.java`)

The `Rollback.java` script enables the rollback of the migration by executing the following steps:

1. **Connection Setup:** Connects to the PostgreSQL database using the specified credentials.
2. **Rollback Procedure:** Reverts the changes made during the migration process by:
    - Dropping the `INTERESTS` table to remove the modified schema.
    - Recreating the `INTERESTS` table with its original structure to restore the database to its initial state.
    - Reinserting the data in the original format into the `INTERESTS` table.
3. **Completion Message:** Prints a success message upon the successful completion of the rollback process.

## Usage Instructions

To utilize these scripts, follow these steps:

1. **Prerequisites:** Ensure that you have a PostgreSQL database installed and running locally. Update the database connection parameters in the scripts (`url`, `username`, `password`) to match your PostgreSQL setup.
2. **Compile Scripts:** Compile both `Migration.java` and `Rollback.java` using a Java compiler.
3. **Migration:** Execute `Migration.java` to perform the migration of the database schema and data.
4. **Rollback:** If necessary, execute `Rollback.java` to revert the changes made by the migration script.

## Dependencies

- Java Development Kit (JDK) installed on your system.
- PostgreSQL JDBC driver (ensure it's included in the classpath or referenced in your project).

## Note

- It's recommended to review and customize the database connection parameters in the scripts before execution to match your PostgreSQL setup.
