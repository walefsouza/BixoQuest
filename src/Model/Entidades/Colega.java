package Model.Entidades;

import java.util.Random;

public class Colega extends Entidade {

    public Colega (String nome, int idade, int humor){
        super(nome,idade, humor);
    }

    public void estudarJogador(Jogador j){
        j.aumentarLevelConhecimento(5);
    }

    public void conversarJogador(Jogador j){

        Random random = new Random();
        int[] valores = {-10, -5, 5, 10};
        int impacto = valores[random.nextInt(valores.length)];

        if (impacto > 0)
            j.aumentarMotivacao(impacto);

        else
            j.decrementarMotivacao(Math.abs(impacto));
    }
}
