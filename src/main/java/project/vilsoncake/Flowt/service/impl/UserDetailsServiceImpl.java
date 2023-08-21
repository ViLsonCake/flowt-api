package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.entity.Role;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.repository.UserRepository;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Search user
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User '%s' not found", username)));
        // Convert to UserDetails
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(rolesToAuthorities(user.getRoles()))
                .build();
    }

    private Collection<GrantedAuthority> rolesToAuthorities(Set<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());
    }
}
