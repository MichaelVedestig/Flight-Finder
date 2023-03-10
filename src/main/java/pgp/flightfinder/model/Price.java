package pgp.flightfinder.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Price {
    private String currency;
    private double adult;
    private double child;

}
