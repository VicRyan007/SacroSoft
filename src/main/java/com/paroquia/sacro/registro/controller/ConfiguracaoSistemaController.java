package com.paroquia.sacro.registro.controller;

import com.paroquia.sacro.registro.model.ConfiguracaoSistema;
import com.paroquia.sacro.registro.service.ConfiguracaoSistemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/configuracao")
public class ConfiguracaoSistemaController {

    @Autowired
    private ConfiguracaoSistemaService configuracaoService;

    @PostMapping
    public ResponseEntity<ConfiguracaoSistema> criarConfiguracaoInicial(
            @RequestBody ConfiguracaoSistema configuracao
    ) {
           ConfiguracaoSistema novaConfiguracao =
                   configuracaoService.criarConfiguracaoInicial(configuracao);
           return ResponseEntity.status(HttpStatus.CREATED).body(novaConfiguracao);
    }

    @GetMapping
    public ResponseEntity<ConfiguracaoSistema> buscarConfiguracao(){
        ConfiguracaoSistema configuracao =
                configuracaoService.buscarConfiguracao();
        return ResponseEntity.ok(configuracao);
    }

    @GetMapping("/status")
    public ResponseEntity<Boolean> sistemaJaConfigurado(){
        Boolean configurado = configuracaoService.sistemaJaConfigurado();

        return ResponseEntity.ok(configurado);
    }


}
