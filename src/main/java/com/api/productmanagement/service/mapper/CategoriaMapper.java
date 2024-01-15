package com.api.productmanagement.service.mapper;

import com.api.productmanagement.repository.dto.CategoriaDTO;
import com.api.productmanagement.repository.entity.CategoriaEntity;
import com.api.productmanagement.repository.form.CategoriaForm;
import com.api.productmanagement.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoriaMapper {
    @Autowired
    ProdutoService service;

    public CategoriaDTO paraDTO(CategoriaEntity entidade) {
        return CategoriaDTO.builder()
                .id(entidade.getId())
                .ativo(entidade.getAtivo())
                .tipo(entidade.getTipo())
                .nome(entidade.getNome())
                .build();
    }
    public CategoriaEntity paraEntidade(CategoriaForm form) {
        return CategoriaEntity.builder()
                .id(form.getId())
                .nome(form.getNome())
                .build();
    }

    public List<CategoriaDTO> paraListaDTO(List<CategoriaEntity> entidade) {
        return entidade.stream()
                .map(this::paraDTO)
                .collect(Collectors.toList());
    }

}
