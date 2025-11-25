package com.Rootale.security.service;

import com.Rootale.member.repository.UserRepository;
import com.Rootale.member.entity.User;
import com.Rootale.member.entity.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = usersRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUser(u.getUsersId(), u.getUsername(), u.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}