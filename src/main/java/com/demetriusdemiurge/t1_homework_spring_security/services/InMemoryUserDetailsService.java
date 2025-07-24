package com.demetriusdemiurge.t1_homework_spring_security.services;


import com.demetriusdemiurge.t1_homework_spring_security.entities.Role;
import com.demetriusdemiurge.t1_homework_spring_security.entities.User;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InMemoryUserDetailsService implements UserDetailsService {

    private final Map<String, User> usersByLogin = new ConcurrentHashMap<>();
    private final Map<String, User> usersByEmail = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();
    private final PasswordEncoder passwordEncoder;

    public InMemoryUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {

        save(User.builder().login("admin").email("admin@example.com").password("admin123").roles(Set.of(Role.ADMIN)).build());
        save(User.builder().login("premium").email("premium@example.com").password("premium123").roles(Set.of(Role.PREMIUM_USER)).build());
        save(User.builder().login("guest").email("guest@example.com").password("guest123").roles(Set.of(Role.GUEST)).build());
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        if (!usersByLogin.containsKey(login)) {
            throw new UsernameNotFoundException("User not found with login: " + login);
        }
        return usersByLogin.get(login);
    }

    public void save(User user) {
        if (usersByLogin.containsKey(user.getLogin())) {
            throw new IllegalArgumentException("Login is already taken!");
        }
        if (usersByEmail.containsKey(user.getEmail())) {
            throw new IllegalArgumentException("Email is already taken!");
        }

        user.setId(idCounter.incrementAndGet());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        usersByLogin.put(user.getLogin(), user);
        usersByEmail.put(user.getEmail(), user);
    }
}