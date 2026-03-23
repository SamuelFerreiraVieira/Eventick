package com.example.Eventick.controller;

import com.example.Eventick.service.IngressoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingressos")
public class CheckinController {

    @Autowired
    private IngressoService ingressoService;

    @PatchMapping("/check-in/{codigo}")
    public ResponseEntity<String> checkIn(@PathVariable String codigo) {
        try {
            String mensagem = ingressoService.realizarCheckIn(codigo);
            return ResponseEntity.ok(mensagem);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

