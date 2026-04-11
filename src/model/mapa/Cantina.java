package model.mapa;

import model.atividades.EventoPassarMal;
import model.entidades.Jogador;

public class Cantina extends Local {

    private int tamanhoFila;

    // construtor

    public Cantina(String nome, String descricao, int tamanhoFila) {
        super(nome, descricao);
        this.tamanhoFila = tamanhoFila;
    }

    // métodos

    public int calcularTempoDeEspera() {
        return this.tamanhoFila * 2;
    }

    // getters
    public int getTamanhoFila() {
        return tamanhoFila;
    }

    @Override
    public TipoLocal getTipo() {
        return TipoLocal.CANTINA;
    }

    // setters

    public void setTamanhoFila(int tamanhoFila) {
        this.tamanhoFila = tamanhoFila;
    }


}
