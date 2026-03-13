package com.example.Eventick.controller;

import com.example.Eventick.model.Evento;
import com.example.Eventick.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/eventos")


public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @PostMapping
    public Evento criarEvento(@RequestBody Evento evento){
        return eventoRepository.save(evento);
    }

    @GetMapping
    public List<Evento> listarEventos(){
        return eventoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> buscarPorId(@PathVariable Long id){
        return eventoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
