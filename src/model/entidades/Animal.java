package model.entidades;

public class Animal extends Entidade {

    private String especie;
    private int nivelFofura; // 0 a 5

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public Animal(String nome, int idade, int humor, String especie, int nivelFofura) {
        super(nome, idade, humor);
        this.especie = especie;
        this.nivelFofura = nivelFofura;
    }

    // Setters  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public void setNivelFofura(int nivelFofura) {
        this.nivelFofura = nivelFofura;
    }

    // Getters  - - - - - - - - - - - - - - - - - - - - - - - -

    public String getEspecie(){
        return this.especie;
    }

    public int getNivelFofura() {
        return this.nivelFofura;
    }

    // Métodos - - - - - - - - - - - - - - - - - - - - - - - -

    public boolean aceitaCarinho() {
        return this.getHumor() >= 50;
    }

    public int calcularDanoAtaque() {
        return 30; // por enquanto é fixo
    }

    public int calcularGanhoMotivacao() {
        return this.nivelFofura * 2;
    }
}
