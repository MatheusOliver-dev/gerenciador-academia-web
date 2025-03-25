package com.senac.gerenciador_academia.service;

import com.senac.gerenciador_academia.model.Plano;
import com.senac.gerenciador_academia.repository.PlanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanoService {
    
    @Autowired
    private PlanoRepository planoRepository;

    public void remover(int id) {
        planoRepository.deleteById(id);
    }

    public Plano salvar(Plano plano) {
        return planoRepository.save(plano);
    }

    public Plano buscarPorId(int id) {
        return planoRepository.findById(id).orElse(null);
    }
}