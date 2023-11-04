package com.egg.novedades.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppController {
    @Autowired
    private JavaMailSender mailSender;
    String emisor = "btocarmona@gmail.com";//dirección de correo que hace el envío.

    @GetMapping("/formulario")
    public String mostrarFormulario() {
        return "enviarcorreo.html";
    }
    @PostMapping("/enviar-correo")
    public void enviarCorreo(String destinatario,String asunto,String mensaje){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emisor);
        message.setTo(destinatario);
        message.setSubject(asunto);
        message.setText(mensaje);
        mailSender.send(message); //método Send(envio), propio de Java Mail Sender.
    }

}
