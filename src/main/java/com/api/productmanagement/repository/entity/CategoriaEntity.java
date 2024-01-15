package com.api.productmanagement.repository.entity;

import com.api.productmanagement.repository.Enum.TipoEnum;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categoria")
public class CategoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Boolean ativo;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoEnum tipo;
}
