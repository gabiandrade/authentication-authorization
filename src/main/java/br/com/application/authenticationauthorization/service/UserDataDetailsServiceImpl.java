package br.com.application.authenticationauthorization.service;

import br.com.application.authenticationauthorization.domain.data.UserDataDetails;
import br.com.application.authenticationauthorization.domain.model.User;
import br.com.application.authenticationauthorization.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Configuration
@AllArgsConstructor
public class UserDataDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findUserByLogin(username).
                orElseThrow(() -> new UsernameNotFoundException("User [" + username + "] not found")));

        return new UserDataDetails(user);

    }

}
