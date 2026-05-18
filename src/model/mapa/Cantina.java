package model.mapa;

public class Cantina extends Local {

    private int tamanhoFila;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -
    public Cantina(String nome, String descricao,String imagemFundo,
                   String musicaFundo, int tamanhoFila) {

        super(nome, descricao, imagemFundo, musicaFundo);
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
