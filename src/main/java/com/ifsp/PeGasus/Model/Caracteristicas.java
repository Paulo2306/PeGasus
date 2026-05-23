package com.ifsp.PeGasus.Model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Caracteristicas{
    
    String nome;
    String valor;

    public Caracteristicas(String nome, String valor) {
        this.nome = nome;
        this.valor = valor;
    }
    public Caracteristicas() {
    }
    
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getValor() {
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }
}