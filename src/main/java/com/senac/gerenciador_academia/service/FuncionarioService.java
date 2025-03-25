package com.senac.gerenciador_academia.service;

import com.senac.gerenciador_academia.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public void remover(int id) {
        funcionarioRepository.deleteById(id);
    }
}
