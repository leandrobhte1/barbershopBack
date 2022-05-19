package com.puc.barbershop.repository;

import com.puc.barbershop.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Empresa findByName(String name);
}
