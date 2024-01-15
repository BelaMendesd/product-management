package com.api.productmanagement.resource;

import com.api.productmanagement.repository.dto.ProdutoAgregadoDTO;
import com.api.productmanagement.repository.dto.ProdutoDTO;
import com.api.productmanagement.repository.form.ProdutoAtualizacaoForm;
import com.api.productmanagement.repository.form.ProdutoForm;
import com.api.productmanagement.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.List;

@RestController
public class ProdutoResource {
    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/listarProdutosPorPagina")
    public Page<ProdutoDTO> listarProdutos(
            @RequestParam(defaultValue = "1") int pagina,
            @RequestParam(defaultValue = "10") int itensPorPagina,
            @RequestParam(defaultValue = "id") String ordenacao) {
        return produtoService.listarProdutosPorPagina(pagina, itensPorPagina, ordenacao);
    }
    @GetMapping("/produtos/{id}")
    public ResponseEntity<ProdutoDTO> obterProdutoPorId(@PathVariable Long id) {
        ProdutoDTO produto = produtoService.listarProdutoPorId(id);
        return ResponseEntity.ok(produto);
    }
    @GetMapping("/produtos")
    public List<ProdutoDTO> listarProdutos() {
        return produtoService.listarTodosProdutos();
    }

    @PostMapping("/produtos")
    public ResponseEntity<ProdutoDTO> criarProduto(@RequestBody ProdutoForm produto) {
        ProdutoDTO novoProduto = produtoService.criarProduto(produto);
        return ResponseEntity.ok(novoProduto);
    }

    @PutMapping("/produtos/{id}")
    public ResponseEntity<ProdutoDTO> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoAtualizacaoForm produtoAtualizado) throws IllegalAccessException, InvocationTargetException {
        ProdutoDTO produto = produtoService.atualizarProduto(id, produtoAtualizado);
        return ResponseEntity.ok(produto);
    }

    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<String> deletarProduto(@PathVariable Long id) {
            String mensagem = produtoService.deletarProduto(id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        }

    @PutMapping("/inativar/{id}")
    public ResponseEntity<String> inativarProduto(@PathVariable Long id) {
        String mensagem = produtoService.inativarProduto(id);
        return new ResponseEntity<>(mensagem, HttpStatus.OK);
    }

    @GetMapping("/listarProdutosPorUsuario/{idUsuario}")
    public List<ProdutoDTO> listarProdutosPorUsuario(@PathVariable Long idUsuario) {
        return produtoService.listarProdutosPorUsuario(idUsuario);
    }

    @GetMapping("/listarProdutosPorFiltros")
    public List<ProdutoDTO> listarProdutosPorFiltros(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) String SKU,
            @RequestParam(required = false) Long categoria,
            @RequestParam(required = false) Double valorCusto,
            @RequestParam(required = false) Double ICMS,
            @RequestParam(required = false) Double valorVenda,
            @RequestParam(required = false) LocalDate dataCadastro,
            @RequestParam(required = false) Integer quantidadeEstoque) {

        return produtoService.listarProdutosPorFiltros(nome, ativo, SKU, categoria, valorCusto, ICMS, valorVenda, dataCadastro, quantidadeEstoque);
    }
    @GetMapping("/listarValoresAgregadosProdutosPorFiltros")
    public List<ProdutoAgregadoDTO> listarValoresAgregadosPorFiltros(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) String SKU,
            @RequestParam(required = false) Long categoria,
            @RequestParam(required = false) Double valorCusto,
            @RequestParam(required = false) Double ICMS,
            @RequestParam(required = false) Double valorVenda,
            @RequestParam(required = false) LocalDate dataCadastro,
            @RequestParam(required = false) Integer quantidadeEstoque) {

        return produtoService.listarValoresAgregadosPorFiltros(nome, ativo, SKU, categoria, valorCusto, ICMS, valorVenda, dataCadastro, quantidadeEstoque);
    }

    @GetMapping("/listarValoresAgregadosPorUsuario/{idUsuario}")
    public List<ProdutoAgregadoDTO> listarValoresAgregadosPorUsuario(@PathVariable Long idUsuario) {
        return produtoService.listarValoresAgregadosPorUsuario(idUsuario);
    }

}
