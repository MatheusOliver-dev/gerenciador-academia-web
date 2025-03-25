package com.senac.gerenciador_academia.repository;

import com.senac.gerenciador_academia.model.Instrutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrutorRepository extends JpaRepository<Instrutor, Integer> {
}