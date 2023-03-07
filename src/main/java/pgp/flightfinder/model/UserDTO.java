package pgp.flightfinder.model;


import pgp.flightfinder.auth.token.Token;

import java.util.List;

public record UserDTO(
        Integer id,
        String email,
        Role role,
        List<Token> tokens
) {
}
