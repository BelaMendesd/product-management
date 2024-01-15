package com.api.productmanagement.service;

import com.api.productmanagement.repository.entity.AuditoriaEntity;
import com.api.productmanagement.repository.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditoriaService {

    @Autowired
    AuditoriaRepository auditoriaRepository;
    public AuditoriaEntity registrarAuditoria(String objetoAlterado, String acaoRealizada, String usuario) {
        AuditoriaEntity auditoria = new AuditoriaEntity();
        auditoria.setObjetoAlterado(objetoAlterado);
        auditoria.setAcaoRealizada(acaoRealizada);
        auditoria.setDataHora(LocalDateTime.now());
        auditoria.setUsuario(usuario);

        auditoriaRepository.save(auditoria);
        return auditoria;
    }

}
