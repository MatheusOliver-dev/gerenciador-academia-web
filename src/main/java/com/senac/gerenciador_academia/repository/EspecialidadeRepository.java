package com.senac.gerenciador_academia.repository;

import com.senac.gerenciador_academia.model.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialidadeRepository extends JpaRepository<Especialidade, Integer> {
    
}
