package br.com.saveup.saveupbackend.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UsuarioDTO {

    private Integer id;

    @NotNull
    private String nome;

    private String email;

    @NotNull
    private String cpf;

    private Boolean emailVerificado;

    private LocalDateTime dtCadastro;

    private LocalDate dtNascimento;

    private BigDecimal vlRenda;

    private String foto;

    private String fotoBase64;

}
