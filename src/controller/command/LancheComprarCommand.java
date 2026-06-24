package controller.command;

import application.SessaoSingleton;
import model.Game;
import model.mapa.Cardapio;

public class LancheComprarCommand implements ICommand {

    private String nomeLanche;
    private int preco;
    private Game jogoAtual;

    public LancheComprarCommand(Cardapio item) {
        this.nomeLanche = item.getNome();
        this.preco = item.getPreco();
        this.jogoAtual = SessaoSingleton.getInstancia().getGame();
    }

    @Override
    public void executar() {

        double dinheiro = jogoAtual.getJogador().getDinheiro();

        if (dinheiro >= preco) {

            jogoAtual.getJogador().decrementarDinheiro(dinheiro - preco);
        }

        else {
            System.out.println("Você não tem dinheiro para comprar " + nomeLanche);
            // pensar em algum alerta para dizer que não há dinheiro disponível
        }
    }
}