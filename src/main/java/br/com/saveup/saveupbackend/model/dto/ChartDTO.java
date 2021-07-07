package br.com.saveup.saveupbackend.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ChartDTO {

	private String mes;
	private BigDecimal totalGanhos;
	private BigDecimal totalDespesas;
	private BigDecimal saldoFinal;
	
}
