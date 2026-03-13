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

@Service
public class IngressoService {

    @Autowired
    private IngressoRepository ingressoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private QRCodeService qrCodeService;

    @Transactional
    public Ingresso realizarInscricao(Ingresso ingresso) throws  Exception {
        String codigoUnico = UUID.randomUUID().toString();
        ingresso.setCodigoValidacao(codigoUnico);

        String qrCodeBase64 = qrCodeService.gerarQRCode(codigoUnico);
        ingresso.setQrCodePath(qrCodeBase64);
        ingresso.setDataInscricao(LocalDateTime.now());
        ingresso.setUtilizado(false);

        return ingressoRepository.save(ingresso);
    }
}
