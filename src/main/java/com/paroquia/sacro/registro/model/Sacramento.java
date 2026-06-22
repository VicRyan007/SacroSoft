package com.paroquia.sacro.registro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Sacramento {
    private LocalDateTime dataCelebracao;
    private String celebrante;
    private String localCelebracao;
    private String livroId;
    private Integer folha;
    private Integer numeroRegistro;
}
