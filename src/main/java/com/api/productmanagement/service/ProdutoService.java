package com.api.productmanagement.service;

import com.api.productmanagement.repository.ProdutoRepositoryComponent;
import com.api.productmanagement.repository.dto.ProdutoAgregadoDTO;
import com.api.productmanagement.repository.dto.ProdutoDTO;
import com.api.productmanagement.repository.entity.*;
import com.api.productmanagement.repository.ProdutoRepository;
import com.api.productmanagement.repository.UsuarioRepository;
import com.api.productmanagement.repository.form.ProdutoAtualizacaoForm;
import com.api.productmanagement.repository.form.ProdutoForm;
import com.api.productmanagement.service.mapper.CategoriaMapper;
import com.api.productmanagement.service.mapper.ProdutoMapper;
import com.api.productmanagement.service.mapper.UsuarioMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoRepositoryComponent produtoRepositoryComponent;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AuditoriaService auditoriaService;
    @Autowired
    private AuditoriaDetalhadaService auditoriaDetalhadaService;
    @Autowired
    private ProdutoMapper produtoMapper;
    @Lazy
    @Autowired
    private CategoriaMapper categoriaMapper;
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    ConfiguracaoExibicaoService configuracaoExibicaoService;

    boolean isCampoVisivel(String nomeCampo) {
        return !configuracaoExibicaoService.deveOcultarCampo(nomeCampo);
    }
    public ProdutoDTO listarProdutoPorId(Long id){
        Optional<ProdutoEntity> entity = produtoRepository.findById(id);
        if(usuarioService.getCurrentCargo().equals("ADMINISTRADOR")) {
            return produtoMapper.paraDTO(entity.orElseThrow(() -> new NoSuchElementException("Produto não encontrado")));
        }
        ProdutoEntity produtoEntity = entity.get();
        return listarProdutoDTO(produtoEntity);
    }

    public ProdutoDTO listarProdutoDTO(ProdutoEntity produtoEntity) {;
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(isCampoVisivel("id") ? produtoEntity.getId() : null);
        produtoDTO.setNome(isCampoVisivel("nome") ? produtoEntity.getNome() : null);
        produtoDTO.setAtivo(isCampoVisivel("ativo") ? produtoEntity.getAtivo() : null);
        produtoDTO.setSKU(isCampoVisivel("sku") ? produtoEntity.getSKU() : null);
        produtoDTO.setCategoria(isCampoVisivel("categoria") ? categoriaMapper.paraDTO(produtoEntity.getCategoria()) : null);
        produtoDTO.setValorCusto(isCampoVisivel("valorCusto") ? produtoEntity.getValorCusto() : null);
        produtoDTO.setICMS(isCampoVisivel("ICMS") ? produtoEntity.getICMS() : null);
        produtoDTO.setValorVenda(isCampoVisivel("valorVenda") ? produtoEntity.getValorVenda() : null);
        produtoDTO.setDataCadastro(isCampoVisivel("dataCadastro") ? produtoEntity.getDataCadastro() : null);
        produtoDTO.setQuantidadeEstoque(isCampoVisivel("quantidadeEstoque") ? produtoEntity.getQuantidadeEstoque() : null);
        produtoDTO.setUsuario(isCampoVisivel("usuario") ? usuarioMapper.paraDTO(produtoEntity.getUsuario()) : null);

        return produtoDTO;
    }
    public List<ProdutoDTO> listarTodosProdutos() {
        List<ProdutoDTO> listaProdutoDTO = new ArrayList<>();
        if(usuarioService.getCurrentCargo().equals("ADMINISTRADOR")) {
            return produtoMapper.paraListaDTO(produtoRepository.findAll());
        }
        else{
            List<ProdutoEntity> produtoEntityList= produtoRepository.findAll();
            for (ProdutoEntity produtoEntity : produtoEntityList) {
                ProdutoDTO produtoDTO = listarProdutoDTO(produtoEntity);
                listaProdutoDTO.add(produtoDTO);
            }
            return listaProdutoDTO;

        }
    }

    public Page<ProdutoDTO> listarProdutosPorPagina(int pagina, int itensPorPagina, String ordenacao) {
        Pageable pageable = PageRequest.of(pagina, itensPorPagina, Sort.by(ordenacao));
        return produtoMapper.paraListaPaginaDTO(produtoRepository.findAll(pageable));
    }

    public ProdutoDTO obterProdutoPorId(Long id) {
        Optional<ProdutoEntity> produtoOptional = produtoRepository.findById(id);
        ProdutoEntity produtoEntity = produtoOptional.orElseThrow(() -> new NoSuchElementException("Produto não encontrado"));
        return produtoMapper.paraDTO(produtoEntity);
    }

    public ProdutoDTO criarProduto(ProdutoForm produtoForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ProdutoEntity produtoEntity = produtoMapper.paraEntity(produtoForm);
        if (Objects.nonNull(produtoEntity.getCategoria().getId())) {
            CategoriaEntity categoria = categoriaService.pesquisarEntidade(produtoEntity.getCategoria().getId());
            UsuarioEntity usuario = usuarioService.pesquisarEntidade(produtoEntity.getUsuario().getId());

            produtoEntity.setCategoria(categoria);
            produtoEntity.setUsuario(usuario);
            produtoEntity.setDataCadastro(LocalDateTime.now());

        }
        ProdutoEntity novoProdutoEntity = produtoRepository.save(produtoEntity);
        ProdutoDTO produtoDTO = obterProdutoPorId(novoProdutoEntity.getId());
        auditoriaService.registrarAuditoria(produtoDTO.getNome(), "Inclusão", usuarioService.findNameByEmail(authentication.getName()));
        return produtoDTO;
    }

    public ProdutoDTO atualizarProduto(Long id, ProdutoAtualizacaoForm produtoAtualizado) throws IllegalAccessException, InvocationTargetException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ProdutoEntity produtoAtualizadoEntity = produtoMapper.paraEntidade(produtoAtualizado);
        Optional<ProdutoEntity> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            ProdutoEntity produtoExistente = produtoOptional.get();

            ProdutoEntity produtoValorAnterior = new ProdutoEntity();
            BeanUtils.copyProperties(produtoValorAnterior, produtoExistente);
            AuditoriaEntity auditoriaEntity = null;
            Field[] fields = ProdutoEntity.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(produtoAtualizadoEntity);
                if (value != null && shouldUpdateField(field)) {
                    field.set(produtoExistente, value);
                }
            }
            auditoriaEntity = auditoriaService.registrarAuditoria(produtoExistente.getNome(), "Alteração", usuarioService.findNameByEmail(authentication.getName()));
            if (!produtoValorAnterior.equals(produtoExistente) && auditoriaEntity!=null) {
                auditoriaDetalhadaService.registrarAlteracaoDetalhada(auditoriaEntity.getId(), produtoValorAnterior, produtoExistente);
                ProdutoEntity entity = produtoRepository.save(produtoExistente);
                return produtoRepository.findById(entity.getId())
                        .map(produtoMapper::paraDTO)
                        .orElseThrow(() -> new NoSuchElementException("Produto não encontrado após salvar"));
            }
            else {
                throw new IllegalAccessException("Permissão negada para atualizar este campo");
            }

        } else {
            throw new NoSuchElementException("Produto não encontrado");
        }
    }

    private boolean shouldUpdateField(Field field) {
        String currentUserRole = usuarioService.getCurrentCargo();

        if (currentUserRole.equals("ADMINISTRADOR")) {
            return true;
        } else if (currentUserRole.equals("ESTOQUISTA")) {
            return !field.getName().equals("ICMS") && !field.getName().equals("valorCusto");
        }
        return false;
    }

    public String deletarProduto(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<ProdutoEntity> produto = produtoRepository.findById(id);
        if (produto.isPresent()) {
            ProdutoEntity produtoEntity = produto.get();
            auditoriaService.registrarAuditoria(produtoEntity.getNome(), "Exclusão", usuarioService.findNameByEmail(authentication.getName()));
            produtoRepository.deleteById(id);
            return "Produto com id " + id + " deletado com sucesso.";
        } else {
            throw new NoSuchElementException("Produto não encontrado");
        }
    }

    public String inativarProduto(Long produtoId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<ProdutoEntity> produtoOptional = produtoRepository.findById(produtoId);
        if (produtoOptional.isPresent()) {
            ProdutoEntity produto = produtoOptional.get();
            if(produto.getAtivo().equals(true)) {
                produto.inativar();
                auditoriaService.registrarAuditoria(produto.getNome(), "Inativação", usuarioService.findNameByEmail(authentication.getName()));
                produtoRepository.save(produto);
                return "Produto com id " + produtoId + " inativado com sucesso.";
            }
            return "Produto com id " + produtoId + " já está inativado.";
        }
        else {
            throw new NoSuchElementException("Produto não encontrado");
        }
    }

    public List<ProdutoDTO> listarProdutosPorUsuario(Long idUsuario) {
        List<ProdutoDTO> listaProdutoDTO = new ArrayList<>();
        Optional<UsuarioEntity> optionalUser = usuarioRepository.findById(idUsuario);

        if (optionalUser.isPresent()) {
            UsuarioEntity usuario = optionalUser.get();
            if(usuarioService.getCurrentCargo().equals("ADMINISTRADOR")) {
                return produtoMapper.paraListaDTO(produtoRepository.findByUsuario(usuario));
            }
            else { List<ProdutoEntity> produtoEntityList= produtoRepository.findByUsuario(usuario);
                for (ProdutoEntity produtoEntity : produtoEntityList) {
                     ProdutoDTO produtoDTO = listarProdutoDTO(produtoEntity);
                     listaProdutoDTO.add(produtoDTO);
                }
                return listaProdutoDTO;
            }
            } else {
            throw new NoSuchElementException("Produtos não encontrados para o usuário com o ID: " + idUsuario);
        }
    }
    public List<ProdutoDTO> listarProdutosPorFiltros(String nome, Boolean ativo, String SKU, Long categoria, Double valorCusto, Double ICMS, Double valorVenda, LocalDate dataCadastro, Integer quantidadeEstoque) {
        return produtoMapper.paraListaDTO(produtoRepositoryComponent.findByFilters(nome, ativo, SKU, categoria, valorCusto, ICMS, valorVenda, dataCadastro, quantidadeEstoque));
    }

    public List<ProdutoAgregadoDTO> listarValoresAgregadosPorFiltros(String nome, Boolean ativo, String SKU, Long categoria, Double valorCusto, Double ICMS, Double valorVenda, LocalDate dataCadastro, Integer quantidadeEstoque) {
        List<ProdutoDTO> produtos = listarProdutosPorFiltros(nome, ativo, SKU, categoria, valorCusto, ICMS, valorVenda, dataCadastro, quantidadeEstoque);
        return calcularValoresAgregados(produtos);
    }
    public List<ProdutoAgregadoDTO> listarValoresAgregadosPorUsuario(Long id) {
        List<ProdutoDTO> produtos = listarProdutosPorUsuarioGeral(id);
        return calcularValoresAgregados(produtos);
    }
    public List<ProdutoDTO> listarProdutosPorUsuarioGeral(Long idUsuario) {
        Optional<UsuarioEntity> optionalUser = usuarioRepository.findById(idUsuario);
        if (optionalUser.isPresent()) {
            UsuarioEntity usuario = optionalUser.get();
            return produtoMapper.paraListaDTO(produtoRepository.findByUsuario(usuario));
        }
            else{
            throw new NoSuchElementException("Produtos não encontrados para o usuário com o ID: " + idUsuario);
        }
    }

    private List<ProdutoAgregadoDTO> calcularValoresAgregados(List<ProdutoDTO> produtos) {
        return produtos.stream()
                .map(this::criarProdutoAgregadoDTO)
                .collect(Collectors.toList());
    }

    private ProdutoAgregadoDTO criarProdutoAgregadoDTO(ProdutoDTO produto) {
        ProdutoAgregadoDTO produtoAgregadoDTO = new ProdutoAgregadoDTO();
        produtoAgregadoDTO.setNome(produto.getNome());
        produtoAgregadoDTO.setCusto(produto.getValorCusto());
        produtoAgregadoDTO.setCustoTotal(calcularCustoTotal(produto));
        produtoAgregadoDTO.setQuantidade(produto.getQuantidadeEstoque());
        produtoAgregadoDTO.setValorTotalPrevisto(calcularValorTotalPrevisto(produto));
        return produtoAgregadoDTO;
    }

    private Double calcularCustoTotal(ProdutoDTO produto) {
        return produto.getValorCusto() * produto.getQuantidadeEstoque();
    }

    private Double calcularValorTotalPrevisto(ProdutoDTO produto) {
        return produto.getValorVenda() * produto.getQuantidadeEstoque();
    }

}
