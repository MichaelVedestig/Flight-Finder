package pgp.flightfinder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pgp.flightfinder.model.Route;
import pgp.flightfinder.service.RouteService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@SpringBootApplication
public class FlightFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightFinderApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(RouteService routeService){
		return args -> {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<Route>> typeReference = new TypeReference<List<Route>>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/json/routes.json");
			try {
				List<Route> routeList = mapper.readValue(inputStream, typeReference);
				routeService.saveAll(routeList);
				System.out.println("In-memory database successfully seeded with data from routes.json.");
			} catch (IOException e) {
				System.out.println("Unable to seed database " + e.getMessage());
			}
		};
	}
}
