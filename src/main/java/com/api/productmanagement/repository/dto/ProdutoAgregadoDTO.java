package com.api.productmanagement.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoAgregadoDTO {
    private String nome;
    private Double custo;
    private Double custoTotal;
    private Integer quantidade;
    private Double valorTotalPrevisto;
}
