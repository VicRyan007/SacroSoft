package com.paroquia.sacro.registro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Eucaristia {

    private LocalDate data;
    private String local;
    private String celebrante;

    private String livroId;
    private Integer pagina;
    private Integer numero;

} 