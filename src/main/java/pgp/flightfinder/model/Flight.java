package pgp.flightfinder.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flight")
public class Flight {
    @Id
    private String flight_id;
    private String departureAt;
    private String arrivalAt;
    private int availableSeats;
    @Embedded
    private Price prices;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "route_id")
    @JsonBackReference
    private Route route;

}
