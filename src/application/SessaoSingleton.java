package application;

import model.Game;

public class SessaoSingleton {

    private static SessaoSingleton instancia;
    private String ultimaRota;
    private Game game;

    private SessaoSingleton() {}

    public static SessaoSingleton getInstancia() {

        if (instancia == null) {
            instancia = new SessaoSingleton();
        }

        return instancia;
    }

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
