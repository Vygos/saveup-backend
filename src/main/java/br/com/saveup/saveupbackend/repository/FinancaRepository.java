package br.com.saveup.saveupbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.saveup.saveupbackend.model.domain.Financa;

public interface FinancaRepository extends JpaRepository<Financa, Integer> {

	@Query(value = "SELECT * FROM FINANCA F WHERE F.ID_USUARIO = :id", nativeQuery = true)
	List<Financa> findAllByUserId(Integer id);

	@Query(value = "SELECT * FROM FINANCA F WHERE F.ID_USUARIO = :id AND F.PERIODO = :periodo", nativeQuery = true)
	List<Financa> findAllByUserIdAndPeriod(Integer id, String periodo);

}
