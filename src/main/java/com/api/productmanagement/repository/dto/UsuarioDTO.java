package com.api.productmanagement.repository.dto;

import com.api.productmanagement.repository.Enum.CargoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nome;
    private CargoEnum cargo;
}
