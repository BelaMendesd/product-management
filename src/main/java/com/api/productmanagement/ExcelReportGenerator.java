package com.api.productmanagement;

import com.api.productmanagement.repository.dto.ProdutoDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class ExcelReportGenerator {
    public byte[] generateExcelReport(List<ProdutoDTO> products, List<String> fields) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Produtos");

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < fields.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(fields.get(i));
            }

            for (int rowNum = 0; rowNum < products.size(); rowNum++) {
                Row row = sheet.createRow(rowNum + 1);
                ProdutoDTO produto = products.get(rowNum);
                for (int i = 0; i < fields.size(); i++) {
                    Cell cell = row.createCell(i);
                    String field = fields.get(i);

                    switch (field) {
                        case "nome":
                            cell.setCellValue(produto.getNome());
                            break;
                        case "ativo":
                            cell.setCellValue(produto.getAtivo());
                            break;
                        case "SKU":
                            cell.setCellValue(produto.getSKU());
                            break;
                        case "categoria":
                            cell.setCellValue(produto.getCategoria().getId());
                            break;
                        case "valorCusto":
                            cell.setCellValue(produto.getValorCusto());
                            break;
                        case "ICMS":
                            cell.setCellValue(produto.getICMS());
                            break;
                        case "valorVenda":
                            cell.setCellValue(produto.getValorVenda());
                            break;
                        case "dataCadastro":
                            cell.setCellValue(produto.getDataCadastro());
                            break;
                        case "quantidadeEstoque":
                            cell.setCellValue(produto.getQuantidadeEstoque());
                            break;
                        default:
                            cell.setCellValue("");
                            break;
                    }
                }
            }

            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
