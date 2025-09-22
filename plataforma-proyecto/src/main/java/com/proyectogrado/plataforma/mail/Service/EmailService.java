package com.proyectogrado.plataforma.mail.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService
{
    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    public void notifyAssignedTeachersForCourse(String courseName, String[] teachers)
    {
        String subject = "Asignación como docente en el curso: " + courseName;
        String text = String.format(
                "Estimado(a) docente,\n\n" +
                        "Se ha creado el curso \"%s\" y usted ha sido asignado como profesor responsable.\n\n" +
                        "Por favor, ingrese a la plataforma para definir la actividad de aprendizaje correspondiente.\n" +
                        "Acceda aquí: https://sima.uis.edu.co/\n\n" +
                        "Atentamente,\n" +
                        "Sistema de Gestión Académica",
                courseName
        );

        sendEmailToMultiple(teachers, subject, text);
    }

    public void notifyStudentsOfActivityCreation(String courseName, String[] students)
    {
        String subject = "Actividad de aprendizaje disponible en el curso: " + courseName;
        String text = String.format(
                "Estimado(a) estudiante,\n\n" +
                        "Su docente ha creado una actividad de aprendizaje para el curso \"%s\".\n\n" +
                        "Por favor, ingrese a la plataforma para consultar el contenido y dar inicio a la actividad.\n" +
                        "Acceda aquí: https://sima.uis.edu.co/\n\n" +
                        "Atentamente,\n" +
                        "Sistema de Gestión Académica",
                courseName
        );

        sendEmailToMultiple(students, subject, text);
    }

    private void sendEmail(String to, String subject, String text)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    private void sendEmailToMultiple(String[] to, String subject, String text)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}