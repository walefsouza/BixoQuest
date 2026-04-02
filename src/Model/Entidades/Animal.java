package Model.Entidades;

public class Animal extends Entidade {

    private String especie;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public Animal(String nome, int idade, int humor, String especie) {
        super(nome, idade, humor);
        this.especie = especie;
    }

    // Setters  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    // Getters  - - - - - - - - - - - - - - - - - - - - - - - -

    public String getEspecie(){
        return this.especie;
    }

    // Métodos - - - - - - - - - - - - - - - - - - - - - - - -

    public void acariciarAnimal(Jogador j) {

        if (getHumor() < 50) {
            atacarJogador(j);
            return;
        }

        j.decrementarEnergia(5);
        j.aumentarMotivacao(5);
        aumentarHumor(5);

    }

    public void atacarJogador(Jogador j) {

        j.decrementarSaude(20);
        j.decrementarEnergia(10);
        j.decrementarMotivacao(10);
    }


}
