package com.paroquia.sacro.registro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "pessoas")   
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String nome;
    private LocalDateTime dataNascimento;
    private String nomePai,nomeMae;

    private Documento documentos;
    private Endereco endereco;
    private Contato contato;

    private Batismo batismo;
    private Eucaristia eucaristia;
    private Crisma crisma;
    private Matrimonio matrimonio;



} 