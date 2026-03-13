package com.example.Eventick.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data

public class Ingresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String codigoValidacao;
    private LocalDateTime dataInscricao;
    private boolean utilizado = false;


    @Column(columnDefinition = "TEXT")
    private String qrCodePath;



}
