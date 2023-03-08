package pgp.flightfinder.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequestDTO {

    String column;
    String value;
}
