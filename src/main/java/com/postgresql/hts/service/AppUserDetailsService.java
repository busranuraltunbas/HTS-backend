package com.postgresql.hts.service;

import com.postgresql.hts.model.UserEntity;
import com.postgresql.hts.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        UserEntity existingUser = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "E-posta adresi bulunamadı: " + email
                        )
                );

        String role = existingUser.getRole() != null
                ? existingUser.getRole().name()
                : "USER";

        return User.builder()
                .username(existingUser.getEmail())
                .password(existingUser.getPassword())
                .authorities(
                        new SimpleGrantedAuthority("ROLE_" + role)
                )
                .disabled(
                        !Boolean.TRUE.equals(
                                existingUser.getIsAccountVerified()
                        )
                )
                .build();
    }
}