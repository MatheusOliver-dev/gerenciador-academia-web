package com.senac.gerenciador_academia.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "agendamento")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;

    @ManyToOne
    @JoinColumn(name = "aula_id", nullable = false)
    private Aula aula;

    private String observacao;
    private String status;

    public Agendamento(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Aula aula, String observacao, String status) {
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.aula = aula;
        this.observacao = observacao;
        this.status = status;
    }

    public Agendamento() {
    }
}
