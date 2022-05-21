package com.puc.barbershop.service;

import com.puc.barbershop.model.Empresa;
import com.puc.barbershop.model.Role;
import com.puc.barbershop.model.User;
import com.puc.barbershop.repository.EmpresaRepository;
import com.puc.barbershop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public List<Empresa> getEmpresas() {
        log.info("Fetchinig all companys!");
        return empresaRepository.findAll();
    }

    @Override
    public Empresa addFuncToEmpresa(String cnpj, String cpf) {
        log.info("Adding funcionario {} to empresa {} ", cpf, cnpj);
        Optional<User> userOptional = userRepository.findByCpf(cpf);
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
    public Empresa deleteFuncionario(String cnpj, String cpf) {
        log.info("Deleting funcionario {} to empresa {} ", cpf, cnpj);
        Optional<User> userOptional = userRepository.findByCpf(cpf);
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
            empresa.getFuncionarios().remove(user);
            return empresa;
        }
    }
}
