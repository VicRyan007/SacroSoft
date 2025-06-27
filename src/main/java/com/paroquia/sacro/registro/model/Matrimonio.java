package com.paroquia.sacro.registro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Matrimonio {


    private LocalDate data;
    private String local;
    private String celebrante;

    private String livroId;
    private Integer pagina;
    private Integer numero;

    private String conjugeId,nomeConjuge;
    private List<String> testemunhas;


} 