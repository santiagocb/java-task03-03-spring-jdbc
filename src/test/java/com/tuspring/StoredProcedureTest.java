package com.tuspring;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class StoredProcedureTest {

    @Test
    public void testDropAndCreateStoredProcedures() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.scan("com.tuspring");
        context.refresh();

        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        StoredProcedureManager storedProcedureManager = new StoredProcedureManager(jdbcTemplate.getDataSource());

        // Drop all procedures
        storedProcedureManager.dropAllStoredProcedures();

        // Create a stored procedure
        String procedureSQL = "CREATE OR REPLACE PROCEDURE sampleProcedure() " +
                "LANGUAGE plpgsql AS $$ BEGIN RAISE NOTICE 'Sample Procedure executed'; END; $$;";
        storedProcedureManager.createStoredProcedure(procedureSQL);

        // List and print stored procedures
        List<String> procedures = storedProcedureManager.listAllStoredProcedures();
        procedures.forEach(System.out::println);

        // Close context
        context.close();
    }
}
