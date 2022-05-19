package com.puc.barbershop.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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
    private String cep;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String telefone;
    private String email;
    private Boolean status = true;

}
