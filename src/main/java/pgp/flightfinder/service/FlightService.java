package pgp.flightfinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pgp.flightfinder.model.*;
import pgp.flightfinder.repository.BookingRepository;
import pgp.flightfinder.repository.FlightRepository;
import pgp.flightfinder.repository.UserRepository;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static pgp.flightfinder.model.SearchRequestDTO.Operation.JOIN;
import static pgp.flightfinder.model.SearchRequestDTO.Operation.NOT_EQUAL;

@Service
public class FlightService {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private FilterSpecification<Flight> filterSpecification;

    public List<FlightDTO> findAll(RequestDTO requestDTO) {
        List<FlightDTO> allFlights = new ArrayList<>();
        allFlights.addAll(findDirectFlights(requestDTO));
        allFlights.addAll(findConnectingFlights(requestDTO));
        return allFlights;
    }

    public List<FlightDTO> findDirectFlights(RequestDTO requestDTO) {
        List<SearchRequestDTO> withDestination = new ArrayList<>();
        withDestination.add(requestDTO.getTo());
        withDestination.addAll(requestDTO.getSearchRequestDTOList());
        Specification<Flight> specifications = filterSpecification
                .getSearchSpecification(requestDTO.getFrom(), withDestination);
        Iterable<Flight> directFlights = flightRepository.findAll(specifications);
        List<FlightDTO> directFlightsDTOs = new ArrayList<>();
        for (Flight flight : directFlights) {
            directFlightsDTOs.add(new FlightDTO(
                    List.of(flight.getFlight_id()),
                    flight.getDepartureAt(),
                    flight.getArrivalAt(),
                    flight.getPrices(),
                    "direct flight"
            ));
        }
        return directFlightsDTOs;
    }

    public List<FlightDTO> findConnectingFlights(RequestDTO requestDTO) {

        requestDTO.getTo().setOperation(NOT_EQUAL);
            List<SearchRequestDTO> withoutDestination = new ArrayList<>();
            withoutDestination.add(requestDTO.getTo());
            withoutDestination.addAll(requestDTO.getSearchRequestDTOList());
        Specification<Flight> departureSpecification = filterSpecification
                .getSearchSpecification(requestDTO.getFrom(), withoutDestination);
        List<Flight> departures = flightRepository.findAll(departureSpecification);

        requestDTO.getTo().setOperation(JOIN);
        requestDTO.getFrom().setOperation(NOT_EQUAL);
            List<SearchRequestDTO> withoutDeparture = new ArrayList<>();
            withoutDeparture.add(requestDTO.getFrom());
            withoutDeparture.addAll(requestDTO.getSearchRequestDTOList());
        Specification<Flight> arrivalSpecification = filterSpecification
                .getSearchSpecification(requestDTO.getTo(), withoutDeparture);
        List<Flight> arrivals = flightRepository.findAll(arrivalSpecification);

        List<FlightDTO> connectingFlights = new ArrayList<>();
        for (Flight departure : departures) {
            for (Flight arrival : arrivals) {

                if (departure.getArrivalAt().compareTo(arrival.getDepartureAt()) < 0) {
                    connectingFlights.add(new FlightDTO(
                            List.of(departure.getFlight_id(), arrival.getFlight_id()),
                            departure.getDepartureAt(),
                            arrival.getArrivalAt(),
                            new Price(
                                    departure.getPrices().getCurrency(),
                                    departure.getPrices().getAdult() + arrival.getPrices().getAdult(),
                                    departure.getPrices().getChild() + arrival.getPrices().getChild()
                            ),
                            Duration.between(OffsetDateTime.parse(arrival.getDepartureAt()), OffsetDateTime.parse(departure.getArrivalAt())).toString()
                    ));
                }
            }
        }
        return connectingFlights;
    }

    public Booking bookFlight(Order order) {
        return bookingRepository.save(new Booking(
                userRepository.findByEmail(order.getEmail()),
                order,
                order.getFlightDTO().getPrices().getAdult() * order.getAdults() +
                order.getFlightDTO().getPrices().getChild() * order.getChildren(),
                order.getFlightDTO().getPrices().getCurrency()
        ));
    }

    public Booking findBooking(String id) {
        return bookingRepository.findById(id).get();
    }
}

