package com.puc.barbershop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "Empresa")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String dono;
    private String cnpj;
    private String descricao;
    private String urlImagem = "http://danielsilvacontabilidade.com.br/empresa2.png";
    private LocalTime horaInicio;
    private LocalTime horaFim;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<User> funcionarios = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Servico> servicos = new ArrayList<>();
    private String cep;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String telefone;
    private String email;
    private Boolean status = true;

}
