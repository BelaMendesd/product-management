package com.api.productmanagement.resource;

import com.api.productmanagement.ExcelReportGenerator;
import com.api.productmanagement.repository.dto.ProdutoDTO;
import com.api.productmanagement.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ExcelReportResource {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ExcelReportGenerator excelReportGenerator;

    @GetMapping("/excel")
    public ResponseEntity<byte[]> generateExcelReport(
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

        byte[] excelData = excelReportGenerator.generateExcelReport(products, fields);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("relatorio_produtos.xlsx").build());

        return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
    }
    @GetMapping("/excelPorUsuario/{idUsuario}")
    public ResponseEntity<byte[]> generateCsvReportUsuario(
            @RequestParam List<String> fields,
            @PathVariable Long idUsuario) {
        List<ProdutoDTO> products =  produtoService.listarProdutosPorUsuarioGeral(idUsuario);
        byte[] excelData = excelReportGenerator.generateExcelReport(products, fields);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("relatorio_produtos.xlsx").build());

        return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
    }

}
