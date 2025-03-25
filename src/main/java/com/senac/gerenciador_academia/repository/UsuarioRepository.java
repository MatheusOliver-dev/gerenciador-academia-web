package com.senac.gerenciador_academia.repository;

import com.senac.gerenciador_academia.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Usuario findByUsuarioAndSenha(String usuario, String senha);
}