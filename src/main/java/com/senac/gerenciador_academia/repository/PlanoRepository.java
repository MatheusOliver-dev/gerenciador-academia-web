package com.senac.gerenciador_academia.repository;

import com.senac.gerenciador_academia.model.Plano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanoRepository extends JpaRepository<Plano, Integer> {
    
}