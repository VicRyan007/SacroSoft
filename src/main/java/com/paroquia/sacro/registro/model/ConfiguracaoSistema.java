package com.paroquia.sacro.registro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "configuracoes_sistema")
public class ConfiguracaoSistema {

    @Id
    private String id;

    private String nomeParoquia;
    private String nomeDiocese;
    private LocalDateTime dataConfiguracao;
    private Boolean configurado;

}
