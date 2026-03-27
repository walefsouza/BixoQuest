package Model.Entidades;

public class Animal extends Entidade {

    private String especie;

    // construtor

    public Animal(String nome, int idade, int humor, String especie) {
        super(nome, idade, humor);
        this.especie = especie;
    }

    // setters

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    // getters

    public String getEspecie(){
        return this.especie;
    }

    // métodos

    public void acariciarAnimal(Jogador j) {
        if (j.getEnergia() > 0) {

            if (j.getEnergia() < 5) {
                j.setEnergia(0);
            }

            j.setEnergia(j.getEnergia()-5);
        }

        if (j.getMotivacao() < 100) {

            if (j.getMotivacao()+5 > 100) {

                int maxAdd = 100 - j.getMotivacao();
                j.setMotivacao(j.getMotivacao() + maxAdd);
            }

            j.setMotivacao(j.getMotivacao()+5);

        }
    }

    public void atacarJogador(Jogador j) {

        if (getHumor() < 50) {
            if (j.getDinheiro() > 20) {
                j.setSaude(j.getSaude()-20);
            }


        }
    }
}
