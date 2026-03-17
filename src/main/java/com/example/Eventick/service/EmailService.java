package com.example.Eventick.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async; // Importado
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Async
    public void enviarEmailConfirmacao(String para, String nomeUsuario, String nomeEvento, byte[] qrCodeImage) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(para);
        helper.setSubject("Inscrição Confirmada - " + nomeEvento);

        Context context = new Context();
        context.setVariable("nomeUsuario", nomeUsuario);
        context.setVariable("nomeEvento", nomeEvento);

        String htmlContent = templateEngine.process("confirmacao", context);
        helper.setText(htmlContent, true);

        helper.addInline("qrcodeImage", new ByteArrayResource(qrCodeImage), "image/png");
        helper.addAttachment("ingresso-eventick.png", new ByteArrayResource(qrCodeImage));

        mailSender.send(message);


        System.out.println("E-mail enviado com sucesso em background para: " + para);
    }
}