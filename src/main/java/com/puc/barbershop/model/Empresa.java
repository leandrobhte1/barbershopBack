package com.puc.barbershop.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "Barbershop")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Empresa {

    @Id
    @SequenceGenerator(name = "empresa_sequence", sequenceName = "empresa_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "empresa_sequence")
    private Long id;
    private String name;
    private String cnpj;
    private String descricao;
    private String urlImagem;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<User> funcionarios = new ArrayList<>();
//    @ManyToMany(fetch = FetchType.EAGER)
//    private Collection<Servico> servicos = new ArrayList<>();
    private String cep;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String telefone;
    private String email;
    private Boolean status = true;

}
