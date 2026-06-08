package br.com.shopflow.auth_service.service;


import br.com.shopflow.auth_service.dto.AuthRequest;
import br.com.shopflow.auth_service.dto.AuthRespose;
import br.com.shopflow.auth_service.dto.RegisterRequest;
import br.com.shopflow.auth_service.enums.Role;
import br.com.shopflow.auth_service.model.User;
import br.com.shopflow.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthRespose register(RegisterRequest dto){
        if (userRepository.findByEmail(dto.email()).isPresent()){
            throw new RuntimeException("Esse usuário já existe, logue com ele");
        }

        var user = User.builder()
                .name(dto.name())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        var token = jwtService.generateToken((UserDetails) user);
        return new AuthRespose(token);
    }

    public AuthRespose login(AuthRequest dto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password())
        );

        var user = userRepository.findByEmail(dto.email()).orElseThrow();
        var token = jwtService.generateToken((UserDetails) user);
        return new AuthRespose(token);
    }
}
