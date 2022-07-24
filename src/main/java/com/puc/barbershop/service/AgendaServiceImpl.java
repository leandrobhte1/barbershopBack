package com.puc.barbershop.service;

import com.puc.barbershop.model.Agenda;
import com.puc.barbershop.repository.AgendaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AgendaServiceImpl implements AgendaService{

    private final AgendaRepository agendaRepository;

    @Override
    public Agenda saveAgenda(Agenda agenda) {
        log.info("Saving new agenda {} to the database.: ", agenda.getDate());
        agendaRepository.save(agenda);
        return agenda;
    }

    @Override
    public Optional<Agenda> getAgenda(LocalDate date, LocalTime horario) {
        log.info("Fetching agenda {} - {} ", date, horario);
        return agendaRepository.findByDateAndHorario(date, horario);
    }

    @Override
    public List<Agenda> getAgenda(LocalDate date, String status) {
        log.info("Fetchinig agenda of day {} ", date);
        return agendaRepository.findByDateAndStatus(date, status, Sort.by(Sort.Direction.ASC, "horario"));
    }

    @Override
    public List<Agenda> getAgendaMonth(LocalDate firstDay, LocalDate lastDay, String status) {
        log.info("Fetchinig agenda of month {} - {} ", firstDay, lastDay);
        return agendaRepository.findByDateBetweenAndStatus(firstDay, lastDay, status, Sort.by(Sort.Direction.ASC, "date").and(Sort.by("horario")));
    }

    @Override
    public void openAgenda(Long id, Long idEmpresa, LocalDate date, LocalTime horario) {
        log.info("Opening agenda of {} - {} ", date, horario);
        agendaRepository.openAgenda(id, idEmpresa, date, horario);
    }

    @Override
    public Agenda deleteAgenda(Long idAgenda) {
        log.info("Deleting agenda {}", idAgenda);

        Optional<Agenda> agendaOptional = agendaRepository.findById(idAgenda);
        if(!agendaOptional.isPresent()) {
            throw new RuntimeException("Agenda not found in database!");
        }else{
            Agenda agenda = agendaOptional.get();
            agendaRepository.delete(agenda);
            return agenda;
        }
    }
}
