package pgp.flightfinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pgp.flightfinder.model.*;
import pgp.flightfinder.service.FilterSpecification;
import pgp.flightfinder.service.FlightService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/flight")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @PostMapping("/filter")
    public ResponseEntity<List<FlightDTO>> getFlights(@RequestBody RequestDTO requestDTO){
        return ResponseEntity.ok(flightService.findAll(requestDTO));
    }

    @PostMapping("/booking")
    public ResponseEntity<Booking> bookFlight(@RequestBody Order order){

        return ResponseEntity.ok(flightService.bookFlight(order));
    }

}

