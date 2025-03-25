package com.senac.gerenciador_academia.repository;

import com.senac.gerenciador_academia.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {
}
