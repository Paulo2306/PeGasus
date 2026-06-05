package com.ifsp.PeGasus.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifsp.PeGasus.Model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {  
}