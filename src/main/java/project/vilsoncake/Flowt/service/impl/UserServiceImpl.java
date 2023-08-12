package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.dto.RegistrationDto;
import project.vilsoncake.Flowt.entity.Role;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.exception.EmailAlreadyExistException;
import project.vilsoncake.Flowt.exception.PasswordsNotMatchException;
import project.vilsoncake.Flowt.exception.UsernameAlreadyExistException;
import project.vilsoncake.Flowt.repository.UserRepository;
import project.vilsoncake.Flowt.service.UserService;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static project.vilsoncake.Flowt.entity.Role.USER;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity addUser(RegistrationDto registrationDto) {
        // Handle exception
        if (userRepository.existsUserByUsername(registrationDto.getUsername())) throw new UsernameAlreadyExistException("Username already exists");
        if (userRepository.existsUserByEmail(registrationDto.getEmail())) throw new EmailAlreadyExistException("Email already exists");
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) throw new PasswordsNotMatchException("Passwords don't match");

        // Create new user and save
        UserEntity user = new UserEntity();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.getRoles().add(USER);
        userRepository.save(user);

        log.info("User '{}' saved", user.getUsername());

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User '%s' not found", username)));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(rolesToAuthorities(user.getRoles()))
                .build();
    }

    private Collection<GrantedAuthority> rolesToAuthorities(Set<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());
    }

    @Override
    public UserEntity getAuthenticatedUser(String authHeader) {
        return null;
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));
    }
}
