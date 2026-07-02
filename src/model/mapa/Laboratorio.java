package model.mapa;

public class Laboratorio extends Local {

    // Atributos - - - - - - - - - - - - - - - - - - - - - - - - -

    private int nComputadores;
    private double multiplicadorEstudo;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public Laboratorio(String nome, String descricao,String imagemFundo,
                       String musicaFundo, int nComputadores, double multiplicadorEstudo) {

        super(nome, descricao, imagemFundo, musicaFundo);
        this.nComputadores = nComputadores;
        this.multiplicadorEstudo = multiplicadorEstudo;
    }

    // Método  - - - - - - - - - - - - - - - - - - - - - - - -

    public boolean temComputadorLivre() {
        return this.nComputadores > 0;
    }

    // Getters  - - - - - - - - - - - - - - - - - - - - - - - -

    public int getComputadoresDisponiveis() {
        return this.nComputadores;
    }

    public double getMultiplicadorEstudo() {
        return this.multiplicadorEstudo;
    }

    @Override
    public TipoLocal getTipo() {
        return TipoLocal.LABORATORIO;
    }

    // Setters  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setComputadoresDisponiveis(int computadoresDisponiveis) {
        this.nComputadores = computadoresDisponiveis;
    }

    public void setMultiplicadorEstudo(double multiplicadorEstudo) {
        this.multiplicadorEstudo = multiplicadorEstudo;
    }
}