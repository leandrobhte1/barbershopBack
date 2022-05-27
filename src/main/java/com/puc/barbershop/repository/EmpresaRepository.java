package com.puc.barbershop.repository;

import com.puc.barbershop.model.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Optional<Empresa> findByName(String name);
    Optional<Empresa> findByCnpj(String cnpj);

    @Query("SELECT e FROM Empresa e " +
            "WHERE LOWER(e.name) like CONCAT('%',:searchTerm,'%') ")
    Page<Empresa> search(
            @Param("searchTerm") String searchTerm,
            Pageable pageable);
}
