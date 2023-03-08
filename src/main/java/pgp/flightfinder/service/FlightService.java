package pgp.flightfinder.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pgp.flightfinder.model.Flight;
import pgp.flightfinder.repository.FlightRepository;

@Service
public class FlightService {

    FlightRepository flightRepository;

    public Iterable<Flight> findAll(Specification<Flight> specifications) {
        return flightRepository.findAll(specifications);
    }
}
