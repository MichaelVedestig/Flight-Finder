package pgp.flightfinder.repository;

import org.springframework.data.repository.CrudRepository;
import pgp.flightfinder.model.Route;

public interface RouteRepository extends CrudRepository<Route, String> {


}
