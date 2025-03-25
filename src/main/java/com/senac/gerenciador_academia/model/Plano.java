package com.senac.gerenciador_academia.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "plano")
public class Plano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String nome;
    private double precoMensal;
    private String recursosIncluidos;

    public Plano(String nome, double precoMensal, String recursosIncluidos) {
        this.nome = nome;
        this.precoMensal = precoMensal;
        this.recursosIncluidos = recursosIncluidos;
    }
    
    public Plano(){
    }  
}