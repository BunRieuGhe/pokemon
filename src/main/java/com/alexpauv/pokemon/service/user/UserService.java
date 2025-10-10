package com.alexpauv.pokemon.service.user;

import com.alexpauv.pokemon.dto.LoginDto;
import com.alexpauv.pokemon.dto.LoginRequest;
import com.alexpauv.pokemon.dto.RegisterDto;
import com.alexpauv.pokemon.dto.RegisterRequest;
import com.alexpauv.pokemon.exception.AuthenticationFailedException;
import com.alexpauv.pokemon.exception.EmailAlreadyExistsException;
import com.alexpauv.pokemon.exception.MyUsernameNotFoundException;
import com.alexpauv.pokemon.exception.UsernameAlreadyExistsException;
import com.alexpauv.pokemon.model.user.User;
import com.alexpauv.pokemon.model.user.UserStatus;
import com.alexpauv.pokemon.repository.user.UserRepository;
import com.alexpauv.pokemon.service.JwtService;
import com.alexpauv.pokemon.service.role.RoleService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.roleService = roleService;
    }

    private boolean isCurrentUserAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return false;
        } else {
            String currentUsername = authentication.getName();
            return getUserByUsername(currentUsername).getRoles().stream().anyMatch(role -> RoleService.ADMIN_ROLE_NAME.equals(role.getName()));
        }
    }

    public RegisterDto registerUser(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        user.setRoles(Set.of(roleService.getDefaultRole()));

        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);

        return new RegisterDto(savedUser, token);
    }

    public LoginDto loginUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                User user = getUserByUsername(loginRequest.getUsername());
                String token = jwtService.generateToken(user);
                return new LoginDto(user.getUsername(), token);
            }
        } catch (AuthenticationException e) {
            throw new AuthenticationFailedException("Authentication failed");
        }
        return new LoginDto();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new MyUsernameNotFoundException("Username not found: " + username));
    }
}
