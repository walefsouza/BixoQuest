package application;

import model.Game;

public class SessaoSingleton {

    // Atributos - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static SessaoSingleton instancia;
    private String ultimaRota;
    private Game game;

    // Singleton - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // A sessão singleton tem a finalidade de resgistrar em qual jogo o usuário
    // está no momento. Ajuda no controle e acesso aos atributos do jogador ativo

    private SessaoSingleton() {}

    public static SessaoSingleton getInstancia() {

        if (instancia == null) {
            instancia = new SessaoSingleton();
        }

        return instancia;
    }

    // Métodos - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getUltimaRota() {
        return ultimaRota;
    }

    public void setUltimaRota(String ultimaRota) {
        this.ultimaRota = ultimaRota;
    }
}
