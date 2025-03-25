package com.senac.gerenciador_academia.service;

import com.senac.gerenciador_academia.model.Agendamento;
import com.senac.gerenciador_academia.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public List<Agendamento> listarTodos() {
        return agendamentoRepository.findAll();
    }

    public Agendamento buscarPorId(int id) {
        return agendamentoRepository.findById(id).orElse(null);
    }

    public void salvar(Agendamento agendamento) {
        agendamentoRepository.save(agendamento);
    }

    public void remover(int id) {
        agendamentoRepository.deleteById(id);
    }
}