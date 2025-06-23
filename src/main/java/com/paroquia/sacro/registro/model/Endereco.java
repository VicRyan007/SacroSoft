package com.paroquia.sacro.registro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    private String rua,bairro,cidade,estado,cep;
    private int numero;
}
