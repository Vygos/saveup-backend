package br.com.saveup.saveupbackend.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TIPO_DESPESA")
public class TipoDespesa {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPO_DESPESA_SEQUENCE")
    @SequenceGenerator(name = "TIPO_DESPESA_SEQUENCE", sequenceName = "TIPO_DESPESA_SEQUENCE", allocationSize = 1)
    @Column(name = "id_tipo_despesa")
    private Integer id;

    private String nome;

    private String email;
}



