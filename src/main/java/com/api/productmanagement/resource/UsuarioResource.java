package com.api.productmanagement.resource;

import com.api.productmanagement.repository.dto.RecoveryJwtTokenDTO;
import com.api.productmanagement.repository.form.LoginForm;
import com.api.productmanagement.repository.form.RegistroUsuarioForm;
import com.api.productmanagement.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsuarioResource {
    @Autowired
    private UsuarioService userService;

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDTO> authenticateUser(@RequestBody LoginForm form) {
        RecoveryJwtTokenDTO token = userService.authenticateUser(form);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody RegistroUsuarioForm createUser) {
        userService.createUser(createUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
