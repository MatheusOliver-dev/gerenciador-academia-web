package com.senac.gerenciador_academia.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="aula")
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String nome;
    private String duracao;
    private String descricao;
    
    @ManyToOne
    @JoinColumn(name = "instrutor_id", nullable = false)
    private Instrutor instrutor;

    public Aula(String nome, String duracao, String descricao, Instrutor instrutor) {
        this.nome = nome;
        this.duracao = duracao;
        this.descricao = descricao;
        this.instrutor = instrutor;
    }

    public Aula() {
    }
}
