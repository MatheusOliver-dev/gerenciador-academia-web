package com.senac.gerenciador_academia.repository;

import com.senac.gerenciador_academia.model.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Integer> {
}