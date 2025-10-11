package com.alexpauv.pokemon.service.login;

import com.alexpauv.pokemon.repository.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginAttemptService {
    private final LoginProperties loginProperties;

    private final UserRepository userRepository;

    public LoginAttemptService(LoginProperties loginProperties, UserRepository userRepository) {
        this.loginProperties = loginProperties;
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

            if (user.getFailedLoginAttempts() >= loginProperties.maxLoginAttempts()) {
                user.lockAccount(loginProperties.lockDurationMinutes());
            }

            userRepository.save(user);
        });
    }
}
