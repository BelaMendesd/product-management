package com.api.productmanagement.service;

import com.api.productmanagement.repository.entity.AuditoriaDetalhadaEntity;
import com.api.productmanagement.repository.AuditoriaDetalhadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AuditoriaDetalhadaService {

    @Autowired
    AuditoriaDetalhadaRepository auditoriaDetalhadaRepository;

    public void registrarDetalheAuditoria(Long auditoriaId, String campoAlterado, String valorAnterior, String valorAtual) {
        AuditoriaDetalhadaEntity detalheAuditoria = new AuditoriaDetalhadaEntity();
        detalheAuditoria.setAuditoriaId(auditoriaId);
        detalheAuditoria.setCampoAlterado(campoAlterado);
        detalheAuditoria.setValorAnterior(valorAnterior);
        detalheAuditoria.setValorAtual(valorAtual);

        auditoriaDetalhadaRepository.save(detalheAuditoria);
    }

    public void registrarAlteracaoDetalhada(Long auditoriaId, Object entidadeAntes, Object entidadeDepois) {
        Class<?> classe = entidadeAntes.getClass();
        Field[] campos = classe.getDeclaredFields();
        List<String> camposAlterados = new ArrayList<>();

        for (Field campo : campos) {
            campo.setAccessible(true);

            try {
                Object valorAntes = campo.get(entidadeAntes);
                Object valorDepois = campo.get(entidadeDepois);

                if (!Objects.equals(valorAntes, valorDepois)) {
                    camposAlterados.add(campo.getName());

                    registrarDetalheAuditoria(
                            auditoriaId,
                            campo.getName(),
                            String.valueOf(valorAntes),
                            String.valueOf(valorDepois)
                    );
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
