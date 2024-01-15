package com.api.productmanagement.repository;

import com.api.productmanagement.repository.entity.ProdutoEntity;
import com.api.productmanagement.repository.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity,Long> {
    List<ProdutoEntity> findByUsuario(UsuarioEntity usuario);
}



