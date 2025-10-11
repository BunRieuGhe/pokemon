package com.alexpauv.pokemon.config;

import com.alexpauv.pokemon.exception.CustomUsernameNotFoundException;
import com.alexpauv.pokemon.model.user.User;
import com.alexpauv.pokemon.repository.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new CustomUsernameNotFoundException("User not found: " + username));
        return new CustomUserDetails(user);
    }
}
