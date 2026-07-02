package model.entidades;

import repository.IGeneralGetNome;

public abstract class Entidade implements IGeneralGetNome {

    // Atributos - - - - - - - - - - - - - - - - - - - - - - - - -

    private String nome;
    private int idade;
    private int humor;
    private String aparencia;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public Entidade(String nome, int idade, int humor, String aparencia) {
        this.nome = nome;
        this.idade = idade;
        this.humor = humor;
        this.aparencia = aparencia;
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

    // Aparência  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setAparencia(String aparecia) {
        this.aparencia = aparencia;
    }

    public String getAparencia(){
        return this.aparencia;
    }

    // Interface  - - - - - - - - - - - - - - - - - - - - - - - -

    @Override
    public String capturarNome() {
        return this.getNome();
    }



}
