package com.puc.barbershop.service;

import com.puc.barbershop.model.Empresa;
import com.puc.barbershop.model.Role;

import java.util.List;
import java.util.Optional;

public interface EmpresaService {
    Empresa saveEmpresa(Empresa empresa);
    Optional<Empresa> getEmpresa(String name);
    List<Empresa> getEmpresas();
    Empresa addFuncToEmpresa(String cnpj, String cpf);
    Empresa deleteFuncionario(String cnpj, String cpf);
}
