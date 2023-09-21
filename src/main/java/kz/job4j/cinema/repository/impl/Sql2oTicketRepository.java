package kz.job4j.cinema.repository.impl;

import kz.job4j.cinema.model.entity.Ticket;
import kz.job4j.cinema.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.Collection;
import java.util.Optional;

@Repository
@ThreadSafe
@Slf4j
public class Sql2oTicketRepository implements TicketRepository {
    private final Sql2o sql2o;
    private static final String INSERT_TICKET = "INSERT INTO tickets (session_id, row_number, place_number, user_id) "
            + "VALUES (:session_id, :row_number, :place_number, :user_id)";
    private static final String FIND_BY_ID = "SELECT * FROM tickets WHERE id = :id";
    private static final String DELETE_BY_ID = "DELETE FROM tickets WHERE id = :id";
    private static final String DELETE_ALL = "DELETE FROM tickets";
    private static final String FIND_ALL = "SELECT * FROM tickets";
    private static final String FIND_BY_SESSION_AND_ROW_AND_PLACE = "SELECT * FROM tickets t"
            + " WHERE t.session_id = :session_id "
            + " and t.row_number = :row_number "
            + " and t.place_number = :place_number ";

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Ticket save(Ticket ticket) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(INSERT_TICKET, true)
                    .addParameter("session_id", ticket.getSessionId())
                    .addParameter("row_number", ticket.getRowNumber())
                    .addParameter("place_number", ticket.getPlaceNumber())
                    .addParameter("user_id", ticket.getUserId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            ticket.setId(generatedId);
            return ticket;
        }
    }

    @Override
    public Optional<Ticket> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_BY_ID);
            var ticket = query.addParameter("id", id).setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetchFirst(Ticket.class);
            return Optional.ofNullable(ticket);
        }
    }

    @Override
    public void deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(DELETE_BY_ID);
            query.addParameter("id", id).executeUpdate();
        }
    }

    @Override
    public void deleteAll() {
        try (var connection = sql2o.open()) {
            connection.createQuery(DELETE_ALL).executeUpdate();
        }
    }

    @Override
    public Optional<Ticket> findBySessionIdAndRowNumberAndPlaceNumber(Integer sessionId, Integer rowNumber, Integer placeNumber) {
        try (var connection = sql2o.open()) {
            log.info("findBySessionIdAndRowNumberAndPlaceNumber method request: sessionId: {}, rowNumber: {}, placeNumber: {}",
                    sessionId, rowNumber, placeNumber);
            var query = connection.createQuery(FIND_BY_SESSION_AND_ROW_AND_PLACE);
            var ticket = query
                    .addParameter("session_id", sessionId)
                    .addParameter("row_number", rowNumber)
                    .addParameter("place_number", placeNumber)
                    .setColumnMappings(Ticket.COLUMN_MAPPING)
                    .executeAndFetchFirst(Ticket.class);
            log.info("findBySessionIdAndRowNumberAndPlaceNumber method response: {}", ticket);
            return Optional.ofNullable(ticket);
        }
    }

    @Override
    public Collection<Ticket> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_ALL);
            return query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetch(Ticket.class);
        }
    }
}
