package br.com.application.authenticationauthorization.domain.repository;

import br.com.application.authenticationauthorization.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByLogin(String login);
}
