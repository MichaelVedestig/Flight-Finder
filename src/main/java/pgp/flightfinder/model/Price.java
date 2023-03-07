package pgp.flightfinder.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Price {
    private String currency;
    private double adult;
    private double child;

}
