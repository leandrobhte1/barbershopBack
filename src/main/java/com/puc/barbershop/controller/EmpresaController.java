package com.puc.barbershop.controller;

import com.puc.barbershop.model.Empresa;
import com.puc.barbershop.model.Role;
import com.puc.barbershop.model.User;
import com.puc.barbershop.service.EmpresaService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class EmpresaController {

    private final EmpresaService empresaService;

    @GetMapping("/empresas")
    public Page<Empresa> getEmpresas(){

        return empresaService.getEmpresas();
    }

    @GetMapping("/empresas/search")
    public Page<Empresa> search(
            @RequestParam("searchTerm") String searchTerm,
            @RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0") int page,
            @RequestParam(
                    value = "size",
                    required = false,
                    defaultValue = "10") int size) {
        return empresaService.search(searchTerm, page, size);

    }

    @CrossOrigin(origins = "*")
    @PostMapping("/empresa/addFuncionario")
    public ResponseEntity<Empresa>addFuncionario(@RequestBody FuncToEmpresa funcToEmpresa){

        return ResponseEntity.ok().body(empresaService.addFuncToEmpresa(funcToEmpresa.getCnpj(), funcToEmpresa.getCpf()));
    }

    @DeleteMapping("/empresa/deleteFuncionario")
    public ResponseEntity<?> deleteFuncionario(@RequestBody FuncToEmpresa funcToEmpresa){

        return ResponseEntity.ok().body(empresaService.deleteFuncionario(funcToEmpresa.getCnpj(), funcToEmpresa.getCpf()));
    }

    @GetMapping("/empresa/{name}")
    public ResponseEntity<Empresa>getEmpresa(@PathVariable(value = "name") String name){

        Optional<Empresa> empresaOptional= empresaService.getEmpresa(name);
        if(!empresaOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            Empresa empresa = empresaOptional.get();
            return new ResponseEntity<Empresa>(empresa, HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/empresa/save")
    public ResponseEntity<Empresa>saveEmpresa(@RequestBody Empresa empresa){

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/empresa/save").toUriString());

        return ResponseEntity.created(uri).body(empresaService.saveEmpresa(empresa));
    }

}

@Data
class FuncToEmpresa {
    private String cnpj;
    private String cpf;
}
