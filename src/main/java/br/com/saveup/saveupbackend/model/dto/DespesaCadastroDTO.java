package br.com.saveup.saveupbackend.model.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DespesaCadastroDTO {
    
    @NotNull
    private Integer id_financa;

    @NotNull
    private String nome;

    @NotNull 
    private BigDecimal valor;

    @NotNull
    private Integer id_tipo;
}
