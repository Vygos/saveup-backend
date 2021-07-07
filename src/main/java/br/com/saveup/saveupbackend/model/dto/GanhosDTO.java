package br.com.saveup.saveupbackend.model.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class GanhosDTO {
	
	private Integer id;

	@NotNull
    private String nome;

	@NotNull
    private BigDecimal valor;

	@NotNull
    private TipoGanhoDTO tipo;

	private FinancaThinDTO financa;

}
