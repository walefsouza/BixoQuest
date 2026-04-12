package model;

import model.academico.Semestre;
import model.entidades.Jogador;
import model.mapa.UniversidadeMapa;
import repository.IGeneralGetNome;

public class Game implements IGeneralGetNome {

    private String nome;
    private boolean formado;
    private Jogador jogador;
    private Semestre semestre;
    private UniversidadeMapa mapa;
    private static final int TOTAL_SEMESTRES = 8;

    // construtor

    public Game(String nome, Jogador jogador, Semestre semestre, UniversidadeMapa mapa) {
        this.nome = nome;
        //this.lastGameplay =; pensar para o futuro
        this.jogador = jogador;
        this.semestre = semestre;
        this.mapa = mapa;
        this.formado = false;

    }

    // métodos

    @Override
    public String capturarNome() {
        return this.getNome();
    }

    public int calcularProgresso(){
        int calculo = (semestre.getNumero() * 100) / TOTAL_SEMESTRES;
        return calculo;
    }

    // setters

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setSemestre(Semestre semestre){
        this.semestre = semestre;
    }

    public void formarJogador() {
        this.formado = true;
    }



    // getters

    public String getNome(){
        return this.nome;
    }

    public Jogador getJogador(){
        return this.jogador;
    }

    public UniversidadeMapa getMapa(){
        return this.mapa;
    }

    public Semestre getSemestre(){
        return this.semestre;
    }

    public boolean verificarFormado() {
        return this.formado;
    }


}
