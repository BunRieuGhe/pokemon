package com.alexpauv.pokemon.service.user;

import com.alexpauv.pokemon.dto.auth.LoginDto;
import com.alexpauv.pokemon.dto.auth.LoginRequest;
import com.alexpauv.pokemon.dto.auth.PasswordResetConfirmationRequest;
import com.alexpauv.pokemon.dto.auth.PasswordResetDto;
import com.alexpauv.pokemon.dto.auth.PasswordResetInquiryRequest;
import com.alexpauv.pokemon.dto.auth.RegisterDto;
import com.alexpauv.pokemon.dto.auth.RegisterRequest;
import com.alexpauv.pokemon.dto.user.UserAddOrRemoveRoleRequest;
import com.alexpauv.pokemon.dto.user.UserDto;
import com.alexpauv.pokemon.exception.AccountDeadException;
import com.alexpauv.pokemon.exception.CustomAccountLockedException;
import com.alexpauv.pokemon.exception.CustomBadCredentialsException;
import com.alexpauv.pokemon.exception.CustomUsernameNotFoundException;
import com.alexpauv.pokemon.exception.EmailAlreadyExistsException;
import com.alexpauv.pokemon.exception.UsernameAlreadyExistsException;
import com.alexpauv.pokemon.model.role.Role;
import com.alexpauv.pokemon.model.user.User;
import com.alexpauv.pokemon.model.user.UserRoleChangeType;
import com.alexpauv.pokemon.model.user.UserStatus;
import com.alexpauv.pokemon.repository.user.UserRepository;
import com.alexpauv.pokemon.service.JwtService;
import com.alexpauv.pokemon.service.auth.PasswordResetService;
import com.alexpauv.pokemon.service.role.RoleService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final PasswordResetService passwordResetService;

    private final RoleService roleService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, PasswordResetService passwordResetService, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordResetService = passwordResetService;
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

    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new CustomUsernameNotFoundException("Username not found: " + username));
    }

    private User getUserByUuid(String uuid) {
        return userRepository.findByUuid(UUID.fromString(uuid)).orElseThrow(() -> new CustomUsernameNotFoundException("User not found: " + uuid));
    }

    @Transactional
    public RegisterDto registerUser(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Username already exists: " + registerRequest.getUsername());
        }
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists: " + registerRequest.getEmail());
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setDateOfBirth(registerRequest.getDateOfBirth());
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
                return new LoginDto(user.getUuid(), user.getUsername(), token);
            }
        } catch (BadCredentialsException e) {
            throw new CustomBadCredentialsException("Wrong credentials bozo");
        } catch (LockedException e) {
            throw new CustomAccountLockedException("Account is currently locked");
        } catch (DisabledException e) {
            throw new AccountDeadException("Account is dead, logging in with this account is prohibited");
        }
        return new LoginDto();
    }

    public PasswordResetDto requestPasswordReset(PasswordResetInquiryRequest request) {
        return passwordResetService.requestPasswordReset(request);
    }

    public PasswordResetDto resetPassword(PasswordResetConfirmationRequest request) {
        return passwordResetService.resetPassword(request);
    }

    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserDto::new).toList();
    }

    public void addOrRemoveUserRole(String userUuid, UserAddOrRemoveRoleRequest request) {
        User user = getUserByUuid(userUuid);
        Role role = roleService.getRoleByUuid(request.getRoleUuid());

        if (UserRoleChangeType.ADD.equals(request.getChangeType())) {
            user.getRoles().add(role);
        } else {
            user.getRoles().remove(role);
        }

        userRepository.save(user);
    }
}
