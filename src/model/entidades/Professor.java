package model.entidades;

import model.academico.Disciplina;

public class Professor extends Entidade {

    private int credibilidade;
    private Disciplina disciplina;

    public Professor (String nome, int idade, int humor, int credibilidade, Disciplina disciplina) {
        super(nome, idade, humor);
        this.credibilidade = credibilidade;
        this.disciplina = disciplina;
    }

    // Credibilidade  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setCredibilidade(int saude) {
        this.credibilidade = saude;
    }

    public int getCredibilidade() {
        return this.credibilidade;
    }

    public void aumentarCredibilidade(int valor) {
        this.credibilidade = Math.min(100, this.credibilidade + valor);
    }

    public void decrementarCredibilidade(int valor) {
        this.credibilidade = Math.max(0, this.credibilidade - valor);
    }

    // Disciplina   - - - - - - - - - - - - - - - - - - - - - - - -

    public void setDisciplina(Disciplina d){
        this.disciplina = d;
    }

    public Disciplina getDisciplina(){
        return this.disciplina;
    }
}
