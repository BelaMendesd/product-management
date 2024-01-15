package com.api.productmanagement.service.mapper;

import com.api.productmanagement.repository.Enum.CargoEnum;
import com.api.productmanagement.repository.dto.UsuarioDTO;
import com.api.productmanagement.repository.entity.UsuarioEntity;
import com.api.productmanagement.repository.form.UsuarioForm;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UsuarioMapperTest {
    @Inject
    private UsuarioMapper usuarioMapper;
    private UsuarioEntity gerarUsuario() {
        return UsuarioEntity.builder()
                .id(1L)
                .nome("Usuario")
                .email("usuario@gmail")
                .cargo(CargoEnum.ADMINISTRADOR)
                .build();
    }
    private UsuarioForm gerarUsuarioForm() {
        UsuarioForm form = new UsuarioForm();
        form.setId(1L);
        form.setNome("Usuario");
        return form;
    }

    @Test
    void testarMapeamentoParaDto() {

        UsuarioDTO dto = usuarioMapper.paraDTO(gerarUsuario());

        assertEquals(1L, dto.getId());
        assertEquals("Usuario", dto.getNome());
        assertEquals(CargoEnum.ADMINISTRADOR,dto.getCargo());


    }
    @Test
    void testarMapeamentoParaEntidade() {

        UsuarioEntity entity = usuarioMapper.paraEntidade(gerarUsuarioForm());

        assertEquals(1L, entity.getId());
        assertEquals("Usuario", entity.getNome());
    }
    @Test
    void testarMapeamentoListaParaDto() {
        List<UsuarioEntity> lista = new ArrayList<UsuarioEntity>();
        lista.add(gerarUsuario());

        List<UsuarioDTO> listaDto = usuarioMapper.paraListaDTO(lista);
        assertTrue(listaDto.iterator()
                .hasNext());
        UsuarioDTO dto = listaDto.iterator()
                .next();

        assertEquals(1L, dto.getId());
        assertEquals("Usuario", dto.getNome());
        assertEquals(CargoEnum.ADMINISTRADOR, dto.getCargo());
    }
}
