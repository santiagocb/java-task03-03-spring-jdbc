# Java Spring JDBC
The target of this exercise is to practice Spring JDBC with Java 17.

## Features
- CRUD of user project
- Generate data in memory and INSERT batches
- Specific SELECT with JOINs to print out all names (only distinct) of users who has more than average friends and 100 likes in the last month.

## Requirements
- Install Docker
- Download Postgres Docker image
- Install psql client

## Run the project
1. Run postgres through Docker with following command: `docker run --rm --name lil-postgres -e POSTGRES_PASSWORD=password -p 5432:5432 -d postgres`
2. Run psql command to create DB: `psql -h localhost -U postgres -f database.sql` and enter the password: `password`
3. Run Main program.
4. Run command to stop Docker execution: `docker stop lil-postgres`

## Output
![Screenshot 2024-09-21 at 10 34 04 PM](https://github.com/user-attachments/assets/b398a207-db88-4899-87f5-3d25f2081eb7)
