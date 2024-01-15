package com.api.productmanagement.service.mapper;

import com.api.productmanagement.repository.dto.UsuarioDTO;
import com.api.productmanagement.repository.entity.UsuarioEntity;
import com.api.productmanagement.repository.form.UsuarioForm;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    public UsuarioDTO paraDTO(UsuarioEntity entidade) {
        return UsuarioDTO.builder()
                .id(entidade.getId())
                .nome(entidade.getNome())
                .cargo(entidade.getCargo())
                .build();
    }

    public UsuarioEntity paraEntidade(UsuarioForm form) {
        return UsuarioEntity.builder()
                .id(form.getId())
                .nome(form.getNome())
                .build();
    }

    public List<UsuarioDTO> paraListaDTO(List<UsuarioEntity> entidade) {
        return entidade.stream()
                .map(this::paraDTO)
                .collect(Collectors.toList());
    }
}
