package infra.repository.user;

import domain.models.user.Airline;

import java.util.List;

public interface IAirlineRepository extends IAbstractUserRepository<Airline> {
    Airline getAirlineByNickname(String nickname);
    Airline getAirlineByEmail(String email);
    List<Airline> findFullAll();
    Airline findFullByNickname(String nickname);
}
