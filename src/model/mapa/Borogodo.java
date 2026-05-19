package model.mapa;

public class Borogodo extends Local {

    private int nivelAglomeracao;
    private boolean atividadeAtiva;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public Borogodo(String nome, String descricao,String imagemFundo, String musicaFundo,
                    int nivelAglomeracao, boolean atividadeAtiva) {

        super(nome, descricao, imagemFundo, musicaFundo);
        this.nivelAglomeracao = nivelAglomeracao;
        this.atividadeAtiva = atividadeAtiva;
    }

    // Getters  - - - - - - - - - - - - - - - - - - - - - - - -

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

    // Setters  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setAtividadeAtiva(boolean atividadeAtiva) {
        this.atividadeAtiva = atividadeAtiva;
    }

    public void setNivelAglomeracao(int nivelAglomeracao) {
        this.nivelAglomeracao = nivelAglomeracao;
    }
}