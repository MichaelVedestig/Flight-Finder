package pgp.flightfinder.repository;

import org.springframework.data.repository.CrudRepository;
import pgp.flightfinder.model.Flight;

public interface FlightRepository extends CrudRepository<Flight, String> {
}
