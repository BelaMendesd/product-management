package com.api.productmanagement.repository.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoForm {
    private Long id;
    private String nome;
    private Boolean ativo;
    private String SKU;
    private CategoriaForm categoria;
    private Double valorCusto;
    private Double ICMS;
    private Double valorVenda;
    private Integer quantidadeEstoque;
    private UsuarioForm usuario;
}
