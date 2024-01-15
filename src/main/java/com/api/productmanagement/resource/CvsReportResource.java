package com.api.productmanagement.resource;

import com.api.productmanagement.CsvReportGenerator;
import com.api.productmanagement.repository.dto.ProdutoDTO;
import com.api.productmanagement.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/report")
public class CvsReportResource {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CsvReportGenerator csvReportGenerator;

    @GetMapping("/csv")
    public ResponseEntity<byte[]> generateCsvReportFiltros(
            @RequestParam List<String> fields,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) String SKU,
            @RequestParam(required = false) Long categoria,
            @RequestParam(required = false) Double valorCusto,
            @RequestParam(required = false) Double ICMS,
            @RequestParam(required = false) Double valorVenda,
            @RequestParam(required = false) LocalDate dataCadastro,
            @RequestParam(required = false) Integer quantidadeEstoque) {

        List<ProdutoDTO> products = produtoService.listarProdutosPorFiltros(nome, ativo, SKU, categoria, valorCusto, ICMS, valorVenda, dataCadastro, quantidadeEstoque);

        byte[] csvBytes = csvReportGenerator.generateCsvReport(products, fields);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=relatorio_produtos.csv")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(csvBytes.length)
                .body(csvBytes);
    }


    @GetMapping("/csvPorUsuario/{idUsuario}")
    public ResponseEntity<byte[]> generateCsvReportUsuario(
            @RequestParam List<String> fields,
            @PathVariable Long idUsuario) {
        List<ProdutoDTO> products =  produtoService.listarProdutosPorUsuarioGeral(idUsuario);
        byte[] csvBytes = csvReportGenerator.generateCsvReport(products, fields);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=relatorio_produtos.csv")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(csvBytes.length)
                .body(csvBytes);
    }


}
