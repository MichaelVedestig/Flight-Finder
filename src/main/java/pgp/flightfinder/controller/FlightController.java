package pgp.flightfinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pgp.flightfinder.model.Flight;
import pgp.flightfinder.model.RequestDTO;
import pgp.flightfinder.service.FilterSpecification;
import pgp.flightfinder.service.FlightService;

@RestController
@RequestMapping("/flight")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private FilterSpecification<Flight> filterSpecification;

    @PostMapping("/filter")
    public Iterable<Flight> getFlights(@RequestBody RequestDTO requestDTO){
        Specification<Flight> specification = filterSpecification
                .getSearchSpecification(requestDTO.getSearchRequestDTOList());
        return flightService.findAll(specification);
        };
    }

