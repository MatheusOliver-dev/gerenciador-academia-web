package com.senac.gerenciador_academia.controller;

import com.senac.gerenciador_academia.model.Agendamento;
import com.senac.gerenciador_academia.model.Aula;
import com.senac.gerenciador_academia.service.AgendamentoService;
import com.senac.gerenciador_academia.service.AulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private AulaService aulaService;

    @GetMapping
    public String listarAgendamentos(Model model) {
        List<Agendamento> agendamentos = agendamentoService.listarTodos();
        model.addAttribute("agendamentos", agendamentos);

        List<Aula> aulas = aulaService.listarTodas();
        model.addAttribute("aulas", aulas);

        return "agendamentos";
    }

    @PostMapping("/cadastrar")
    public String cadastrarAgendamento(
            @RequestParam("inicio") String inicioStr,
            @RequestParam("fim") String fimStr,
            @RequestParam("aulaId") int aulaId,
            @RequestParam("observacao") String observacao,
            @RequestParam("status") String status) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        LocalDateTime inicio = LocalDateTime.parse(inicioStr, formatter);
        LocalDateTime fim = LocalDateTime.parse(fimStr, formatter);

        Agendamento agendamento = new Agendamento();
        agendamento.setDataHoraInicio(inicio);
        agendamento.setDataHoraFim(fim);
        agendamento.setAula(aulaService.buscarPorId(aulaId));
        agendamento.setObservacao(observacao);
        agendamento.setStatus(status);

        agendamentoService.salvar(agendamento);
        return "redirect:/agendamentos";
    }

    @GetMapping("/editar/{id}")
    public String editarAgendamento(@PathVariable int id, Model model) {
        Agendamento agendamento = agendamentoService.buscarPorId(id);
        model.addAttribute("agendamento", agendamento);

        List<Aula> aulas = aulaService.listarTodas();
        model.addAttribute("aulas", aulas);

        return "editarAgendamento";
    }

    @PostMapping("/editar")
    public String salvarEdicaoAgendamento(
            @RequestParam("id") int id,
            @RequestParam("inicio") String inicioStr,
            @RequestParam("fim") String fimStr,
            @RequestParam("aulaId") int aulaId,
            @RequestParam("observacao") String observacao,
            @RequestParam("status") String status) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        LocalDateTime inicio = LocalDateTime.parse(inicioStr, formatter);
        LocalDateTime fim = LocalDateTime.parse(fimStr, formatter);

        Agendamento agendamento = agendamentoService.buscarPorId(id);
        agendamento.setDataHoraInicio(inicio);
        agendamento.setDataHoraFim(fim);
        agendamento.setAula(aulaService.buscarPorId(aulaId));
        agendamento.setObservacao(observacao);
        agendamento.setStatus(status);

        agendamentoService.salvar(agendamento);
        return "redirect:/agendamentos";
    }

    @GetMapping("/remover/{id}")
    public String removerAgendamento(@PathVariable int id) {
        agendamentoService.remover(id);
        return "redirect:/agendamentos";
    }
    
    @GetMapping("/{id}")
    @ResponseBody
    public Agendamento obterAgendamento(@PathVariable int id) {
        Agendamento agendamento = agendamentoService.buscarPorId(id);
        return agendamento;
    }
}