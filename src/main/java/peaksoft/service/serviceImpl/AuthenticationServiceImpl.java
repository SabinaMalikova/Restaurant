package peaksoft.service.serviceImpl;


import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.jwt.JwtService;
import peaksoft.dto.authDto.AuthenticationResponse;
import peaksoft.dto.authDto.SignInRequest;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exception.BadCredentialException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.UserRepo;
import peaksoft.service.AuthenticationService;

import java.time.LocalDate;


@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final JwtService jwtService;

    @PostConstruct
    public void initSaveAdmin() {
        User user = new User();
        user.setFirstName("Admin");
        user.setLastName("Adminov");
        user.setDateOfBirth(LocalDate.now());
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("admin123"));
        user.setPhoneNumber("+996500500500");
        user.setRole(Role.ADMIN);
        user.setExperience(5);
        if (!userRepo.existsByEmail(user.getEmail())){
            userRepo.save(user);
        }
    }


    @Override
    public AuthenticationResponse signIn(SignInRequest signInRequest) {

        User user = userRepo.getUserByEmail(signInRequest.email())
                .orElseThrow(() -> new NotFoundException("User with email " + signInRequest.email() + " not found"));

        if (signInRequest.email().isBlank()) {
            throw new BadCredentialException("invalid email");
        }
        if (!passwordEncoder.matches(signInRequest.password(), user.getPassword())){
            throw new BadCredentialException("invalid password");
        }

        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .token(token)
                .build();
    }
}
