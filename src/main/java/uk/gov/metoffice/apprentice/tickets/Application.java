package uk.gov.metoffice.apprentice.tickets;

import uk.gov.metoffice.apprentice.tickets.database.DatabaseConnection;
import uk.gov.metoffice.apprentice.tickets.server.WebServer;

import java.util.logging.Logger;

public class Application {
    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        DatabaseConnection dbConnection = new DatabaseConnection();

        WebServer webServer = new WebServer(dbConnection);
        try {
            webServer.startServer();
        } catch (Exception ex) {
            LOGGER.warning("failed to start web server" + ex.getMessage());
        }
    }
}
