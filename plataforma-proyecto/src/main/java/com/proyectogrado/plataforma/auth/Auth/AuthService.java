package com.proyectogrado.plataforma.auth.Auth;

import com.proyectogrado.plataforma.auth.Jwt.JwtService;
import com.proyectogrado.plataforma.auth.Entities.User;
import com.proyectogrado.plataforma.auth.Entities.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        try {
            // Authenticate the user (loads from DB via UserDetailsService)
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // Get the authenticated user (already loaded from DB)
            User user = (User) authentication.getPrincipal();

            // Generate the token
            String token = jwtService.getToken(user);

            return AuthResponse.builder()
                    .token(token)
                    .role(user.getRole().name())
                    .message("")
                    .build();

        } catch (AuthenticationException e) {
            return AuthResponse.builder()
                    .token("")
                    .message("Invalid username or password.")
                    .role("")
                    .build();
        } catch (DataAccessException e) {
            return AuthResponse.builder()
                    .token("")
                    .message("Database error. Please try again later.")
                    .role("")
                    .build();
        } catch (Exception e) {
            return AuthResponse.builder()
                    .token("")
                    .message("An unexpected error occurred.")
                    .role("")
                    .build();
        }
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return AuthResponse.builder()
                    .token("") // Empty token since registration failed
                    .message("Email (username) already taken. Please choose a different one.")
                    .build();
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .role(request.getRole())
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .message("") // No message in successful registration
                .role(user.getRole().name())
                .build();
    }
}
