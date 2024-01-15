package com.api.productmanagement.repository.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProdutoDTO {

    private Long id;
    private String nome;
    private Boolean ativo;
    private String SKU;
    private CategoriaDTO categoria;
    private Double valorCusto;
    private Double ICMS;
    private Double valorVenda;
    private LocalDateTime dataCadastro;
    private Integer quantidadeEstoque;
    private UsuarioDTO usuario;

}
