package com.api.productmanagement.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produto")
public class ProdutoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Boolean ativo;
    private String SKU;
    @ManyToOne
    private CategoriaEntity categoria;
    private Double valorCusto;
    private Double ICMS;
    private Double valorVenda;
    private LocalDateTime dataCadastro;
    private Integer quantidadeEstoque;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    public void inativar() {
        this.ativo = false;
    }


}
