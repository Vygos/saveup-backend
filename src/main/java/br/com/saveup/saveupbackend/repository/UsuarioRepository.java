package br.com.saveup.saveupbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.saveup.saveupbackend.model.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);
    Boolean existsByEmail(String email);
    
}
