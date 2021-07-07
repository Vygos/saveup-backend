package br.com.saveup.saveupbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.saveup.saveupbackend.model.domain.TipoDespesa;

public interface TipoDespesaRepository extends JpaRepository<TipoDespesa, Integer> {
    
    List<TipoDespesa> findAll();

    Optional<TipoDespesa> findById(Integer id);

    Page<TipoDespesa> findAllByEmailOrEmailIsNull(Pageable pageable, String email);

    @Query(value = "SELECT TG.NOME FROM TIPO_DESPESA TG WHERE LOWER(TG.NOME) = LOWER(:nome) AND TG.EMAIL = :email " +
            "UNION " +
            "SELECT TG.NOME FROM TIPO_DESPESA TG WHERE LOWER(TG.NOME) = LOWER(:nome) AND TG.EMAIL IS NULL ",
            nativeQuery = true)
    List<String> existsByNomeAndEmail(String nome, String email);

    List<TipoDespesa> findAllByEmailIsNull();

    List<TipoDespesa> findAllByEmail(String email);
}
