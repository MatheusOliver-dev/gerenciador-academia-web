package com.senac.gerenciador_academia.controller;

import com.senac.gerenciador_academia.model.Aula;
import com.senac.gerenciador_academia.model.Instrutor;
import com.senac.gerenciador_academia.repository.AulaRepository;
import com.senac.gerenciador_academia.repository.InstrutorRepository;
import com.senac.gerenciador_academia.service.AulaService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
public class AulaController {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private InstrutorRepository instrutorRepository;

    @Autowired
    private AulaService aulaService;

    @GetMapping("/aulas")
    public String listarAulas(Model model) {
        List<Aula> aulas = aulaRepository.findAll();
        List<Instrutor> instrutores = instrutorRepository.findAll();

        model.addAttribute("aulas", aulas);
        model.addAttribute("instrutores", instrutores);

        return "aulas";
    }

    @PostMapping("/aula/cadastrar")
    @ResponseBody
    public Map<String, Object> cadastrarAula(
            @RequestParam("nome") String nome,
            @RequestParam("duracao") String duracao,
            @RequestParam("descricao") String descricao,
            @RequestParam("instrutorId") int instrutorId) {

        Aula aula = new Aula();
        aula.setNome(nome);
        aula.setDuracao(duracao);
        aula.setDescricao(descricao);

        Instrutor instrutor = instrutorRepository.findById(instrutorId).orElse(null);
        if (instrutor != null) {
            aula.setInstrutor(instrutor);
        }

        aula = aulaRepository.save(aula);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("id", aula.getId());

        return resposta;
    }

    @GetMapping("/editar-aula/{id}")
    public String carregarDadosEdicao(@PathVariable("id") int id, Model model) {
        Aula aula = aulaRepository.findById(id).orElse(null);
        if (aula != null) {
            model.addAttribute("aula", aula);
            model.addAttribute("instrutores", instrutorRepository.findAll());
        }
        return "aulas";
    }

    @PutMapping("/aula/editar/{id}")
    @ResponseBody
    public ResponseEntity<String> editarAula(
            @PathVariable("id") int id,
            @RequestParam("nome") String nome,
            @RequestParam("duracao") String duracao,
            @RequestParam("descricao") String descricao,
            @RequestParam("instrutorId") int instrutorId) {

        Aula aula = aulaRepository.findById(id).orElse(null);
        if (aula != null) {
            aula.setNome(nome);
            aula.setDuracao(duracao);
            aula.setDescricao(descricao);

            Instrutor instrutor = instrutorRepository.findById(instrutorId).orElse(null);
            if (instrutor != null) {
                aula.setInstrutor(instrutor);
            }

            aulaRepository.save(aula);
            return ResponseEntity.ok("Aula editada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Aula não encontrada!");
        }
    }

    @DeleteMapping("/aula/remover/{id}")
    @ResponseBody
    public ResponseEntity<String> removerAula(@PathVariable("id") int id) {
        try {
            aulaService.remover(id);
            return ResponseEntity.ok("Aula removida com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao remover aula!");
        }
    }
}