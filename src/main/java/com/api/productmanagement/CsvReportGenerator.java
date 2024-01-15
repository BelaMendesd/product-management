package com.api.productmanagement;

import com.api.productmanagement.repository.dto.ProdutoDTO;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
@Component
public class CsvReportGenerator {

    public byte[] generateCsvReport(List<ProdutoDTO> products, List<String> fields) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(outputStream))) {

            csvWriter.writeNext(fields.toArray(new String[0]));

            for (ProdutoDTO produto : products) {
                String[] data = extractDataFromFields(produto, fields);
                csvWriter.writeNext(data);
            }

            csvWriter.flush();
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    private String[] extractDataFromFields(ProdutoDTO product, List<String> fields) {
        String[] data = new String[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            String field = fields.get(i);
            switch (field) {
                case "nome":
                    data[i] = product.getNome();
                    break;
                case "ativo":
                    data[i] = String.valueOf(product.getAtivo());
                    break;
                case "SKU":
                    data[i] = product.getSKU();
                    break;
                case "categoria":
                    data[i] = String.valueOf(product.getCategoria().getId());
                    break;
                case "valorCusto":
                    data[i] = String.valueOf(product.getValorCusto());
                    break;
                case "ICMS":
                    data[i] = String.valueOf(product.getICMS());
                    break;
                case "valorVenda":
                    data[i] = String.valueOf(product.getValorVenda());
                    break;
                case "dataCadastro":
                    data[i] = String.valueOf(product.getDataCadastro());
                    break;
                case "quantidadeEstoque":
                    data[i] = String.valueOf(product.getQuantidadeEstoque());
                    break;
                default:
                    data[i] = "";
                    break;
            }
        }
        return data;
    }

}

