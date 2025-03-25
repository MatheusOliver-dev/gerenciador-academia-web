package com.senac.gerenciador_academia.repository;

import com.senac.gerenciador_academia.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {;
}