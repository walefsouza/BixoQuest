package controller.command;

import application.RotasFixas;
import application.SceneManager;

public class JogadorStatusCommand implements ICommand {

    // Rota de transferência - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    public void executar() {
        SceneManager.navegar(RotasFixas.STATUSJOGADOR.getRotaFixa());
    }
}