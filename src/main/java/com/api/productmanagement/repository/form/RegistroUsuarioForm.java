package com.api.productmanagement.repository.form;

import com.api.productmanagement.repository.Enum.CargoEnum;

public record RegistroUsuarioForm(
        String nome,
        String email,

        String password,
        CargoEnum cargo
){}
