package com.tuspring;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class StoredProcedureManager {

    private final JdbcTemplate jdbcTemplate;

    public StoredProcedureManager(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void dropAllStoredProcedures() {
        List<String> procedures = jdbcTemplate.query(
                "SELECT proname FROM pg_proc p JOIN pg_namespace n ON p.pronamespace = n.oid WHERE n.nspname = 'public' AND p.prokind = 'p'",
                (rs, rowNum) -> rs.getString("proname")
        );

        for (String procedure : procedures) {
            jdbcTemplate.execute("DROP PROCEDURE IF EXISTS " + procedure + " CASCADE;");
        }
    }

    public void createStoredProcedure(String procedureSQL) {
        jdbcTemplate.execute(procedureSQL);
    }

    public List<String> listAllStoredProcedures() {
        return jdbcTemplate.query(
                "SELECT proname FROM pg_proc p JOIN pg_namespace n ON p.pronamespace = n.oid WHERE n.nspname = 'public' AND p.prokind = 'p'",
                (rs, rowNum) -> rs.getString("proname")
        );
    }
}
