package pgp.flightfinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pgp.flightfinder.model.Flight;
import pgp.flightfinder.repository.FlightRepository;

@Service
public class FlightService {

    @Autowired
    FlightRepository flightRepository;

    public Iterable<Flight> findAll(Specification<Flight> specifications) {
        System.out.println(specifications);
        return flightRepository.findAll(specifications);
    }
}
