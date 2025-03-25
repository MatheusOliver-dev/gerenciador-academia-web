package com.senac.gerenciador_academia.service;

import com.senac.gerenciador_academia.repository.EspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EspecialidadeService {
    
    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    public void remover(int id) {
        especialidadeRepository.deleteById(id);
    }
}