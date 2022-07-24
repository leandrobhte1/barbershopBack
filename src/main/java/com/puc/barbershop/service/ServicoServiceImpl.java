package com.puc.barbershop.service;

import com.puc.barbershop.model.Empresa;
import com.puc.barbershop.model.Servico;
import com.puc.barbershop.model.User;
import com.puc.barbershop.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ServicoServiceImpl implements ServicoService{

    private final ServiceRepository serviceRepository;

    @Override
    public Servico saveServico(Servico servico) {
        log.info("Saving new service {} to the database.: ", servico.getNome());
        serviceRepository.save(servico);
        return servico;
    }

    @Override
    public Optional<Servico> getServico(String nome) {
        log.info("Fetchinig service {} ", nome);
        return serviceRepository.findByNome(nome);
    }

    @Override
    public Optional<Servico> getServico(Long id) {
        log.info("Fetchinig service {} ", id);
        return serviceRepository.findById(id);
    }

    @Override
    public Page<Servico> getServicos() {
        log.info("Fetchinig all services!");
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "name");
        return new PageImpl<>(
                serviceRepository.findAll(),
                pageRequest, size);
    }

    @Override
    public Servico deleteServico(Long idServico) {
        log.info("Deleting service {}", idServico);

        Optional<Servico> servicoOptional = serviceRepository.findById(idServico);
        if(!servicoOptional.isPresent()) {
            throw new RuntimeException("Servico not found in database!");
        }else{
            Servico servico = servicoOptional.get();
            serviceRepository.delete(servico);
            return servico;
        }
    }
}
