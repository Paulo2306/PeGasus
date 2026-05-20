package com.ifsp.PeGasus.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifsp.PeGasus.Model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}