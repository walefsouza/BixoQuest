package controller.command;

import application.RotasFixas;
import application.SceneManager;

public class MenuPausaCommand implements ICommand {

    @Override
    public void executar() {
        SceneManager.abrirModal(RotasFixas.MENUPAUSE.getRotaFixa());
    }
}