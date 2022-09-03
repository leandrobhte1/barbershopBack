package com.puc.barbershop.service;

import com.puc.barbershop.model.Agenda;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AgendaService {
    Agenda saveAgenda(Agenda agenda);
    List<Agenda> getAgenda(LocalDate date, String status);
    List<?> getHistory(Long idCliente);
    List<Agenda> getAgendaMonth(LocalDate firstDay, LocalDate lastDay, String status);
    Agenda deleteAgenda(Long idAgenda);
    void openAgenda(Long id, Long idEmpresa, LocalDate date, LocalTime horario);
    Optional<Agenda> getAgenda(LocalDate date, LocalTime horario);
    List<?> consultarAgenda(LocalDate date, String status);
    List<?> consultaHistorico(LocalDate date, String status);
    List<?> consultaAgendamentosFuturos(LocalDate date, String status);
}
