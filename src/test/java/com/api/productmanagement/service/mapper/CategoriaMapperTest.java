package com.api.productmanagement.service.mapper;

import com.api.productmanagement.repository.Enum.TipoEnum;
import com.api.productmanagement.repository.dto.CategoriaDTO;
import com.api.productmanagement.repository.entity.CategoriaEntity;
import com.api.productmanagement.repository.form.CategoriaForm;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CategoriaMapperTest {

    @Inject
    private CategoriaMapper categoriaMapper;
    private CategoriaEntity gerarCategoria() {
        return CategoriaEntity.builder()
                .id(1L)
                .nome("categoria")
                .ativo(true)
                .tipo(TipoEnum.NORMAL)
                .build();
    }
    private CategoriaForm gerarCategoriaForm() {
        CategoriaForm form = new CategoriaForm();
                form.setId(1L);
                form.setNome("categoria");
                return form;
    }

    @Test
    void testarMapeamentoParaDto() {

        CategoriaDTO dto = categoriaMapper.paraDTO(gerarCategoria());

        assertEquals(1L, dto.getId());
        assertEquals("categoria", dto.getNome());
        assertEquals(true, dto.getAtivo());
        assertEquals(TipoEnum.NORMAL,dto.getTipo());

    }
    @Test
    void testarMapeamentoParaEntidade() {

        CategoriaEntity entity = categoriaMapper.paraEntidade(gerarCategoriaForm());

        assertEquals(1L, entity.getId());
        assertEquals("categoria", entity.getNome());

    }
    @Test
    void testarMapeamentoListaParaDto() {
        List<CategoriaEntity> lista = new ArrayList<CategoriaEntity>();
        lista.add(gerarCategoria());

        List<CategoriaDTO> listaDto = categoriaMapper.paraListaDTO(lista);
        assertTrue(listaDto.iterator()
                .hasNext());
        CategoriaDTO dto = listaDto.iterator()
                .next();

        assertEquals(1L, dto.getId());
        assertEquals("categoria", dto.getNome());
        assertEquals(true, dto.getAtivo());
        assertEquals(TipoEnum.NORMAL, dto.getTipo());
    }

}
