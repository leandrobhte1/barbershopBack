package com.puc.barbershop.controller;

import com.puc.barbershop.model.Empresa;
import com.puc.barbershop.model.User;
import com.puc.barbershop.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class EmpresaController {

    private final EmpresaService empresaService;

    @GetMapping("/empresas")
    public ResponseEntity<List<Empresa>> getEmpresas(){

        return ResponseEntity.ok().body(empresaService.getEmpresas());
    }

    @GetMapping("/empresa/{name}")
    public ResponseEntity<Empresa>getEmpresa(@PathVariable(value = "name") String name){

        return ResponseEntity.ok().body(empresaService.getEmpresa(name));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/empresa/save")
    public ResponseEntity<Empresa>saveEmpresa(@RequestBody Empresa empresa){

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/empresa/save").toUriString());

        return ResponseEntity.created(uri).body(empresaService.saveEmpresa(empresa));
    }

}
