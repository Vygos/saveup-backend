package br.com.saveup.saveupbackend.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

@Data
public class TipoDespesaDTO {
    
    private Integer id;

    @NotBlank
    @NotNull
    private String nome;

    @JsonIgnore
    private String email;
}
