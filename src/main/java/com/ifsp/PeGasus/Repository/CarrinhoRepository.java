package com.ifsp.PeGasus.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifsp.PeGasus.Model.Carrinho;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    
}