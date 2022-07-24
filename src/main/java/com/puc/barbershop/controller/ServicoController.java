package com.puc.barbershop.controller;

import com.puc.barbershop.model.Empresa;
import com.puc.barbershop.model.Servico;
import com.puc.barbershop.service.ServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ServicoController {

    private final ServicoService servicoService;

    @GetMapping("/servicos")
    public Page<Servico> getServicos(){

        return servicoService.getServicos();
    }

    @GetMapping("/servico/{nome}")
    public ResponseEntity<Servico>getServico(@PathVariable(value = "nome") String nome){

        Optional<Servico> servicoOptional= servicoService.getServico(nome);
        if(!servicoOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            Servico servico = servicoOptional.get();
            return new ResponseEntity<Servico>(servico, HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/servico/save")
    public ResponseEntity<Servico> saveServico(@RequestBody Servico servico){

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/servico/save").toUriString());

        return ResponseEntity.created(uri).body(servicoService.saveServico(servico));
    }

    @DeleteMapping("/servico/{idServico}")
    public ResponseEntity<?> deleteServico(@PathVariable(value = "idServico") Long idServico){

        return ResponseEntity.ok().body(servicoService.deleteServico(idServico));
    }

}
