package Model.Game;

import Model.Academico.Semestre;
import Model.Entidades.Jogador;
import Model.Mapa.UniversidadeMapa;

import java.util.Date;

public class Game {

    private String gameName;
    //private Date lastGameplay; decidir se vai ter ou não
    private Jogador jogador;
    private Semestre semestre;
    private UniversidadeMapa mapa;
    private static final int TOTAL_SEMESTRES = 8;

    // construtor

    public Game(String gameName, Jogador jogador, Semestre semestre, UniversidadeMapa mapa) {
        this.gameName = gameName;
        //this.lastGameplay =; pensar para o futuro
        this.jogador = jogador;
        this.semestre = semestre;
        this.mapa = mapa;

    }

    // métodos

    public int calcularProgresso(){
        int calculo = (semestre.getNumero() * 100) / TOTAL_SEMESTRES;
        return calculo;
    }

    // setters

    public void setGameName(String gameName){
        this.gameName = gameName;
    }


    // getters

    public String getGameName(){
        return this.gameName;
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


}
