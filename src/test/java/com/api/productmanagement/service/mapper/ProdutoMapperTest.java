package com.api.productmanagement.service.mapper;

import com.api.productmanagement.repository.ProdutoRepository;
import com.api.productmanagement.repository.dto.ProdutoDTO;
import com.api.productmanagement.repository.entity.ProdutoEntity;
import com.api.productmanagement.repository.form.ProdutoAtualizacaoForm;
import com.api.productmanagement.repository.form.ProdutoForm;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ProdutoMapperTest {
    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoMapper produtoMapper;


    @Test
    private ProdutoEntity gerarProdutoCompleto() {
        ProdutoEntity produto = new ProdutoEntity();
        produto.setId(1L);
        produto.setNome("Produto");
        produto.setSKU("SKU");
        produto.setAtivo(true);
        produto.setICMS(2.1);
        produto.setDataCadastro(LocalDateTime.now());
        produto.setQuantidadeEstoque(2);
        produto.setValorCusto(25.0);
        produto.setValorVenda(35.0);
        return produto;
    }
    @Test
    private ProdutoForm gerarProdutoCompletoForm() {
        ProdutoForm produto = new ProdutoForm();
        produto.setId(1L);
        produto.setNome("Produto");
        produto.setSKU("SKU");
        produto.setAtivo(true);
        produto.setICMS(2.1);
        produto.setQuantidadeEstoque(2);
        produto.setValorCusto(25.0);
        produto.setValorVenda(35.0);
        return produto;
    }

    @Test
    private ProdutoAtualizacaoForm gerarProdutoCompletoFormAtualizacao() {
        ProdutoAtualizacaoForm produto = new ProdutoAtualizacaoForm();
        produto.setId(1L);
        produto.setNome("Produto");
        produto.setSKU("SKU");
        produto.setAtivo(true);
        produto.setICMS(2.1);
        produto.setQuantidadeEstoque(2);
        produto.setValorCusto(25.0);
        produto.setValorVenda(35.0);
        return produto;
    }

    @Test
    void testarMapeamentoParaDto() {
        ProdutoDTO dto = produtoMapper.paraDTO(gerarProdutoCompleto());

        assertEquals(1L, dto.getId());
        assertEquals("Produto", dto.getNome());
        assertEquals("SKU", dto.getSKU());

    }
    @Test
    void testarMapeamentoParaEntidade() {
        ProdutoEntity dto = produtoMapper.paraEntity(gerarProdutoCompletoForm());

        assertEquals(1L, dto.getId());
        assertEquals("Produto", dto.getNome());
        assertEquals("SKU", dto.getSKU());

    }
    @Test
    void testarMapeamentoParaEntidadeFormAtualizacao() {
        ProdutoEntity dto = produtoMapper.paraEntidade(gerarProdutoCompletoFormAtualizacao());

        assertEquals(1L, dto.getId());
        assertEquals("Produto", dto.getNome());
        assertEquals("SKU", dto.getSKU());

    }
    @Test
    void testarMapeamentoListaParaDto() {
        List<ProdutoEntity> lista = new ArrayList<ProdutoEntity>();
        lista.add(gerarProdutoCompleto());

        List<ProdutoDTO> listaDto = produtoMapper.paraListaDTO(lista);
        assertTrue(listaDto.iterator()
                .hasNext());
        ProdutoDTO dto = listaDto.iterator()
                .next();

        assertEquals(1L, dto.getId());
        assertEquals("Produto", dto.getNome());
        assertEquals("SKU", dto.getSKU());
    }

}
