package br.com.saveup.saveupbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.saveup.saveupbackend.model.domain.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Integer> {
 
    List<Despesa> findAll();
    Optional<Despesa> findById(Integer id);
}
