package com.ifsp.PeGasus.Model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cupoms")
public class Cupom {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "valorPor")
    private int valorPor;

    public Cupom(String nome, int valorPor) {
        this.nome = nome;
        this.valorPor = valorPor;
    }

    public Cupom() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getValorPor() {
        return valorPor;
    }

    public void setValorPor(int valorPor) {
        this.valorPor = valorPor;
    }
}
