package com.example.Eventick.service;

import com.example.Eventick.model.Ingresso;
import com.example.Eventick.repository.EventoRepository;
import com.example.Eventick.repository.IngressoRepository;
import com.example.Eventick.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Base64;

@Service
public class IngressoService {

    @Autowired
    private IngressoRepository ingressoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private QRCodeService qrCodeService;

    @Autowired
    private EmailService emailService;

    @Transactional
    public String realizarCheckIn(String codigoValidacao) {

        Ingresso ingresso = ingressoRepository.findByCodigoValidacao(codigoValidacao)
                .orElseThrow(() -> new RuntimeException("Ingresso inválido ou não encontrado!"));


        if (ingresso.isUtilizado()) {
            throw new RuntimeException("Alerta: Este ingresso já foi utilizado!");
        }


        ingresso.setUtilizado(true);
        ingressoRepository.save(ingresso);

        return "Check-in realizado com sucesso! Bem-vindo(a), " + ingresso.getUsuario().getNome();
    }

    @Transactional
    public Ingresso realizarInscricao(Ingresso ingresso) throws Exception {


        var usuario = usuarioRepository.findById(ingresso.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        var evento = eventoRepository.findById(ingresso.getEvento().getId())
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));


        long inscritosAtuais = ingressoRepository.countByEventoId(evento.getId());

        if (evento.getVagasTotais() != null && inscritosAtuais >= evento.getVagasTotais()) {
            throw new RuntimeException("Desculpe, as vagas para o evento '" + evento.getNome() + "' esgotaram!");
        }



        String codigoUnico = UUID.randomUUID().toString();
        ingresso.setCodigoValidacao(codigoUnico);

        byte[] qrCodeBytes = qrCodeService.gerarQRCode(codigoUnico);
        ingresso.setQrCodePath(Base64.getEncoder().encodeToString(qrCodeBytes));


        ingresso.setUsuario(usuario);
        ingresso.setEvento(evento);
        ingresso.setDataInscricao(LocalDateTime.now());
        ingresso.setUtilizado(false);


        Ingresso ingressoSalvo = ingressoRepository.save(ingresso);


        try {
            emailService.enviarEmailConfirmacao(
                    usuario.getEmail(),
                    usuario.getNome(),
                    evento.getNome(),
                    qrCodeBytes
            );
        } catch (Exception e) {
            System.err.println("Ingresso salvo, mas falha no e-mail: " + e.getMessage());
        }

        return ingressoSalvo;
    }
}