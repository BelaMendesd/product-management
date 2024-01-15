package com.api.productmanagement.repository.dto;

import com.api.productmanagement.repository.Enum.TipoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
    private Long id;
    private String nome;
    private Boolean ativo;
    private TipoEnum tipo;
}
