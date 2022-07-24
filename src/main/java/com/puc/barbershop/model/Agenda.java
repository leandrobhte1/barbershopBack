package com.puc.barbershop.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "Agenda")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="idEmpresa")
    private Long idEmpresa;
    @Column(name="idCliente")
    private Long idCliente;
    @Column(name="idFuncionario")
    private Long idFuncionario;
    @Column(name="idServico")
    private Long idServico;
    @Column(name="date")
    private LocalDate date;
    @Column(name="horario")
    private LocalTime horario;
    @Column(name="status")
    private String status;
    @Column(name="nota")
    private double nota;
    @Column(name="anotacao")
    private String anotacao;


}
