package pgp.flightfinder.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestDTO {

    private SearchRequestDTO from;
    private SearchRequestDTO to;
    private List<SearchRequestDTO> searchRequestDTOList;
}
