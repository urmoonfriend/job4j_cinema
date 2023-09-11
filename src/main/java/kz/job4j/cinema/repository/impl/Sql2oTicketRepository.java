package kz.job4j.cinema.repository.impl;

import kz.job4j.cinema.model.entity.Ticket;
import kz.job4j.cinema.repository.TicketRepository;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.Optional;

@Repository
@ThreadSafe
public class Sql2oTicketRepository implements TicketRepository {

    private final Sql2o sql2o;

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Ticket save(Ticket ticket) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("INSERT INTO tickets (session_id, row_number, place_number, user_id) "
                            + "VALUES (:session_id, :row_number, :place_number, :user_id)", true)
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
            var query = connection.createQuery("SELECT * FROM tickets WHERE id = :id");
            var ticket = query.addParameter("id", id).executeAndFetchFirst(Ticket.class);
            return Optional.ofNullable(ticket);
        }
    }

    @Override
    public void deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM tickets WHERE id = :id");
            query.addParameter("id", id).executeUpdate();
        }
    }

    @Override
    public Optional<Ticket> findBySessionIdAndRowNumberAndPlaceNumber(Integer sessionId, Integer rowNumber, Integer placeNumber) {
        try (var connection = sql2o.open()) {
            System.out.println("findBySessionIdAndRowNumberAndPlaceNumber method request: sessionId: " + sessionId
                    + " , rowNumber: " + rowNumber + " , placeNUmber: " + placeNumber);
            var query = connection.createQuery("SELECT * FROM tickets t"
                    + " WHERE t.session_id = :session_id "
                    + " and t.row_number = :row_number "
                    + " and t.place_number = :place_number ");
            var ticket = query
                    .addParameter("session_id", sessionId)
                    .addParameter("row_number", rowNumber)
                    .addParameter("place_number", placeNumber)
                    .setColumnMappings(Ticket.COLUMN_MAPPING)
                    .executeAndFetchFirst(Ticket.class);
            System.out.println("findBySessionIdAndRowNumberAndPlaceNumber method response: " + ticket);
            return Optional.ofNullable(ticket);
        }
    }
}
