package com.paroquia.sacro.registro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private int numero;
}
