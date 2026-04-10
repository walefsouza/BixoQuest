package model.entidades;

public abstract class Entidade {

    private String nome;
    private int idade;
    private int humor;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public Entidade(String nome, int idade, int humor) {
        this.nome = nome;
        this.idade = idade;
        this.humor = humor;
    }

    // Nome  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome(){
        return this.nome;
    }

    // Idade  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getIdade() {
        return this.idade;
    }

    // Humor  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setHumor(int humor) {
        this.humor = humor;
    }

    public int getHumor(){
        return this.humor;
    }

    public void aumentarHumor(int valor) {
        this.humor = Math.min(100, this.humor + valor);
    }

    public void decrementarHumor(int valor) {
        this.humor = Math.max(0, this.humor - valor);
    }



}
