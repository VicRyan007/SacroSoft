package com.paroquia.sacro.registro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Matrimonio extends Sacramento {
    private String idConjuge;
    private String nomeConjuge;
    private List<String> testemunhas; // Permite múltiplos padrinhos e testemunhas
    private List<String> padrinhos; // Permite múltiplos padrinhos
    private List<String> celebrantes; // Permite múltiplos celebrantes
}