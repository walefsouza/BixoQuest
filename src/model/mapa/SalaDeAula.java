package model.mapa;

public class SalaDeAula extends Local {

    private boolean salaLivre;

    // construtor
    public SalaDeAula(String nome, String descricao, boolean salaLivre) {
        super(nome, descricao);
        this.salaLivre = salaLivre;
    }

    // getter
    public boolean getSalaLivre() {
        return this.salaLivre;
    }

    @Override
    public TipoLocal getTipo() {
        return TipoLocal.SALA_DE_AULA;
    }

    // setter
    public void setSalaLivre(boolean salaLivre) {
        this.salaLivre = salaLivre;
    }
}