package com.ifsp.PeGasus.Repository;

import com.ifsp.PeGasus.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNome(String nome);
    Optional<User> findByNomeAndSenha(String nome, String senha);
}
