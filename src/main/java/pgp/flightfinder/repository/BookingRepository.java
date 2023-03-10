package pgp.flightfinder.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pgp.flightfinder.model.Booking;

@Repository
public interface BookingRepository extends CrudRepository<Booking, String> {
}
