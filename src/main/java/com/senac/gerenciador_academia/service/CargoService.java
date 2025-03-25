package com.senac.gerenciador_academia.service;

import com.senac.gerenciador_academia.model.Cargo;
import com.senac.gerenciador_academia.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    public void remover(int id) {
        cargoRepository.deleteById(id);
    }

    public Cargo salvar(Cargo cargo) {
        return cargoRepository.save(cargo);
    }

    public Cargo buscarPorId(int id) {
        return cargoRepository.findById(id).orElse(null);
    }
}