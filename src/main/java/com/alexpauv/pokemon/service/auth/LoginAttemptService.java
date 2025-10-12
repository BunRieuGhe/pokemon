package com.alexpauv.pokemon.service.auth;

import com.alexpauv.pokemon.repository.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginAttemptService {
    private final SecurityProperties securityProperties;

    private final UserRepository userRepository;

    public LoginAttemptService(SecurityProperties securityProperties, UserRepository userRepository) {
        this.securityProperties = securityProperties;
        this.userRepository = userRepository;
    }

    @Transactional
    public void doLoginSuccessAction(String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            if (user.getFailedLoginAttempts() > 0 || user.getLockoutTime() != null) {
                user.resetFailedAttempts();
                userRepository.save(user);
            }
        });
    }

    @Transactional
    public void doLoginFailureAction(String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            user.incrementFailedAttempts();

            if (user.getFailedLoginAttempts() >= securityProperties.maxLoginAttempts()) {
                user.lockAccount(securityProperties.lockDurationMinutes());
            }

            userRepository.save(user);
        });
    }
}
