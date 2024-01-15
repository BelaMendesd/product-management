package com.api.productmanagement.service.mapper;

import com.api.productmanagement.repository.dto.ProdutoDTO;
import com.api.productmanagement.repository.entity.ProdutoEntity;
import com.api.productmanagement.repository.form.ProdutoAtualizacaoForm;
import com.api.productmanagement.repository.form.ProdutoForm;
import com.api.productmanagement.service.CategoriaService;
import com.api.productmanagement.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ProdutoMapper {
    @Lazy
    @Autowired
    CategoriaMapper categoriaMapper;
    @Autowired
    UsuarioMapper usuarioMapper;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    CategoriaService categoriaService;
    public ProdutoDTO paraDTO(ProdutoEntity entidade) {
        return ProdutoDTO.builder()
                .id(entidade.getId())
                .nome(entidade.getNome())
                .ativo(entidade.getAtivo())
                .SKU(entidade.getSKU())
                .categoria(Objects.nonNull(entidade.getCategoria())
                        ? categoriaMapper.paraDTO(entidade.getCategoria())
                        : null)
                .valorCusto(entidade.getValorCusto())
                .ICMS(entidade.getICMS())
                .valorVenda(entidade.getValorVenda())
                .dataCadastro(entidade.getDataCadastro())
                .quantidadeEstoque(entidade.getQuantidadeEstoque())
                .usuario(Objects.nonNull(entidade.getUsuario())
                        ? usuarioMapper.paraDTO(entidade.getUsuario())
                        : null)
                .build();
    }
    public ProdutoEntity paraEntity(ProdutoForm form) {
        return ProdutoEntity.builder()
                .id(form.getId())
                .nome(form.getNome())
                .ativo(form.getAtivo())
                .SKU(form.getSKU())
                .categoria(Objects.nonNull(form.getCategoria())
                        ? categoriaMapper.paraEntidade(form.getCategoria())
                        : null)
                .valorCusto(form.getValorCusto())
                .ICMS(form.getICMS())
                .valorVenda(form.getValorVenda())
                .quantidadeEstoque(form.getQuantidadeEstoque())
                .usuario(Objects.nonNull(form.getUsuario())
                        ? usuarioMapper.paraEntidade(form.getUsuario())
                        : null)
                .build();
    }

    public List<ProdutoDTO> paraListaDTO(List<ProdutoEntity> entidade) {
        return entidade.stream()
                .map(this::paraDTO)
                .collect(Collectors.toList());
    }

    public Page<ProdutoDTO> paraListaPaginaDTO(Page<ProdutoEntity> entidade) {

        return entidade.map(this::paraDTO);
    }
    public ProdutoEntity paraEntidade(ProdutoAtualizacaoForm form) {
        return ProdutoEntity.builder()
                .id(form.getId())
                .nome(form.getNome())
                .ativo(form.getAtivo())
                .SKU(form.getSKU())
                .categoria(Objects.nonNull(form.getCategoria())
                        ? categoriaService.pesquisarEntidade(form.getCategoria())
                        : null)
                .valorCusto(form.getValorCusto())
                .ICMS(form.getICMS())
                .valorVenda(form.getValorVenda())
                .quantidadeEstoque(form.getQuantidadeEstoque())
                .usuario(Objects.nonNull(form.getUsuario())
                        ? usuarioService.pesquisarEntidade(form.getUsuario())
                        : null)
                .build();
    }
}
