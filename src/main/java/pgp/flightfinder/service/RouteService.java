package pgp.flightfinder.service;

import org.springframework.stereotype.Service;
import pgp.flightfinder.model.Route;
import pgp.flightfinder.repository.RouteRepository;

import java.util.List;

@Service
public class RouteService {


    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public Iterable<Route> findAll() {
        return routeRepository.findAll();
    }
    public void saveAll(List<Route> routeList) {
        routeRepository.saveAll(routeList);
    }
}
