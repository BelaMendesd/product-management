package com.api.productmanagement.repository.Enum;

public enum TipoEnum {
    NORMAL("normal"),
    ESPECIAL("especial"),
    PERSONALIZADO("personalizado");

    private String tipo;

    TipoEnum(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
