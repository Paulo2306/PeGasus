package com.ifsp.PeGasus.Repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ifsp.PeGasus.Model.Produto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class ProdutoRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Produto produto) {
        String sql = "INSERT INTO produtos (nome, descricao, preco) VALUES (:nome, :descricao, :preco)"; 
        Query query = em.createNativeQuery(sql);
        query.setParameter("nome", produto.getNome());
        query.setParameter("descricao", produto.getDescricao());
        query.setParameter("preco", produto.getPreco());
        query.executeUpdate();
    }

    @Transactional
    public List<Produto> findAll(){
        String sql = "select * from produtos";
        Query q = em.createNativeQuery(sql, Produto.class);
        @SuppressWarnings("unchecked")
        List<Produto> listaProdutos = q.getResultList();
        return listaProdutos;        
    }

    @Transactional
    public Produto findByID(long id){
        String sql = "select * from produtos WHERE id = :id";
        Query query = em.createNativeQuery(sql, Produto.class);
        query.setParameter("id",id);
        Produto produto = (Produto) query.getSingleResult();
        return produto;
    }
    
    @Transactional
    public void update(Produto produto){
        String sql = "UPDATE produtos SET descricao = :descricao, preco = :preco WHERE id = :id";
        Query query =em.createNativeQuery(sql);
        query.setParameter("id", produto.getId());
        query.setParameter("descricao",produto.getDescricao());
        query.setParameter("preco", produto.getPreco());
        query.executeUpdate();
    }
    
    @Transactional
    public void delete(long id){
        String sql = "DELETE FROM produtos WHERE id = :id";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id", id);
        query.executeUpdate();
    }    
    
}