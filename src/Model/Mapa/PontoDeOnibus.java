package Model.Mapa;
import Model.Entidades.Jogador;

public class PontoDeOnibus extends Local{

    public PontoDeOnibus(String nome, String descricao) {
        super(nome, descricao);
    }
    public void restart(Jogador j) {
        j.setEnergia(100);
        // acrescentar uma semana vencida no semestre;
    }

    public String descreverLocal(){
        return "Sabia que aqui é o lugar que você pode recarregar suas energias?";
        // eu tenho a string descrição, mas estou em dúvida se esse atributo deve existir, se o jogador vai
        //procurar a descrição ou ela aparece como uma mensagem aleatória como curiosidade...
        // algo parecido com a tela de login do minecraft, várias mensagens aleatórias
        // e se eu implementar isso para toda vez que o jogador mudar de lugar?
    }
}
