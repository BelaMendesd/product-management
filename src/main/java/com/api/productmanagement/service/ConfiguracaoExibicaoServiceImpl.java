package com.api.productmanagement.service;

import com.api.productmanagement.repository.ConfiguracaoExibicaoRepository;
import com.api.productmanagement.repository.entity.ConfiguracaoExibicaoEntity;
import com.api.productmanagement.repository.form.ConfiguracaoExibicaoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ConfiguracaoExibicaoServiceImpl implements ConfiguracaoExibicaoService {

    @Autowired
    private ConfiguracaoExibicaoRepository configuracaoExibicaoRepository;

    @Override
    public void configurarExibicaoProduto(ConfiguracaoExibicaoForm configuracaoForm) {
        Optional<ConfiguracaoExibicaoEntity> configuracaoExistente = configuracaoExibicaoRepository
                .findByNomeCampo(configuracaoForm.getNomeCampo());

        ConfiguracaoExibicaoEntity configuracao = configuracaoExistente.orElse(new ConfiguracaoExibicaoEntity());
        configuracao.setNomeCampo(configuracaoForm.getNomeCampo());
        configuracao.setOcultarParaEstoquista(configuracaoForm.isOcultarParaEstoquista());

        configuracaoExibicaoRepository.save(configuracao);
    }
    @Override
    public List<ConfiguracaoExibicaoEntity> obterTodasConfiguracoes() {
        return configuracaoExibicaoRepository.findAll();
    }
    public boolean deveOcultarCampo(String nomeCampo) {
        return configuracaoExibicaoRepository.findByNomeCampo(nomeCampo)
                .map(ConfiguracaoExibicaoEntity::isOcultarParaEstoquista)
                .orElse(false);
    }
}
