package com.example.mobile;

import java.io.Serializable;

public class MedicamentoModel implements Serializable {
    private int id;
    private String nome;
    private String descricao;
    private String horario;
    private boolean tomado;

    public MedicamentoModel() {}

    public MedicamentoModel(int id, String nome, String descricao, String horario, boolean tomado) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.horario = horario;
        this.tomado = tomado;
    }


    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getHorario() { return horario; }
    public boolean isTomado() { return tomado; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setHorario(String horario) { this.horario = horario; }
    public void setTomado(boolean tomado) { this.tomado = tomado; }
}