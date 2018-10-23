package uk.gov.metoffice.apprentice.tickets.database;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import uk.gov.metoffice.apprentice.tickets.domain.Event;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventRowMapper implements RowMapper<Event> {
    @Nullable
    @Override
    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Event(rs.getObject("event_id"),
                rs.getString("event_name"),
                rs.getString("event_location"),
                rs.getTimestamp("event_dt").toLocalDateTime());
    }
}
