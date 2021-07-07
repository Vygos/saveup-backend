package br.com.saveup.saveupbackend.model.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;


@Data
public class FinancaDTO {

	private Integer id;

	private BigDecimal vlBase;

	private BigDecimal vlMargem;

	@NotNull
	@Pattern(regexp = "\\d{1,2}/\\d{4}", message = "Formato inválido, o formato correto é mm/yyyy")
	private String periodo;

	@NotNull
	private UsuarioDTO usuario;

	private List<GanhosDTO> ganhos = new ArrayList<>();

	private List<DespesaDTO> despesas = new ArrayList<>();

}
