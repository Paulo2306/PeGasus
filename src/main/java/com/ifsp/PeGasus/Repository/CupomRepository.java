package com.ifsp.PeGasus.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifsp.PeGasus.Model.Cupom;

@Repository
public interface CupomRepository extends JpaRepository<Cupom, Long> { 
    Optional<Cupom> findByNomeIgnoreCase(String nome);
}