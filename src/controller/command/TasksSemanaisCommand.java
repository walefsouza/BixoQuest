package controller.command;

import application.RotasFixas;
import application.SceneManager;

public class TasksSemanaisCommand implements ICommand {

    // Implementação - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    public void executar() {
        SceneManager.abrirModal(RotasFixas.TASKSSEMANAIS.getRotaFixa());
    }
}