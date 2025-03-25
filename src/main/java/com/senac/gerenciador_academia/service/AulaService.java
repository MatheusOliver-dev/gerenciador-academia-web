package com.senac.gerenciador_academia.service;

import com.senac.gerenciador_academia.model.Aula;
import com.senac.gerenciador_academia.repository.AulaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    public void remover(int id) {
        aulaRepository.deleteById(id);
    }
    
    public List<Aula> listarTodas() {
        return aulaRepository.findAll();
    }

    public Aula buscarPorId(int id) {
        return aulaRepository.findById(id).orElse(null);
    }

    public void salvar(Aula aula) {
        aulaRepository.save(aula);
    }
}