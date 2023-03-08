package pgp.flightfinder.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestDTO {

    private List<SearchRequestDTO> searchRequestDTOList;
}
