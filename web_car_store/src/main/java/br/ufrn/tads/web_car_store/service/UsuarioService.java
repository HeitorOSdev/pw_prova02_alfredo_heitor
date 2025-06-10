package br.ufrn.tads.web_car_store.service;

import br.ufrn.tads.web_car_store.domain.Usuario;
import br.ufrn.tads.web_car_store.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario save(Usuario u) {
        // Codifica a senha antes de salvar no banco
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return repository.save(u);
    }
}