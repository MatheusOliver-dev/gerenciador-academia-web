package com.senac.gerenciador_academia.controller;

import com.senac.gerenciador_academia.model.Plano;
import com.senac.gerenciador_academia.repository.PlanoRepository;
import com.senac.gerenciador_academia.service.PlanoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

@Controller
public class PlanoController {

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private PlanoService planoService;

    @GetMapping("/planos")
    public String listarPlanos(Model model) {
        List<Plano> planos = planoRepository.findAll();
        model.addAttribute("planos", planos);
        return "planos";
    }

    @PostMapping("/plano/cadastrar")
    public String cadastrarPlano(@RequestParam("nome") String nome,
            @RequestParam("precoMensal") String precoMensal,
            @RequestParam("recursosIncluidos") String recursosIncluidos) {

        precoMensal = precoMensal.replace("R$", "").replace(".", "").replace(",", ".");
        double preco = Double.parseDouble(precoMensal);

        Plano plano = new Plano(nome, preco, recursosIncluidos);
        planoRepository.save(plano);
        return "redirect:/planos";
    }

    @GetMapping("/plano/editar/{id}")
    public String editarPlano(@PathVariable("id") int id, Model model) {
        Plano plano = planoRepository.findById(id).orElse(null);
        if (plano != null) {
            model.addAttribute("plano", plano);
        }
        return "editarPlano";
    }

    @PostMapping("/plano/editar")
    public String editarPlano(@RequestParam("id") int id,
            @RequestParam("nome") String nome,
            @RequestParam("precoMensal") String precoMensal,
            @RequestParam("recursosIncluidos") String recursosIncluidos) {

        precoMensal = precoMensal.replace("R$", "").replace(".", "").replace(",", ".");
        double preco = Double.parseDouble(precoMensal);

        Plano plano = planoRepository.findById(id).orElse(null);
        if (plano != null) {
            plano.setNome(nome);
            plano.setPrecoMensal(preco);
            plano.setRecursosIncluidos(recursosIncluidos);
            planoRepository.save(plano);
        }
        return "redirect:/planos";
    }

    @DeleteMapping("/plano/remover/{id}")
    @ResponseBody
    public ResponseEntity<String> removerPlano(@PathVariable("id") int id) {
        try {
            planoService.remover(id);
            return ResponseEntity.ok("Plano removido com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao remover plano!");
        }
    }
}
