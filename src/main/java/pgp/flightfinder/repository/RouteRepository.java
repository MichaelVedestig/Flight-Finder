package pgp.flightfinder.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pgp.flightfinder.model.Route;

@Repository

public interface RouteRepository extends CrudRepository<Route, String> {


}
