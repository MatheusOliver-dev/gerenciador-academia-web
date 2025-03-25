package com.senac.gerenciador_academia.controller;

import com.senac.gerenciador_academia.model.Cargo;
import com.senac.gerenciador_academia.model.Funcionario;
import com.senac.gerenciador_academia.repository.CargoRepository;
import com.senac.gerenciador_academia.repository.FuncionarioRepository;
import com.senac.gerenciador_academia.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping("/funcionarios")
    public String listarFuncionarios(Model model) {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        List<Cargo> cargos = cargoRepository.findAll();

        model.addAttribute("funcionarios", funcionarios);
        model.addAttribute("cargos", cargos);

        return "funcionarios";
    }

    @PostMapping("/funcionario/cadastrar")
    @ResponseBody
    public Map<String, Object> cadastrarFuncionario(
            @RequestParam("nome") String nome,
            @RequestParam("cpf") String cpf,
            @RequestParam("cargoId") int cargoId) {

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setCpf(cpf);

        Cargo cargo = cargoRepository.findById(cargoId).orElse(null);
        if (cargo != null) {
            funcionario.setCargo(cargo);
        }

        funcionario = funcionarioRepository.save(funcionario);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("id", funcionario.getId());

        return resposta;
    }

    @GetMapping("/editar-funcionario/{id}")
    public String carregarDadosEdicao(@PathVariable("id") int id, Model model) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElse(null);
        if (funcionario != null) {
            model.addAttribute("funcionario", funcionario);
            model.addAttribute("cargos", cargoRepository.findAll());
        }
        return "funcionarios";
    }

    @PutMapping("/funcionario/editar/{id}")
    @ResponseBody
    public ResponseEntity<String> editarFuncionario(
            @PathVariable("id") int id,
            @RequestParam("nome") String nome,
            @RequestParam("cpf") String cpf,
            @RequestParam("cargoId") int cargoId) {

        Funcionario funcionario = funcionarioRepository.findById(id).orElse(null);
        if (funcionario != null) {
            funcionario.setNome(nome);
            funcionario.setCpf(cpf);

            Cargo cargo = cargoRepository.findById(cargoId).orElse(null);
            if (cargo != null) {
                funcionario.setCargo(cargo);
            }

            funcionarioRepository.save(funcionario);
            return ResponseEntity.ok("Funcionário editado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Funcionário não encontrado!");
        }
    }

    @DeleteMapping("/funcionario/remover/{id}")
    @ResponseBody
    public ResponseEntity<String> removerFuncionario(@PathVariable("id") int id) {
        try {
            funcionarioService.remover(id);
            return ResponseEntity.ok("Funcionário removido com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao remover funcionário!");
        }
    }
}