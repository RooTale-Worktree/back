package com.LiveToon.users.security;

import com.LiveToon.repository.UsersRepository;
import com.LiveToon.users.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users u = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUser(u.getUsersId(), u.getUsername(), u.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
