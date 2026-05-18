package model.entidades;

import model.academico.Disciplina;

public class Professor extends Entidade {

    private int credibilidade;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public Professor (String nome, int idade, int humor, String aparencia, int credibilidade) {
        super(nome, idade, humor, aparencia);
        this.credibilidade = credibilidade;
    }

    // Credibilidade  - - - - - - - - - - - - - - - - - - - - - - - -

    public int getCredibilidade() {
        return this.credibilidade;
    }

    public void aumentarCredibilidade(int valor) {
        this.credibilidade = Math.min(100, this.credibilidade + valor);
    }

    public void decrementarCredibilidade(int valor) {
        this.credibilidade = Math.max(0, this.credibilidade - valor);
    }

    // Tipo Entidade  - - - - - - - - - - - - - - - - - - - - - - - -

    public TipoEntidade getTipoEntidade() {
        return TipoEntidade.PROFESSOR;
    }

}
