package com.puc.barbershop.service;

import com.puc.barbershop.model.Empresa;
import com.puc.barbershop.model.Role;
import com.puc.barbershop.model.Servico;
import com.puc.barbershop.model.User;
import com.puc.barbershop.repository.EmpresaRepository;
import com.puc.barbershop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EmpresaServiceImpl implements EmpresaService{

    private final EmpresaRepository empresaRepository;
    private final UserRepository userRepository;

    @Override
    public Empresa saveEmpresa(Empresa empresa) {
        log.info("Saving new company {} to the database.: ", empresa.getName());
        empresaRepository.save(empresa);
        return empresa;
    }

    @Override
    public Optional<Empresa> getEmpresa(String name) {
        log.info("Fetchinig company {} ", name);
        return empresaRepository.findByName(name);
    }

    @Override
    public Optional<Empresa> getEmpresaById(Long id) {
        log.info("Fetchinig company {} ", id);
        return empresaRepository.findById(id);
    }

    @Override
    public Page<Empresa> getEmpresas() {
        log.info("Fetchinig all companys!");
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "name");
        return new PageImpl<>(
                empresaRepository.findAll(),
                pageRequest, size);
    }

    @Override
    public Empresa addFuncToEmpresa(String cnpj, String funcionarioUsername) {
        log.info("Adding funcionario {} to empresa {} ", funcionarioUsername, cnpj);
        Optional<User> userOptional = userRepository.findByUsername(funcionarioUsername);
        User user = new User();
        if(!userOptional.isPresent()) {
            throw new RuntimeException("User not found in database!");
        }else{
            user = userOptional.get();
        }
        Optional<Empresa> empresaOptional = empresaRepository.findByCnpj(cnpj);
        if(!empresaOptional.isPresent()) {
            throw new RuntimeException("Empresa not found in database!");
        }else{
            Empresa empresa = empresaOptional.get();
            empresa.getFuncionarios().add(user);
            return empresa;
        }
    }

    @Override
    public Empresa deleteFuncionario(String cnpj, String funcionarioUsername) {
        log.info("Deleting funcionario {} to empresa {} ", funcionarioUsername, cnpj);
        Optional<User> userOptional = userRepository.findByUsername(funcionarioUsername);
        User user = new User();
        if(!userOptional.isPresent()) {
            throw new RuntimeException("Funcionario not found in database!");
        }else{
            user = userOptional.get();
        }
        Optional<Empresa> empresaOptional = empresaRepository.findByCnpj(cnpj);
        if(!empresaOptional.isPresent()) {
            throw new RuntimeException("Empresa not found in database!");
        }else{
            Empresa empresa = empresaOptional.get();
            empresa.getFuncionarios().remove(user);
            return empresa;
        }
    }

    @Override
    public Empresa saveServicoToEmpresa(Empresa empresa, Servico servico) {
        empresa.getServicos().add(servico);
        return empresa;
    }

    @Override
    public Empresa deleteServicoFromEmpresa(Empresa empresa, Servico servico) {
        empresa.getServicos().remove(servico);
        return empresa;
    }

    @Override
    public Page<Empresa> search(
            String searchTerm,
            int page,
            int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "name");

        return empresaRepository.search(
                searchTerm.toLowerCase(),
                pageRequest);
    }
}
