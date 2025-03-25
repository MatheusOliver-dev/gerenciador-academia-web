package com.senac.gerenciador_academia.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        String usuarioLogado = (String) session.getAttribute("usuarioLogado");
        if (usuarioLogado != null) {
            model.addAttribute("usuarioLogado", usuarioLogado);
        }
        return "dashboard";
    }

    @GetMapping("site/agendamentos")
    public String agendamentos() {
        return "redirect:/agendamentos";
    }

    @GetMapping("site/aulas")
    public String aulas() {
        return "redirect:/aulas";
    }

    @GetMapping("site/cargos")
    public String cargos() {
        return "redirect:/cargos";
    }

    @GetMapping("/clientes")
    public String clientes() {
        return "/clientes";
    }

    @GetMapping("/estoque")
    public String estoque() {
        return "/estoque";
    }

    @GetMapping("/financeiro")
    public String financeiro() {
        return "/financeiro";
    }

    @GetMapping("site/funcionarios")
    public String funcionarios() {
        return "redirect:/funcionarios";
    }

    @GetMapping("site/instrutores")
    public String instrutores() {
        return "redirect:/instrutores";
    }

    @GetMapping("site/planos")
    public String planos() {
        return "redirect:/planos";
    }

    @GetMapping("/vendas")
    public String vendas() {
        return "/vendas";
    }
}