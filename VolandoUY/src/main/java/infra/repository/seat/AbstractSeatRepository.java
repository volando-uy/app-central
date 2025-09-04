package infra.repository.seat;

import domain.models.seat.Seat;
import infra.repository.BaseRepository;

public class AbstractSeatRepository extends BaseRepository<Seat> {
    public AbstractSeatRepository() {
        super(Seat.class);
    }
}
