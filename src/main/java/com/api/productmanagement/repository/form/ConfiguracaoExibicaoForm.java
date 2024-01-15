package com.api.productmanagement.repository.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracaoExibicaoForm {
    private String nomeCampo;
    private boolean ocultarParaEstoquista;
}
