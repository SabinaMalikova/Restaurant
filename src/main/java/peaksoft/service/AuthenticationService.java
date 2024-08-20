package peaksoft.service;

import peaksoft.dto.authDto.AuthenticationResponse;
import peaksoft.dto.authDto.SignInRequest;

public interface AuthenticationService {
    AuthenticationResponse signIn(SignInRequest signInRequest);


}
