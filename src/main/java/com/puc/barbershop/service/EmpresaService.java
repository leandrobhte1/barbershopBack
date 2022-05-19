package com.puc.barbershop.service;

import com.puc.barbershop.model.Empresa;
import com.puc.barbershop.model.Role;

import java.util.List;

public interface EmpresaService {
    Empresa saveEmpresa(Empresa empresa);
    Empresa getEmpresa(String name);
    List<Empresa> getEmpresas();
}
