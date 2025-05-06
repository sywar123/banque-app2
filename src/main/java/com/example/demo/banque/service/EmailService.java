package com.example.demo.banque.service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service 
public class EmailService {
	public final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    public void envoyerEmail(String emailDestinataire, String sujet, String message) {
       
            SimpleMailMessage email = new SimpleMailMessage();
            email.setFrom("hibadwahem9@gmail.com");
            email.setTo(emailDestinataire);
            email.setSubject(sujet);
            email.setText(message);
            
            System.out.println("Configuration mail prête. Envoi en cours..."); // LOG 2
            mailSender.send(email);
            System.out.println("SUCCÈS: Email envoyé à " + emailDestinataire); // LOG 3
            
      
    }

    
}