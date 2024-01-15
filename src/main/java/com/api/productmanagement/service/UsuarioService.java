package com.api.productmanagement.service;

import com.api.productmanagement.repository.UsuarioRepository;
import com.api.productmanagement.repository.dto.RecoveryJwtTokenDTO;
import com.api.productmanagement.repository.entity.UsuarioEntity;
import com.api.productmanagement.repository.form.LoginForm;
import com.api.productmanagement.repository.form.RegistroUsuarioForm;
import com.api.productmanagement.security.authentication.JwtTokenService;
import com.api.productmanagement.security.config.WebSecurityConfig;
import com.api.productmanagement.security.userDetails.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class UsuarioService {
    @Autowired
    UsuarioRepository repository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private WebSecurityConfig securityConfiguration;

    public RecoveryJwtTokenDTO authenticateUser(LoginForm form) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(form.email(), form.password());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new RecoveryJwtTokenDTO(jwtTokenService.generateToken(userDetails));
    }

    public void createUser(RegistroUsuarioForm createUser) {

        UsuarioEntity newUser = UsuarioEntity.builder()
                .nome(createUser.nome())
                .email(createUser.email())
                .password(securityConfiguration.passwordEncoder().encode(createUser.password()))
                .cargo(createUser.cargo())
                .build();
        repository.save(newUser);
    }

    public UsuarioEntity pesquisarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
    }

    public String getCurrentCargo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getAuthorities().stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse(null);

        } else return null;
    }
    public String findNameByEmail (String email){
         Optional<UsuarioEntity> usuarioEntityOptional = repository.findByEmail(email);
         UsuarioEntity usuarioEntity = usuarioEntityOptional.get();
         return usuarioEntity.getNome();
    }
}

