package model;

import model.academico.Semestre;
import model.entidades.Jogador;
import model.mapa.UniversidadeMapa;
import repository.IGeneralGetNome;

import java.util.ArrayList;
import java.util.List;

public class Game implements IGeneralGetNome {

    private String nome;
    private boolean formado;
    private Jogador jogador;
    private Semestre semestre;
    private static final int TOTAL_SEMESTRES = 6;
    private List<String> eventosRealizados;
    private List<String> tasksRealizadas;
    private boolean flagSemana;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public Game(String nome, Jogador jogador, Semestre semestre) {
        this.nome = nome;
        this.jogador = jogador;
        this.semestre = semestre;
        this.formado = false;
        this.tasksRealizadas = new ArrayList<>();
        this.eventosRealizados = new ArrayList<>();
        this.flagSemana = false;

    }

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - -

    @Override
    public String capturarNome() {
        return this.getNome();
    }

    public int calcularProgresso(){
        int calculo = (semestre.getNumero() * 100) / TOTAL_SEMESTRES;
        return calculo;
    }

    // Setters  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setSemestre(Semestre semestre){
        this.semestre = semestre;
    }

    public void formarJogador() {
        this.formado = true;
    }

    public void setEventoRealizado (String nomeEvento) {
        this.eventosRealizados.add(nomeEvento);
    }

    public void setTasksRealizadas (String nomeEvento) {
        if (!tasksRealizadas.contains(nomeEvento)) {
            this.tasksRealizadas.add(nomeEvento);
        }
    }

    public void setFlagSemana(boolean flagSemana) {
        this.flagSemana = flagSemana;
    }

    // Getters  - - - - - - - - - - - - - - - - - - - - - - - -

    public String getNome(){
        return this.nome;
    }

    public Jogador getJogador(){
        return this.jogador;
    }

    public Semestre getSemestre(){
        return this.semestre;
    }

    public boolean verificarFormado() {
        return this.formado;
    }

    public boolean getEventoRealizado (String nomeEvento) {
        return eventosRealizados.contains(nomeEvento);
    }

    public boolean getTaskRealizada (String nomeEvento) {
        return tasksRealizadas.contains(nomeEvento);
    }

    public boolean getFlagSemana() {
        return this.flagSemana;
    }
}
