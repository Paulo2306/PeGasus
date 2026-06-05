package com.ifsp.PeGasus.Model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "carrinhos")
public class Carrinho {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "idUser")
    private long idUser;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "produtos_carrinho",
        joinColumns = @JoinColumn(name = "carrinho_id"),
        inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private List<Produto> produtos;


    @ManyToOne
    @JoinColumn(name = "cupom_id")
    private Cupom cupom;

    public Cupom getCupom() { return cupom; }
    public void setCupom(Cupom cupom) { this.cupom = cupom; }

    public double getSubtotal() {
        if (this.produtos == null) return 0.0;
        return this.produtos.stream().mapToDouble(Produto::getPreco).sum();
    }

    public double getValorDesconto() {
        if (this.cupom == null) return 0.0;
        return (getSubtotal() * this.cupom.getValorPor()) / 100.0;
    }

    public double getTotal() {
        return getSubtotal() - getValorDesconto();
    }

    public Carrinho() {
    }

    public Carrinho(long idUser) {
        this.idUser = idUser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }
}