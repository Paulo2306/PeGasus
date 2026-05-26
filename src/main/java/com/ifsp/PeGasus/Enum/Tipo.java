package com.ifsp.PeGasus.Enum;

public enum Tipo {
    ADMIN("Admin"), CLIENTE("Cliente");

    private String tipo;

    Tipo(String tipo){
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

} 
