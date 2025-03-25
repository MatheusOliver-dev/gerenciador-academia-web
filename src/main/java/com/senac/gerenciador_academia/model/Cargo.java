package com.senac.gerenciador_academia.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "cargo")
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String nome;
    private double salario;
    private double comissao;

    public Cargo(String nome, double salario, double comissao) {
        this.nome = nome;
        this.salario = salario;
        this.comissao = comissao;
    }

    public Cargo() {
    }
}