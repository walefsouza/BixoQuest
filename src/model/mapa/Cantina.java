package model.mapa;

import model.atividades.EventoPassarMal;
import model.entidades.Jogador;

public class Cantina extends Local {

    private int tamanhoFila;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -
    public Cantina(String nome, String descricao, int tamanhoFila) {
        super(nome, descricao);
        this.tamanhoFila = tamanhoFila;
    }

    // Método  - - - - - - - - - - - - - - - - - - - - - - - -

    public int calcularTempoDeEspera() {
        return this.tamanhoFila * 2;
    }

    // Getters  - - - - - - - - - - - - - - - - - - - - - - - -

    public int getTamanhoFila() {
        return tamanhoFila;
    }

    @Override
    public TipoLocal getTipo() {
        return TipoLocal.CANTINA;
    }

    // Setter  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setTamanhoFila(int tamanhoFila) {
        this.tamanhoFila = tamanhoFila;
    }


}
