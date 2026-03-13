package com.example.Eventick.controller;

import com.example.Eventick.model.Ingresso;
import com.example.Eventick.repository.IngressoRepository; // Adicione este import
import com.example.Eventick.service.IngressoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscricoes")
public class IngressoController {

    @Autowired
    private IngressoService ingressoService;

    @Autowired
    private IngressoRepository ingressoRepository; // Você precisa injetar o repository aqui

    @PostMapping
    public ResponseEntity<Ingresso> criarIngresso(@RequestBody Ingresso ingresso) {
        try {
            Ingresso novoIngresso = ingressoService.realizarInscricao(ingresso);
            return ResponseEntity.ok(novoIngresso);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public List<Ingresso> listarInscricoes() {
        return ingressoRepository.findAll();
    }

}