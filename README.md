# Java Spring JDBC
The target of this exercise is to practice Spring JDBC with Java 17.

## Features
- CRUD of user project mixing Stored procedures and common SQL statements.
- Generate data in memory and INSERT batches through Stored procedures.
- Specific SELECT with JOINs to print out all names (only distinct) of users who has more than average friends and 100 likes in the last month USING stored function.
- Specific SELECT with JOINs to print out all names (only distinct) of users who has more than average friends and 100 likes in the last month USING SQL statement.
- Check difference between both approaches (With Store function is faster in each run).

## Requirements
- Install Docker
- Download Postgres Docker image
- Install psql client

## Run the project
1. Run postgres through Docker with following command: `docker run --rm --name lil-postgres -e POSTGRES_PASSWORD=password -p 5432:5432 -d postgres`
2. Run psql command to create DB: `psql -h localhost -U postgres -f database.sql` and enter the password: `password`
3. Run psql command to create stored procedures: `psql -h localhost -U postgres -d tu_spring_db -f stored_procedures.sql` and enter the password: `password`
4. Run Main program.
5. Run command to stop Docker execution: `docker stop lil-postgres`

## Output
![Screenshot 2024-09-25 at 11 51 14 AM](https://github.com/user-attachments/assets/1949c369-7527-413f-b64d-d5c1291e4e63)


## Test output
![Screenshot 2024-09-25 at 11 49 07 AM](https://github.com/user-attachments/assets/5d4eedac-8671-408b-bdcc-c6dfe3a2561b)
