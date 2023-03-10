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
        Iterable<Flight> directflights = flightRepository.findAll(specifications);
        List<FlightDTO> directFlightsDTOs = new ArrayList<>();
        for (Flight flight : directflights) {
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


        Specification<Flight> departureSpecification = filterSpecification
                .getSearchSpecification(requestDTO.getFrom(), requestDTO.getSearchRequestDTOList());
        List<Flight> departures = flightRepository.findAll(departureSpecification);

        Specification<Flight> arrivalSpecification = filterSpecification
                .getSearchSpecification(requestDTO.getTo(), requestDTO.getSearchRequestDTOList());
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

