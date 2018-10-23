package uk.gov.metoffice.apprentice.tickets.handlers;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.gov.metoffice.apprentice.tickets.database.DatabaseConnection;
import uk.gov.metoffice.apprentice.tickets.database.EventRowMapper;
import uk.gov.metoffice.apprentice.tickets.domain.Event;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class EventsListingHandler extends AbstractHandler {

    private DatabaseConnection databaseConnection;
    private EventRowMapper rowMapper = new EventRowMapper();

    public EventsListingHandler(final DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String html = createHtmlContent(getEvents());

        PrintWriter out = response.getWriter();
        out.print(html);

        baseRequest.setHandled(true);
    }

    private String getFileContents(String relativeFilepath) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        return IOUtils.toString(classLoader.getResourceAsStream(relativeFilepath));
    }

    private String createHtmlContent(List<Event> events) throws IOException {
        String eventsHtml = getFileContents("html/events.html");
        Document html = Jsoup.parse(eventsHtml);

        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            String rowNumHtml = "<th scope=\"row\">" + (i + 1) + "</th>";
            String eventNameHtml = "<td scope=\"row\">" + event.getName() + "</td>";
            String eventDateHtml = "<td scope=\"row\">" + event.getDateTime() + "</td>";
            String eventLocationHtml = "<td scope=\"row\">" + event.getLocation() + "</td>";

            html.getElementById("table-data")
                    .append("<tr>" + rowNumHtml + eventNameHtml + eventDateHtml + eventLocationHtml + "\n");
        }
        return html.toString();
    }

    private List<Event> getEvents() {
        JdbcTemplate jdbcTemplate = databaseConnection.getJdbcTemplate();

        return jdbcTemplate.query("SELECT * FROM public.events_table", rowMapper);
    }

}
