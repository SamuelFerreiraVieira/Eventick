package com.example.Eventick.controller.dto;

import java.time.LocalDateTime;

public class ErroResponse {
    private LocalDateTime timestamp;
    private int status;
    private String mensagem;
    private String caminho;

    public ErroResponse(int status, String mensagem, String caminho) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.mensagem = mensagem;
        this.caminho = caminho;
    }


    public LocalDateTime getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public String getMensagem() { return mensagem; }
    public String getCaminho() { return caminho; }
}