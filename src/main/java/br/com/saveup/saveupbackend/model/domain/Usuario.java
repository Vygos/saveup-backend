package br.com.saveup.saveupbackend.model.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQUENCE")
    @SequenceGenerator(name = "USUARIO_SEQUENCE", sequenceName = "USUARIO_SEQUENCE", allocationSize = 1)
    @Column(name = "ID_USUARIO")
    private Integer id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "DT_NASCIMENTO")
    private LocalDate dtNascimento;

    @Column(name = "VL_RENDA")
    private BigDecimal vlRenda;

    @Column(name = "CPF")
    private String cpf;

    @Column(name = "SECRET", nullable = false)
    private String secret;

    @Column(name = "FOTO")
    private String foto;

    @Column(name = "DT_CADASTRO", nullable = false)
    private LocalDateTime dtCadastro;
}
