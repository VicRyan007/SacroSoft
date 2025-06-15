package com.paroquia.sacro.registro.model;

import com.paroquia.sacro.registro.model.enums.PerfilUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;

    private String nome, email,senha;

    private PerfilUsuario perfil;
    private Boolean ativo;
    private LocalDateTime dataCadastro;


} 