package epam.gym.util;

import epam.gym.config.JWTService;
import epam.gym.dao.RoleDao;
import epam.gym.dto.JwtAuthenticationResponse;
import epam.gym.dto.user.SignInRequest;
import epam.gym.dto.user.SignUpRequest;
import epam.gym.entity.Role;
import epam.gym.entity.RoleEntity;
import epam.gym.entity.Trainee;
import epam.gym.entity.Trainer;
import epam.gym.entity.User;
import epam.gym.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthenticationService {

    private UserService userService;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private RoleDao roleDao;


    AuthenticationService(@Lazy UserService userService, JWTService jwtService, PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          RoleDao roleDao){
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.roleDao = roleDao;
    }


    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        Set<String> existingUsernames = userService.getExistingUsernames();
        String generatedUsername = ProfileGenerationHelper.generateUsername(request.getFirstName(), request.getLastName(),
                existingUsernames);

        User user;

        if ("trainee".equalsIgnoreCase(request.getUserType())) {
            RoleEntity userRole = roleDao.findByName(Role.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

            user = Trainee.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .dateOfBirth(request.getDateOfBirth())
                    .username(generatedUsername)
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(Set.of(userRole))
                    .build();
        } else if ("trainer".equalsIgnoreCase(request.getUserType())) {
            user = Trainer.builder()
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(Set.of(RoleEntity.builder().name(Role.ROLE_USER).build()))
                    .build();
        } else {
            throw new IllegalArgumentException("Invalid user type: " + request.getUserType());

        }
        userService.save(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }


    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
