package br.com.saveup.saveupbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.saveup.saveupbackend.model.domain.TipoGanho;

public interface TipoGanhoRepository extends JpaRepository<TipoGanho, Integer> {
    
    List<TipoGanho> findAll();

    Optional<TipoGanho> findById(Integer id);

    Page<TipoGanho> findAllByEmailOrEmailIsNull(Pageable pageable, String email);

    @Query(value = "SELECT TG.NOME FROM TIPO_GANHO TG WHERE LOWER(TG.NOME) = LOWER(:nome) AND TG.EMAIL = :email " +
            "UNION " +
            "SELECT TG.NOME FROM TIPO_GANHO TG WHERE LOWER(TG.NOME) = LOWER(:nome) AND TG.EMAIL IS NULL ",
            nativeQuery = true)
    List<String> existsByNomeAndEmail(String nome, String email);

    List<TipoGanho> findAllByEmailIsNull();

    List<TipoGanho> findAllByEmail(String email);

}
