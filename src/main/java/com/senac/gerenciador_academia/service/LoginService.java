package com.senac.gerenciador_academia.service;

import com.senac.gerenciador_academia.model.Usuario;
import com.senac.gerenciador_academia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario autenticar(String usuario, String senha) {
        return usuarioRepository.findByUsuarioAndSenha(usuario, senha);
    }
}