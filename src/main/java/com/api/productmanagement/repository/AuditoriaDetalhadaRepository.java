package com.api.productmanagement.repository;

import com.api.productmanagement.repository.entity.AuditoriaDetalhadaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriaDetalhadaRepository extends JpaRepository<AuditoriaDetalhadaEntity,Long> {
}
