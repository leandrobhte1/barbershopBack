package com.puc.barbershop.controller;

import com.puc.barbershop.model.Agenda;
import com.puc.barbershop.model.Servico;
import com.puc.barbershop.model.User;
import com.puc.barbershop.repository.AgendaRepository;
import com.puc.barbershop.service.AgendaService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Slf4j
public class AgendaController {

    private final AgendaService agendaService;
    private final AgendaRepository agendaRepository;
    public int idOpenAgenda = 50000;

    @GetMapping("/agenda/disponivel")
    public List<Agenda> getAgendaDisponiveis(@RequestParam("date") String date) throws ParseException {

        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        List<Agenda> agendaList= agendaService.getAgenda(localDate, "disponivel");

        return agendaList;
    }

    @GetMapping("/agenda/agendados")
    public List<Agenda> getAgendaAgendados(@RequestParam("date") String date) throws ParseException {

        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        List<Agenda> agendaList= agendaService.getAgenda(localDate, "agendado");

        return agendaList;
    }

    @GetMapping("/agenda/admin/agendados")
    public List<?> getAgendaAdminAgendados(@RequestParam("date") String date) throws ParseException {

        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<?> agendaList= agendaService.consultarAgenda(localDate, "agendado");

        return agendaList;
    }

    @GetMapping("/agenda/month")
    public List<Agenda> getAgendaMonth(@RequestParam("date") String date) throws ParseException {

        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate firstDay = localDate.withDayOfMonth(1);
        LocalDate lastDay = localDate.withDayOfMonth(localDate.getMonth().length(localDate.isLeapYear()));

        List<Agenda> agendaList= agendaService.getAgendaMonth(firstDay, lastDay, "disponivel");

        return agendaList;
    }

    @GetMapping("/agenda/month/ocupado")
    public List<Agenda> getAgendaMonthOcupado(@RequestParam("date") String date) throws ParseException {

        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate firstDay = localDate.withDayOfMonth(1);
        LocalDate lastDay = localDate.withDayOfMonth(localDate.getMonth().length(localDate.isLeapYear()));

        List<Agenda> agendaList= agendaService.getAgendaMonth(firstDay, lastDay, "agendado");

        return agendaList;
    }

    @GetMapping("/agenda/history")
    public List<Agenda> getHistoryClient(@RequestParam("idCliente") Long idCliente) {

        return agendaService.getHistory(idCliente);
    }

    @GetMapping("/agenda/all")
    public List<Agenda> getAgenda() {

        return agendaRepository.findAll();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/agenda/criarHorarios")
    public ResponseEntity<?> criarHorariosAgenda(@RequestBody OpenAgenda openAgenda){

        LocalDate dateInicio = LocalDate.parse(openAgenda.getDateInicio(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalTime horarioInicio = LocalTime.parse(openAgenda.getHorarioInicio(), DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalDate dateFim = LocalDate.parse(openAgenda.getDateFim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalTime horarioFim = LocalTime.parse(openAgenda.getHorarioFim(), DateTimeFormatter.ofPattern("HH:mm:ss"));

        LocalDate diaAtual = dateInicio;
        LocalTime horarioAtual = horarioInicio;

        long dias = dateInicio.until(dateFim, ChronoUnit.DAYS);

        for(int id = idOpenAgenda; id <= (idOpenAgenda + dias); id++){
            if(id == 40000) {
                agendaRepository.openAgenda(Long.valueOf(id), openAgenda.getIdEmpresa(), dateInicio, horarioInicio);
            }else{
                if(horarioAtual.isAfter(horarioFim) || horarioAtual == horarioFim){
                    diaAtual = diaAtual.plusDays(1);
                    if(diaAtual.getDayOfWeek().toString() != "SUNDAY"){
                        horarioAtual = horarioInicio;
                        agendaRepository.openAgenda(Long.valueOf(id), openAgenda.getIdEmpresa(), diaAtual, horarioAtual);
                    }
                }else{
                    if(diaAtual.getDayOfWeek().toString() != "SUNDAY"){
                        horarioAtual = horarioAtual.plusMinutes(30);
                        agendaRepository.openAgenda(Long.valueOf(id), openAgenda.getIdEmpresa(), diaAtual, horarioAtual);
                    }
                }
            }
        }

        return ResponseEntity.ok().build();

    }

    @CrossOrigin(origins = "*")
    @PostMapping("/agenda/save")
    public ResponseEntity<Agenda> saveAgenda(@RequestBody AgendaSimilar agenda){

        LocalDate date = LocalDate.parse(agenda.getDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalTime horario = LocalTime.parse(agenda.getHorario(), DateTimeFormatter.ofPattern("HH:mm:ss"));

        Optional<Agenda> agendaOptional= agendaService.getAgenda(date, horario);
        if(!agendaOptional.isPresent()){
            log.info("Agenda não disponível para este dia!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            log.info("Agenda encontrada! ");
            Agenda agenda1 = new Agenda();
            agenda1.setId(agendaOptional.get().getId());
            agenda1.setIdEmpresa(agenda.getIdEmpresa());
            agenda1.setIdCliente(agenda.getIdCliente());
            agenda1.setIdFuncionario(agenda.getIdFuncionario());
            agenda1.setIdServico(agenda.getIdServico());
            agenda1.setDate(LocalDate.parse(agenda.getDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            agenda1.setHorario(LocalTime.parse(agenda.getHorario(), DateTimeFormatter.ofPattern("HH:mm:ss")));
            agenda1.setStatus("agendado");
            agenda1.setNota(agenda.getNota());
            agenda1.setAnotacao(agenda.getAnotacao());
            log.info("Updating agenda with id {} ", agenda1.getId());
            return new ResponseEntity<>(agendaService.saveAgenda(agenda1), HttpStatus.OK);
        }
    }

    @DeleteMapping("/agenda/{idAgenda}")
    public ResponseEntity<?> deleteAgenda(@PathVariable(value = "idAgenda") Long idAgenda){

        return ResponseEntity.ok().body(agendaService.deleteAgenda(idAgenda));
    }

}

@Data
class OpenAgenda {
    private Long idEmpresa;
    private String dateInicio;
    private String dateFim;
    private String horarioInicio;
    private String horarioFim;
}


@Data
class AgendaSimilar {
    private Long idEmpresa;
    private Long idCliente;
    private Long idFuncionario;
    private Long idServico;
    private String date;
    private String horario;
    private String status;
    private double nota;
    private String anotacao;
}
