package com.senac.gerenciador_academia.controller;

import com.senac.gerenciador_academia.model.Especialidade;
import com.senac.gerenciador_academia.model.Instrutor;
import com.senac.gerenciador_academia.repository.EspecialidadeRepository;
import com.senac.gerenciador_academia.repository.InstrutorRepository;
import com.senac.gerenciador_academia.service.InstrutorService;
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
public class InstrutorController {

    @Autowired
    private InstrutorRepository instrutorRepository;

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Autowired
    private InstrutorService instrutorService;

    @GetMapping("/instrutores")
    public String listarInstrutores(Model model) {
        List<Instrutor> instrutores = instrutorRepository.findAll();
        List<Especialidade> especialidades = especialidadeRepository.findAll();

        model.addAttribute("instrutores", instrutores);
        model.addAttribute("especialidades", especialidades);

        return "instrutores";
    }

    @PostMapping("/instrutor/cadastrar")
    @ResponseBody
    public Map<String, Object> cadastrarInstrutor(
            @RequestParam("nome") String nome,
            @RequestParam("email") String email,
            @RequestParam("cpf") String cpf,
            @RequestParam("telefone") String telefone,
            @RequestParam("especialidadeId") int especialidadeId) {

        Instrutor instrutor = new Instrutor();
        instrutor.setNome(nome);
        instrutor.setEmail(email);
        instrutor.setCpf(cpf);
        instrutor.setTelefone(telefone);

        Especialidade especialidade = especialidadeRepository.findById(especialidadeId).orElse(null);
        if (especialidade != null) {
            instrutor.setEspecialidade(especialidade);
        }

        instrutor = instrutorRepository.save(instrutor);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("id", instrutor.getId());

        return resposta;
    }

    @GetMapping("/editar/{id}")
    public String carregarDadosEdicao(@PathVariable("id") int id, Model model) {
        Instrutor instrutor = instrutorRepository.findById(id).orElse(null);
        if (instrutor != null) {
            model.addAttribute("instrutor", instrutor);
            model.addAttribute("especialidades", especialidadeRepository.findAll());
        }
        return "instrutores";
    }

    @PutMapping("/instrutor/editar/{id}")
    @ResponseBody
    public ResponseEntity<String> editarInstrutor(
            @PathVariable("id") int id,
            @RequestParam("nome") String nome,
            @RequestParam("email") String email,
            @RequestParam("cpf") String cpf,
            @RequestParam("telefone") String telefone,
            @RequestParam("especialidadeId") int especialidadeId) {

        Instrutor instrutor = instrutorRepository.findById(id).orElse(null);
        if (instrutor != null) {
            instrutor.setNome(nome);
            instrutor.setEmail(email);
            instrutor.setCpf(cpf);
            instrutor.setTelefone(telefone);

            Especialidade especialidade = especialidadeRepository.findById(especialidadeId).orElse(null);
            if (especialidade != null) {
                instrutor.setEspecialidade(especialidade);
            }

            instrutorRepository.save(instrutor);
            return ResponseEntity.ok("Instrutor editado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Instrutor não encontrado!");
        }
    }

    @DeleteMapping("/instrutor/remover/{id}")
    @ResponseBody
    public ResponseEntity<String> removerInstrutor(@PathVariable("id") int id) {
        try {
            instrutorService.remover(id);
            return ResponseEntity.ok("Instrutor removido com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao remover instrutor!");
        }
    }
}
