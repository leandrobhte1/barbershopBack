package com.puc.barbershop.repository;

import com.puc.barbershop.model.Agenda;
import com.puc.barbershop.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    List<Agenda> findByDateAndStatus(LocalDate date, String status, Sort sort);

    Optional<Agenda> findByDateAndHorario(LocalDate date, LocalTime horario);

    List<Agenda> findByDateBetweenAndStatus(LocalDate firstDay,LocalDate lastDay, String status, Sort sort);

    @Transactional
    @Modifying
    @Query(
            value =
                    "insert into Agenda (id, id_empresa, id_cliente, id_funcionario, id_servico, date, horario, status, nota, anotacao) values (:id, :idEmpresa, null, null, null, :date, :horario, 'disponivel', 0, null)",
            nativeQuery = true)
    void openAgenda(@Param("id") Long id, @Param("idEmpresa") Long idEmpresa, @Param("date") LocalDate date, @Param("horario") LocalTime horario);
}

