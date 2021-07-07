package br.com.saveup.saveupbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.saveup.saveupbackend.model.domain.Ganhos;

public interface GanhosRepository extends JpaRepository<Ganhos, Integer> {
 
    List<Ganhos> findAll();
    Optional<Ganhos> findById(Integer id);
}
