package com.api.productmanagement.service;

import com.api.productmanagement.repository.ProdutoRepository;
import com.api.productmanagement.repository.dto.ProdutoDTO;
import com.api.productmanagement.repository.entity.ProdutoEntity;
import com.api.productmanagement.service.mapper.ProdutoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProdutoServiceTest {
    @InjectMocks
    private ProdutoService produtoService;
    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoMapper produtoMapper;

    @Mock
    private UsuarioService usuarioService;
    @Mock
    private ConfiguracaoExibicaoService configuracaoExibicaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testListarProdutoPorIdAdmin() {
        when(usuarioService.getCurrentCargo()).thenReturn("ADMINISTRADOR");

        ProdutoEntity produtoEntity = new ProdutoEntity();
        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        when(produtoMapper.paraDTO(produtoEntity)).thenReturn(new ProdutoDTO());

        ProdutoDTO resultado = produtoService.listarProdutoPorId(1L);

        assertNotNull(resultado);
    }
    @Test
    void testListarProdutosPorPagina() {
        Pageable pageable = PageRequest.of(1, 10, Sort.by("nome"));
        Page<ProdutoEntity> produtosPage = new PageImpl<>(Collections.singletonList(new ProdutoEntity()));

        when(produtoRepository.findAll(pageable)).thenReturn(produtosPage);
        when(produtoMapper.paraListaPaginaDTO(produtosPage)).thenReturn(new PageImpl<>(Collections.singletonList(new ProdutoDTO())));

        Page<ProdutoDTO> resultado = produtoService.listarProdutosPorPagina(1, 10, "nome");

        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
    }
    @Test
    void testIsCampoVisivel() {

        boolean resultado = produtoService.isCampoVisivel("nomeCampo");
        assertTrue(resultado);
    }
    @Test
    void testListarProdutoDTO() {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setId(1L);
        produtoEntity.setNome("Produto Teste");
        produtoEntity.setAtivo(true);
        produtoEntity.setSKU("SKU123");
        produtoEntity.setValorCusto(10.0);
        produtoEntity.setICMS(0.1);
        produtoEntity.setValorVenda(20.0);
        produtoEntity.setDataCadastro(LocalDateTime.now());
        produtoEntity.setQuantidadeEstoque(100);

        when(configuracaoExibicaoService.deveOcultarCampo(anyString())).thenReturn(true);

        ProdutoDTO resultado = produtoService.listarProdutoDTO(produtoEntity);

        assertNotNull(resultado);
        assertNull(resultado.getId());
        assertNull(resultado.getNome());
        assertNull(resultado.getAtivo());
        assertNull(resultado.getSKU());
        assertNull(resultado.getValorCusto());
        assertNull(resultado.getICMS());
        assertNull(resultado.getValorVenda());
        assertNull(resultado.getDataCadastro());
        assertNull(resultado.getQuantidadeEstoque());
    }

}
