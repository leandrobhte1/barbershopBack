package com.puc.barbershop.service;

import com.puc.barbershop.model.Empresa;
import com.puc.barbershop.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EmpresaServiceImpl implements EmpresaService{

    private final EmpresaRepository empresaRepository;

    @Override
    public Empresa saveEmpresa(Empresa empresa) {
        log.info("Saving new company {} to the database.: ", empresa.getName());
        empresaRepository.save(empresa);
        return null;
    }

    @Override
    public Empresa getEmpresa(String name) {
        log.info("Fetchinig company {} ", name);
        return empresaRepository.findByName(name);
    }

    @Override
    public List<Empresa> getEmpresas() {
        log.info("Fetchinig all companys!");
        return empresaRepository.findAll();
    }
}
