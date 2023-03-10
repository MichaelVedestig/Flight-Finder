package pgp.flightfinder.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class FlightDTO {

    private List<String> flightIdList;
    private String departureAt;
    private String arrivalAt;
    private Price prices;
    private String waitTime;
}
