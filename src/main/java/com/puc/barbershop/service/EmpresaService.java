package com.puc.barbershop.service;

import com.puc.barbershop.model.Empresa;
import com.puc.barbershop.model.Role;
import com.puc.barbershop.model.Servico;
import com.puc.barbershop.model.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface EmpresaService {
    Empresa saveEmpresa(Empresa empresa);
    Optional<Empresa> getEmpresa(String name);
    Optional<Empresa> getEmpresaById(Long id);
    Page<Empresa> getEmpresas();
    Empresa addFuncToEmpresa(String cnpj, String funcionarioUsername);
    Empresa deleteFuncionario(String cnpj, String funcionarioUsername);
    Empresa saveServicoToEmpresa(Empresa empresa, Servico servico);
    Empresa deleteServicoFromEmpresa(Empresa empresa, Servico servico);
    Page<Empresa> search(String searchTerm,int page,int size);
}
