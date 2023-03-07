package pgp.flightfinder.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pgp.flightfinder.model.UserDTO;

@Controller
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/user/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/user/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticate
    ){
        return ResponseEntity.ok(authenticationService.authenticate(authenticate));

    }
    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUser (
            @RequestBody String email
    ){
        return ResponseEntity.ok(authenticationService.getUser(email));
    }

}
