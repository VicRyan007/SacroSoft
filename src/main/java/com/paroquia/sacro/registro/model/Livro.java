package com.paroquia.sacro.registro.model;

import com.paroquia.sacro.registro.model.enums.TipoSacramento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "livros")
public class Livro {

    @Id
    private String id;


    private TipoSacramento tipoSacramento;
    private Integer numero;
    private Integer anoInicio;
    private Integer anoFim;

    private String paroquia;
    private String celebranteResponsavel;
}
