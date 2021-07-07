package br.com.saveup.saveupbackend.model.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DespesaDTO {

	private Integer id;

	@NotNull
	private String nome;

	@NotNull
	private BigDecimal valor;

	@NotNull
	private TipoDespesaDTO tipo;

	private FinancaThinDTO financa;
}
