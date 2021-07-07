package br.com.saveup.saveupbackend.model.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Data;

import static javax.persistence.CascadeType.*;

@Data
@Entity
@Table(name = "FINANCA")
public class Financa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FINANCA_SEQUENCE")
    @SequenceGenerator(name = "FINANCA_SEQUENCE", sequenceName = "FINANCA_SEQUENCE", allocationSize = 1)
    @Column(name = "id_financa")
    private Integer id;

    @Column(name = "vl_base")
    private BigDecimal vlBase;

    @Column(name = "vl_margem")
    private BigDecimal vlMargem;

    private String periodo;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "financa", cascade = {PERSIST, MERGE, REMOVE}, orphanRemoval = true)
    List<Ganhos> ganhos = new ArrayList<>();

    @OneToMany(mappedBy = "financa", cascade = {PERSIST, MERGE, REMOVE}, orphanRemoval = true)
    List<Despesa> despesas = new ArrayList<>();

}
