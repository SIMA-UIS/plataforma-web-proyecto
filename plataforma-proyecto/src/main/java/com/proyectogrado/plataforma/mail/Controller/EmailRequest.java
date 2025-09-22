package com.proyectogrado.plataforma.mail.Controller;

import lombok.Data;

@Data
public class EmailRequest
{
    private String courseName;
    private String[] recipients;
}
