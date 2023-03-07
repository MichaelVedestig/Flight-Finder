package pgp.flightfinder.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "route")
public class Route {

    @Id
    private String route_id;
    private String departureDestination;
    private String arrivalDestination;
    @OneToMany(mappedBy = "route", cascade= CascadeType.ALL)
    @JsonManagedReference
    private Set<Flight> itineraries;
}
