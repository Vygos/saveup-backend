package br.com.saveup.saveupbackend.service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.saveup.saveupbackend.handlers.NegocioException;
import br.com.saveup.saveupbackend.model.domain.Despesa;
import br.com.saveup.saveupbackend.model.domain.Financa;
import br.com.saveup.saveupbackend.model.domain.Ganhos;
import br.com.saveup.saveupbackend.model.dto.ChartDTO;
import br.com.saveup.saveupbackend.model.dto.FinancaDTO;
import br.com.saveup.saveupbackend.repository.DespesaRepository;
import br.com.saveup.saveupbackend.repository.FinancaRepository;
import br.com.saveup.saveupbackend.repository.GanhosRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FinancaService {

	private final FinancaRepository financaRepository;
	private final GanhosRepository ganhosRepository;
	private final DespesaRepository despesaRepository;
	private final ModelMapper mapper;

	public List<FinancaDTO> findByUserIdAndYear(Integer id, String ano) {
		return financaRepository.findAllByUserId(id).stream().filter(financa -> financa.getPeriodo().contains(ano))
				.sorted(getFinancaComparatorByPeriod()).map(financa -> mapper.map(financa, FinancaDTO.class))
				.collect(Collectors.toList());
	}

	public List<String> listYears(Integer id) {
		List<Financa> financas = financaRepository.findAllByUserId(id);

		return financas.stream()
				.map(financa -> financa.getPeriodo().split("/")[1])
				.sorted((a1, a2) -> a2.compareTo(a1))
				.distinct().collect(Collectors.toList());
	}

	public List<ChartDTO> getDataCharts(Integer id, String ano) {
		List<Financa> financas = financaRepository.findAllByUserId(id).stream()
				.filter(financa -> financa.getPeriodo().contains(ano)).sorted(getFinancaComparatorByPeriod())
				.collect(Collectors.toList());

		return financas.stream().map(financa -> {
			String mes = financa.getPeriodo().split("/")[0];

			BigDecimal totalGanhos = financa.getGanhos().stream().map(ganho -> ganho.getValor()).reduce(BigDecimal.ZERO,
					(acc, value) -> acc.add(value));

			BigDecimal totalDespesas = financa.getDespesas().stream().map(despesa -> despesa.getValor())
					.reduce(BigDecimal.ZERO, (acc, value) -> acc.add(value));

			BigDecimal saldoFinal = totalGanhos.subtract(totalDespesas);

			return new ChartDTO(mes, totalGanhos, totalDespesas, saldoFinal);
		}).collect(Collectors.toList());
	}

	public FinancaDTO create(FinancaDTO financaDTO) {

		if (!financaRepository.findAllByUserIdAndPeriod(financaDTO.getUsuario().getId(), financaDTO.getPeriodo())
				.isEmpty()) {
			throw new NegocioException("Esse periodo já existe");
		}

		Financa financaEntity = mapper.map(financaDTO, Financa.class);

		Financa novaFinanca = financaRepository.save(financaEntity);

		atualizarGanhosEDespesas(novaFinanca);

		return mapper.map(financaRepository.save(novaFinanca), FinancaDTO.class);
	}

	private void atualizarGanhosEDespesas(Financa novaFinanca) {
		List<Ganhos> ganhos = novaFinanca.getGanhos();
		List<Despesa> despesas = novaFinanca.getDespesas();

		ganhos.forEach(despesa -> despesa.setFinanca(novaFinanca));
		despesas.forEach(ganho -> ganho.setFinanca(novaFinanca));

		ganhosRepository.saveAll(ganhos);
		despesaRepository.saveAll(despesas);
	}

	public FinancaDTO atualizar(FinancaDTO financa) {
		Financa financaEntity = mapper.map(financa, Financa.class);

		return mapper.map(financaRepository.save(financaEntity), FinancaDTO.class);
	}

	private Comparator<Financa> getFinancaComparatorByPeriod() {
		return (f1, f2) -> f1.getPeriodo().compareTo(f2.getPeriodo());
	}
}
