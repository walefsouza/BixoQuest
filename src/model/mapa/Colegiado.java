package model.mapa;

public class Colegiado extends Local {

    private boolean sistemaAtivo;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public Colegiado(String nome, String descricao,String imagemFundo,
                     String musicaFundo, boolean sistemaAtivo) {

        super(nome, descricao,imagemFundo, musicaFundo);
        this.sistemaAtivo = sistemaAtivo;
    }

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - -

    public boolean verificarAtendimento() {
        if (!this.sistemaAtivo) {
            return false;
        }
        return true;
    }

    // Getters  - - - - - - - - - - - - - - - - - - - - - - - -

    public boolean getSistemaAtivo() {
        return this.sistemaAtivo;
    }

    @Override
    public TipoLocal getTipo() {
        return TipoLocal.COLEGIADO;
    }

    // Setter  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setSistemaAtivo(boolean sistemaAtivo) {
        this.sistemaAtivo = sistemaAtivo;
    }
}