package com.senac.gerenciador_academia.controller;

import com.senac.gerenciador_academia.model.Especialidade;
import com.senac.gerenciador_academia.repository.EspecialidadeRepository;
import com.senac.gerenciador_academia.service.EspecialidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
public class EspecialidadeController {

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Autowired
    private EspecialidadeService especialidadeService;

    @GetMapping("/especialidades")
    public String listarEspecialidades(Model model) {
        List<Especialidade> especialidades = especialidadeRepository.findAll();
        model.addAttribute("especialidades", especialidades);
        return "especialidades";
    }

    @PostMapping("/especialidade/cadastrar")
    public String cadastrarEspecialidade(@RequestParam("nome") String nome) {
        Especialidade especialidade = new Especialidade();
        especialidade.setNome(nome);
        especialidadeRepository.save(especialidade);
        return "redirect:/especialidades";
    }

    @GetMapping("/especialidade/editar/{id}")
    public String editarEspecialidade(@PathVariable("id") int id, Model model) {
        Especialidade especialidade = especialidadeRepository.findById(id).orElse(null);
        if (especialidade != null) {
            model.addAttribute("especialidade", especialidade);
        }
        return "editarEspecialidade";
    }

    @PostMapping("/especialidade/editar")
    public String editarEspecialidade(@RequestParam("id") int id, @RequestParam("nome") String nome) {
        Especialidade especialidade = especialidadeRepository.findById(id).orElse(null);
        if (especialidade != null) {
            especialidade.setNome(nome);
            especialidadeRepository.save(especialidade);
        }
        return "redirect:/especialidades";
    }

    @DeleteMapping("/especialidade/remover/{id}")
    @ResponseBody
    public ResponseEntity<String> removerEspecialidade(@PathVariable("id") int id) {
        try {
            especialidadeService.remover(id);
            return ResponseEntity.ok("Especialidade removida com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao remover especialidade!");
        }
    }

}
