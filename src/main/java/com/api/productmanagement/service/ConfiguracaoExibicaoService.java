package com.api.productmanagement.service;

import com.api.productmanagement.repository.entity.ConfiguracaoExibicaoEntity;
import com.api.productmanagement.repository.form.ConfiguracaoExibicaoForm;

import java.util.List;

public interface ConfiguracaoExibicaoService {
    List<ConfiguracaoExibicaoEntity> obterTodasConfiguracoes();
    void configurarExibicaoProduto(ConfiguracaoExibicaoForm configuracaoForm);

    boolean deveOcultarCampo(String nomeCampo);
    }