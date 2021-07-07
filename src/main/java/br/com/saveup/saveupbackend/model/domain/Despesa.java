package br.com.saveup.saveupbackend.model.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "DESPESA")
public class Despesa {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DESPESA_SEQUENCE")
    @SequenceGenerator(name = "DESPESA_SEQUENCE", sequenceName = "DESPESA_SEQUENCE", allocationSize = 1)
    @Column(name = "id_despesa")
    private Integer id;

    private String nome;
    
    private BigDecimal Valor;
    
    @ManyToOne
    @JoinColumn(name = "id_tipo_despesa")
    private TipoDespesa tipoDespesa;

    @ManyToOne
    @JoinColumn(name = "id_financa")
    private Financa financa;

	
}
