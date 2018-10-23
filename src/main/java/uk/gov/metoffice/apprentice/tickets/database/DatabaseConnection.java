package uk.gov.metoffice.apprentice.tickets.database;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseConnection {

    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());

    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

    private DataSource getDataSource() {
        String url = "jdbc:postgresql://localhost/mike-ticket-system";

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        try {
            dataSource.getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
        }

        return dataSource;
    }
}
