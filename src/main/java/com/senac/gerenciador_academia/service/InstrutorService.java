package com.senac.gerenciador_academia.service;

import com.senac.gerenciador_academia.repository.InstrutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstrutorService {

    @Autowired
    private InstrutorRepository instrutorRepository;

    public void remover(int id) {
        instrutorRepository.deleteById(id);
    }
}