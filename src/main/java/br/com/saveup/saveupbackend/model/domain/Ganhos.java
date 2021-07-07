package br.com.saveup.saveupbackend.model.domain;

import java.math.BigDecimal;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "GANHOS")
public class Ganhos {
   
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GANHOS_SEQUENCE")
    @SequenceGenerator(name = "GANHOS_SEQUENCE", sequenceName = "GANHOS_SEQUENCE", allocationSize = 1)
    @Column(name = "id_ganho")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_financa")
    private Financa financa;

    private String nome;

    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "id_tipo_ganho")
    private TipoGanho tipoGanho;
}
