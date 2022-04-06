package br.com.application.authenticationauthorization.api.controller;

import br.com.application.authenticationauthorization.domain.model.User;
import br.com.application.authenticationauthorization.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/lets-code")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/save")
    public ResponseEntity save(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok().body(userRepository.save(user));
    }
}
