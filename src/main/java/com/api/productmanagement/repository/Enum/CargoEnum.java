package com.api.productmanagement.repository.Enum;

public enum CargoEnum {
    ESTOQUISTA("estoquista"),
    ADMINISTRADOR("administrador");

    private String cargo;

    CargoEnum(String cargo) {
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }
}
