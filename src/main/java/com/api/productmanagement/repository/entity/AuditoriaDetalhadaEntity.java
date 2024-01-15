package com.api.productmanagement.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "auditoria_detalhada")
public class AuditoriaDetalhadaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long auditoriaId;
    private String campoAlterado;
    private String valorAnterior;
    private String valorAtual;
}
