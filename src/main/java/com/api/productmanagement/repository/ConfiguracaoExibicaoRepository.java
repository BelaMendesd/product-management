package com.api.productmanagement.repository;

import com.api.productmanagement.repository.entity.ConfiguracaoExibicaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfiguracaoExibicaoRepository extends JpaRepository<ConfiguracaoExibicaoEntity, Long> {
    Optional<ConfiguracaoExibicaoEntity> findByNomeCampo(String nomeCampo);

}
