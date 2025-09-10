package infra.repository.booking;

import domain.models.bookflight.BookFlight;
import infra.repository.BaseRepository;

import java.util.List;

public abstract class AbstractBookingRepository extends BaseRepository<BookFlight> {
    public AbstractBookingRepository() {
        super(BookFlight.class);
    }
}
