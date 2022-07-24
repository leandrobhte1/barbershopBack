package com.puc.barbershop.service;

import com.puc.barbershop.model.Servico;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ServicoService {
    Servico saveServico(Servico servico);
    Optional<Servico> getServico(String nome);
    Optional<Servico> getServico(Long id);
    Page<Servico> getServicos();
    Servico deleteServico(Long idServico);
}
