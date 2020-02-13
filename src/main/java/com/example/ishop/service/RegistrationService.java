package com.example.ishop.service;

import com.example.ishop.domain.Role;
import com.example.ishop.domain.SortParam;
import com.example.ishop.domain.UserEntity;
import com.example.ishop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String registrationUser(UserEntity user) {
        UserEntity loadUser = userRepository.findByUsername(user.getUsername());

        if (loadUser == null) {
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setSortParam(SortParam.NAME_ASC);
            userRepository.save(user);
            return "redirect:/login";
        }

        return "registration";
    }

}
