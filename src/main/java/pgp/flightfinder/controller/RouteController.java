package pgp.flightfinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pgp.flightfinder.model.Route;
import pgp.flightfinder.service.RouteService;

@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    public RouteService routeService;
    @GetMapping
    public Iterable<Route> all(){
        return routeService.findAll();
    }
}
