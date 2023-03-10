package pgp.flightfinder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchRequestDTO {

    String filterBy;
    Operation operation;
    String value;
    String joinTable;

    public enum Operation{
        EQUAL, LIKE, IN, GREATER_THAN, LESS_THAN, BETWEEN, JOIN, NOT_EQUAL;
    }
}
