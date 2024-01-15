package com.api.productmanagement.service;

import com.api.productmanagement.repository.CategoriaRepository;
import com.api.productmanagement.repository.entity.CategoriaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class CategoriaService {
    @Autowired
    CategoriaRepository repository;


    public CategoriaEntity pesquisarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Categoria não encontrada"));
    }
}
