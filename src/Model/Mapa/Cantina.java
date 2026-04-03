package Model.Mapa;

import Model.Entidades.Jogador;

public class Cantina extends Local {


    public Cantina(String nome, String descricao) {
        super(nome, descricao);
        this.adicionarEvento(new EventoPassarMal(
                "Passou mal",
                "Comeu algo estragado",
                this, //acontece aqui mesmo
                10, 0 , 0, -10, 0, 0,
        ));
    }

    public boolean comprarLanche(Cardapio c, Jogador j) {
        if (j.getDinheiro() >= c.getPreco()){
            j.decrementarDinheiro(c.getPreco());
            j.aumentarEnergia(5);
            return true;
        }
        return false;
    }

    @Override
    public String descreverLocal() {
        return "Compre um lanche, convserse com seus amigos e morra na fila";
    }
}
