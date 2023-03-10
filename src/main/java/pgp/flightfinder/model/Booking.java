package pgp.flightfinder.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;


@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    private String booking_id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
    @Embedded
    private Order bookingDetails;

    private double total;

    @Column(insertable=false, updatable=false)
    private String currency;

    public Booking (User user, Order bookingDetails, double total, String currency){
        this.booking_id = RandomStringUtils.randomAlphanumeric(6).toUpperCase();
        this.user = user;
        this.bookingDetails = bookingDetails;
        this.total = total;
        this.currency = currency;
    }
}
