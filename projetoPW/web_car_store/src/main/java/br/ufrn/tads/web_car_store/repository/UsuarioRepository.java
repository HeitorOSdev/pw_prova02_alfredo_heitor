package br.ufrn.tads.web_car_store.repository;

import br.ufrn.tads.web_car_store.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Método para buscar um usuário pelo seu nome de usuário (login)
    Optional<Usuario> findByUsername(String username);
}