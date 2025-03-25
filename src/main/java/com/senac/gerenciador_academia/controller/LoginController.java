package com.senac.gerenciador_academia.controller;

import com.senac.gerenciador_academia.model.Usuario;
import com.senac.gerenciador_academia.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SessionAttributes("usuarioLogado")
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public String login(@RequestParam("usuario") String usuario,
            @RequestParam("senha") String senha,
            HttpSession session,
            Model model) {

        Usuario usuarioAutenticado = usuarioRepository.findByUsuarioAndSenha(usuario, senha);

        if (usuarioAutenticado != null) {
            session.setAttribute("usuarioLogado", usuarioAutenticado.getUsuario());

            model.addAttribute("usuarioLogado", usuarioAutenticado.getUsuario());

            return "redirect:/dashboard";
        }

        model.addAttribute("erro", "Usuário ou senha inválidos");
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
