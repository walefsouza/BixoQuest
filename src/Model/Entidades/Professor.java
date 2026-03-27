package Model.Entidades;

public class Professor extends Entidade {

    private int credibilidade;
    //private Disciplina disciplina;

    public Professor (String nome, int idade, int humor, int credibilidade) {
        super(nome, idade, humor);
        this.credibilidade = credibilidade;
        //this.disciplina = disciplina;
    }
}
