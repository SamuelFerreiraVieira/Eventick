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
        // 1. Procura o ingresso pelo UUID que veio no QR Code
        Ingresso ingresso = ingressoRepository.findByCodigoValidacao(codigoValidacao)
                .orElseThrow(() -> new RuntimeException("Ingresso inválido ou não encontrado!"));

        // 2. Segurança: Verifica se o ingresso já foi "passado" no leitor antes
        if (ingresso.isUtilizado()) {
            throw new RuntimeException("Alerta: Este ingresso já foi utilizado!");
        }

        // 3. Marca como utilizado no banco de dados
        ingresso.setUtilizado(true);
        ingressoRepository.save(ingresso);

        return "Check-in realizado com sucesso! Bem-vindo(a), " + ingresso.getUsuario().getNome();
    }

    @Transactional
    public Ingresso realizarInscricao(Ingresso ingresso) throws Exception {

        // 1. Buscamos os dados completos do Usuário e do Evento
        var usuario = usuarioRepository.findById(ingresso.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        var evento = eventoRepository.findById(ingresso.getEvento().getId())
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        // --- LÓGICA DE NEGÓCIO: VALIDAÇÃO DE VAGAS ---
        // Contamos quantos ingressos já existem para este ID de evento
        long inscritosAtuais = ingressoRepository.countByEventoId(evento.getId());

        if (evento.getVagasTotais() != null && inscritosAtuais >= evento.getVagasTotais()) {
            throw new RuntimeException("Desculpe, as vagas para o evento '" + evento.getNome() + "' esgotaram!");
        }
        // ----------------------------------------------

        // 2. Geramos o código único e o QR Code
        String codigoUnico = UUID.randomUUID().toString();
        ingresso.setCodigoValidacao(codigoUnico);

        byte[] qrCodeBytes = qrCodeService.gerarQRCode(codigoUnico);
        ingresso.setQrCodePath(Base64.getEncoder().encodeToString(qrCodeBytes));

        // 3. Preenchemos os dados restantes
        ingresso.setUsuario(usuario);
        ingresso.setEvento(evento);
        ingresso.setDataInscricao(LocalDateTime.now());
        ingresso.setUtilizado(false);

        // 4. Salvamos no Banco
        Ingresso ingressoSalvo = ingressoRepository.save(ingresso);

        // 5. Enviamos o e-mail com o design Cyano Premium
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