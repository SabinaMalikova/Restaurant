package peaksoft.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.authDto.AuthenticationResponse;
import peaksoft.dto.authDto.SignInRequest;
import peaksoft.dto.userDto.UserRequest;
import peaksoft.service.AuthenticationService;
import peaksoft.service.UserService;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "auth-api")
@RequiredArgsConstructor
public class AuthApi {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/signIn")
    AuthenticationResponse signIn(@RequestBody SignInRequest signInRequest){
        return authenticationService.signIn(signInRequest);
    }


    @PostMapping("/usersRequest")
    SimpleResponse requestUserToRestaurant(@RequestBody @Valid UserRequest userRequest){
        return userService.requestUserToRestaurant(userRequest);
    }

}
