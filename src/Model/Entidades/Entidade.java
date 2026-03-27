package Model.Entidades;

public abstract class Entidade {

    private String nome;
    private int idade;
    private int humor;

    //construtor

    public Entidade(String nome, int idade, int humor) {
        this.nome = nome;
        this.idade = idade;
        this.humor = humor;
    }

    // setters

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setHumor(int humor) {
        this.humor = humor;
    }

    // getters

    public String getNome(){
        return this.nome;
    }

    public int getHumor(){
        return this.humor;
    }

    public int getIdade(){
        return this.idade;
    }

}
