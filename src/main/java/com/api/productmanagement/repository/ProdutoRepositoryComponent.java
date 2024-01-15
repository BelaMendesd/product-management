package com.api.productmanagement.repository;

import com.api.productmanagement.repository.entity.ProdutoEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ProdutoRepositoryComponent {

    @Autowired
    EntityManager entityManager;

    public List<ProdutoEntity> findByFilters(String nome, Boolean ativo, String SKU, Long categoria, Double valorCusto, Double ICMS, Double valorVenda, LocalDate dataCadastro, Integer quantidadeEstoque) {
        StringBuilder queryBuilder = new StringBuilder("SELECT p FROM ProdutoEntity p WHERE 1 = 1");

        if (nome != null) {
            queryBuilder.append(" AND p.nome LIKE :nome");
            nome = "%" + nome + "%";
        }

        if (ativo != null) {
            queryBuilder.append(" AND p.ativo = :ativo");
        }
        if (SKU != null) {
            queryBuilder.append(" AND p.SKU = :SKU");
        }
        if (categoria != null) {
            queryBuilder.append(" AND p.categoria = :categoria");
        }
        if (valorCusto != null) {
            queryBuilder.append(" AND p.valorCusto = :valorCusto");
        }
        if (ICMS != null) {
            queryBuilder.append(" AND p.ICMS = :ICMS");
        }
        if (valorVenda != null) {
            queryBuilder.append(" AND p.valorVenda = :valorVenda");
        }
        if (dataCadastro != null) {
            queryBuilder.append(" AND p.dataCadastro = :dataCadastro");
        }
        if (quantidadeEstoque != null) {
            queryBuilder.append(" AND p.quantidadeEstoque = :quantidade");
        }

        Query query = entityManager.createQuery(queryBuilder.toString());

        if (nome != null) {
            query.setParameter("nome", nome);
        }

        return query.getResultList();
    }
}
