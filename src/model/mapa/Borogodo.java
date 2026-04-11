package model.mapa;

public class Borogodo extends Local {

    private int nivelAglomeracao;
    private boolean atividadeAtiva;

    // construtor

    public Borogodo(String nome, String descricao, int nivelAglomeracao, boolean atividadeAtiva) {
        super(nome, descricao);
        this.nivelAglomeracao = nivelAglomeracao;
        this.atividadeAtiva = atividadeAtiva;
    }

    // métodos

    public int sorteNoTriguinho(int valorApostado) {

        double sorte = Math.random();

        // Precisa ter algum evento ou task ativo para o jogador fazer apostas
        if (!this.atividadeAtiva) {
            return valorApostado;
        }

        if (sorte <= 0.30) {
            return valorApostado * 3;
        }

        else {
            return 0;
        }

    }

    // getters

    public boolean getAtividadeAtiva() {
        return atividadeAtiva;
    }

    public int getNivelAglomeracao() {
        return nivelAglomeracao;
    }

    @Override
    public TipoLocal getTipo() {
        return TipoLocal.BOROGODO;
    }

    // setters

    public void setAtividadeAtiva(boolean atividadeAtiva) {
        this.atividadeAtiva = atividadeAtiva;
    }

    public void setNivelAglomeracao(int nivelAglomeracao) {
        this.nivelAglomeracao = nivelAglomeracao;
    }
}