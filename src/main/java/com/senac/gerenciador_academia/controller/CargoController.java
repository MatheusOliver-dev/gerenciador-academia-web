package com.senac.gerenciador_academia.controller;

import com.senac.gerenciador_academia.model.Cargo;
import com.senac.gerenciador_academia.repository.CargoRepository;
import com.senac.gerenciador_academia.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
public class CargoController {

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private CargoService cargoService;

    @GetMapping("/cargos")
    public String listarCargos(Model model) {
        List<Cargo> cargos = cargoRepository.findAll();
        model.addAttribute("cargos", cargos);
        return "cargos";
    }

    @PostMapping("/cargo/cadastrar")
    public String cadastrarCargo(@RequestParam("nome") String nome, 
                                 @RequestParam("salario") double salario, 
                                 @RequestParam("comissao") double comissao) {
        Cargo cargo = new Cargo();
        cargo.setNome(nome);
        cargo.setSalario(salario);
        cargo.setComissao(comissao);
        cargoRepository.save(cargo);
        return "redirect:/cargos";
    }

    @GetMapping("/cargo/editar/{id}")
    public String editarCargo(@PathVariable("id") int id, Model model) {
        Cargo cargo = cargoRepository.findById(id).orElse(null);
        if (cargo != null) {
            model.addAttribute("cargo", cargo);
        }
        return "editarCargo";
    }

    @PostMapping("/cargo/editar")
    public String editarCargo(@RequestParam("id") int id, 
                              @RequestParam("nome") String nome, 
                              @RequestParam("salario") double salario, 
                              @RequestParam("comissao") double comissao) {
        Cargo cargo = cargoRepository.findById(id).orElse(null);
        if (cargo != null) {
            cargo.setNome(nome);
            cargo.setSalario(salario);
            cargo.setComissao(comissao);
            cargoRepository.save(cargo);
        }
        return "redirect:/cargos";
    }

    @DeleteMapping("/cargo/remover/{id}")
    @ResponseBody
    public ResponseEntity<String> removerCargo(@PathVariable("id") int id) {
        try {
            cargoService.remover(id);
            return ResponseEntity.ok("Cargo removido com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao remover cargo!");
        }
    }
}