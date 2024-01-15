package com.api.productmanagement.resource;

import com.api.productmanagement.repository.form.ConfiguracaoExibicaoForm;
import com.api.productmanagement.service.ConfiguracaoExibicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/configuracoes-exibicao")
public class ConfiguracaoExibicaoResource {
    @Autowired
    private ConfiguracaoExibicaoService configuracaoExibicaoService;

    @PostMapping
    public ResponseEntity<?> configurarExibicaoProduto(
            @RequestBody ConfiguracaoExibicaoForm configuracaoForm) {
        configuracaoExibicaoService.configurarExibicaoProduto(configuracaoForm);
        return ResponseEntity.ok("Configuração de exibição atualizada com sucesso");
    }
}
