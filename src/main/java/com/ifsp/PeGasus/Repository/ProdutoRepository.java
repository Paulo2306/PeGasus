package com.ifsp.PeGasus.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifsp.PeGasus.Model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
    @Query("SELECT p FROM Produto p WHERE " +
           "(:nome IS NULL OR :nome = '' OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%')) OR LOWER(p.descricao) LIKE LOWER(CONCAT('%', :nome, '%'))) " +
           "AND (:categoriaId IS NULL OR p.categoria.id = :categoriaId)")
    Page<Produto> findByNomeAndCategoria(@Param("nome") String nome, @Param("categoriaId") Long categoriaId, Pageable pageable);

    boolean existsByCategoriaId(long categoriaId);
}