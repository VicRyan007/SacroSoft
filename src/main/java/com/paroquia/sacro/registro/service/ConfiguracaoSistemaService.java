package com.paroquia.sacro.registro.service;

import com.paroquia.sacro.registro.model.ConfiguracaoSistema;
import com.paroquia.sacro.registro.repository.ConfiguracaoSistemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConfiguracaoSistemaService {

    @Autowired
    private ConfiguracaoSistemaRepository configuracaoSistemaRepository;

    public ConfiguracaoSistema criarConfiguracaoInicial(ConfiguracaoSistema configuracaoSistema) {
        configuracaoSistema.setDataConfiguracao(LocalDateTime.now());
        configuracaoSistema.setConfigurado(true);
        return configuracaoSistemaRepository.save(configuracaoSistema);
    }

    public ConfiguracaoSistema buscarConfiguracao() {
        return configuracaoSistemaRepository.findAll()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public boolean sistemaJaConfigurado() {
        ConfiguracaoSistema configuracaoSistema = buscarConfiguracao();
        return configuracaoSistema != null && Boolean.TRUE.equals(configuracaoSistema.getConfigurado());
    }
}
