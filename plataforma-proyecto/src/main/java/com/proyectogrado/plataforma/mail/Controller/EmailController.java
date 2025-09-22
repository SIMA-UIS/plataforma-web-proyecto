package com.proyectogrado.plataforma.mail.Controller;

import com.proyectogrado.plataforma.mail.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/emails")
public class EmailController
{
    @Autowired
    private EmailService emailService;

    // Notify teachers when a course is created
    @PostMapping("/notify-teachers")
    public ResponseEntity<String> notifyTeachers(@RequestBody EmailRequest request)
    {
        List<String> currentRoles = getCurrentUserRoles();
        boolean isAdminOrTeacher = currentRoles.contains("ADMIN") || currentRoles.contains("TEACHER");
        if (!isAdminOrTeacher)
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para enviar correos");
        }

        emailService.notifyAssignedTeachersForCourse(
                request.getCourseName(),
                request.getRecipients()
        );

        return ResponseEntity.ok("Notification sent to teachers for course: " + request.getCourseName());
    }

    // Notify students when an activity is created
    @PostMapping("/notify-students")
    public ResponseEntity<String> notifyStudents(@RequestBody EmailRequest request)
    {
        List<String> currentRoles = getCurrentUserRoles();
        boolean isAdminOrTeacher = currentRoles.contains("ADMIN") || currentRoles.contains("TEACHER");
        if (!isAdminOrTeacher)
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para enviar correos");
        }

        emailService.notifyStudentsOfActivityCreation(
                request.getCourseName(),
                request.getRecipients()
        );

        return ResponseEntity.ok("Notification sent to students for course: " + request.getCourseName());
    }

    // Helpers to check authenticated user
    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    private List<String> getCurrentUserRoles() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getAuthorities().stream()
                    .map(authority -> authority.getAuthority())
                    .toList();
        }
        return List.of();
    }
}