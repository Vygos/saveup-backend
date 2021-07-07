package br.com.saveup.saveupbackend.model.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UsuarioCadastroDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String email;

    @NotBlank
    private String secret;

}
