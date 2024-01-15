package com.api.productmanagement.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "configuracao_exibicao")
public class ConfiguracaoExibicaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCampo;
    private boolean ocultarParaEstoquista;
}
