package model.mapa;

public class Colegiado extends Local {

    private boolean sistemaAtivo;

    // construtor

    public Colegiado(String nome, String descricao, boolean sistemaAtivo) {
        super(nome, descricao);
        this.sistemaAtivo = sistemaAtivo;
    }

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - -

    public boolean verificarAtendimento() {
        if (!this.sistemaAtivo) {
            return false;
        }
        return true;
    }

    // getters

    public boolean getSistemaAtivo() {
        return this.sistemaAtivo;
    }

    @Override
    public TipoLocal getTipo() {
        return TipoLocal.COLEGIADO;
    }

    // setters

    public void setSistemaAtivo(boolean sistemaAtivo) {
        this.sistemaAtivo = sistemaAtivo;
    }
}