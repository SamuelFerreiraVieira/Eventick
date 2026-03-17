package com.example.Eventick.controller;

import com.example.Eventick.model.Evento;
import com.example.Eventick.repository.EventoRepository;
import com.example.Eventick.repository.IngressoRepository; // Import necessário
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private IngressoRepository ingressoRepository; // Injetamos para contar os ingressos

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


    @GetMapping("/{id}/dashboard")
    public ResponseEntity<Map<String, Object>> obterDashboard(@PathVariable Long id) {
        return eventoRepository.findById(id).map(evento -> {


            long totalInscritos = ingressoRepository.countByEventoId(id);


            long totalCheckins = ingressoRepository.countByEventoIdAndUtilizadoTrue(id);


            long vagasRestantes = (evento.getVagasTotais() != null) ?
                    (evento.getVagasTotais() - totalInscritos) : 0;

            Map<String, Object> stats = new HashMap<>();
            stats.put("evento", evento.getNome());
            stats.put("vagasTotais", evento.getVagasTotais());
            stats.put("totalInscritos", totalInscritos);
            stats.put("totalCheckins", totalCheckins);
            stats.put("vagasDisponiveis", Math.max(0, vagasRestantes));

            return ResponseEntity.ok(stats);
        }).orElse(ResponseEntity.notFound().build());
    }
}