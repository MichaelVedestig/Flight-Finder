package pgp.flightfinder.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pgp.flightfinder.model.Flight;

@Repository

public interface FlightRepository extends CrudRepository<Flight, String>, JpaSpecificationExecutor {
}
