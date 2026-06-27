package controller.setups; // Ajuste conforme seu pacote

import application.RotasFixas;
import application.SceneManager;
import application.SessaoSingleton;
import application.Utilitarios;
import com.google.gson.reflect.TypeToken;
import controller.setups.TaskItemController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Game;
import model.atividades.Evento;
import model.atividades.Task;
import repository.IRepository;
import repository.Repository;
import service.AtividadeService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TasksSemanaisController implements Initializable {

    @FXML private VBox vboxTarefas;
    @FXML private ImageView btnVoltar;
    @FXML private AnchorPane painelFundo;

    private Game game;
    private AtividadeService atividadeService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        IRepository tasksRepo = new Repository("dados/bancotasks.json", new TypeToken<ArrayList<Task>>(){}.getType());
        IRepository eventosRepo = new Repository("dados/eventos-bixoquest.json", new TypeToken<ArrayList<Evento>>(){}.getType());

        this.atividadeService = new AtividadeService(tasksRepo, eventosRepo);
        this.game = SessaoSingleton.getInstancia().getGame();

        Platform.runLater(() -> renderizarTarefas());
    }

    public void renderizarTarefas() {

        vboxTarefas.getChildren().clear();
        List<Task> tarefasDaSemana = game.getSemestre().getBancoTasks();

        if (tarefasDaSemana == null || tarefasDaSemana.isEmpty()) {
            System.out.println("Lista de tarefas vazia??");
            return;
        }

        for (Task task : tarefasDaSemana) {

            if (!task.getRealizada()) {

                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/overlays/cardTaskBase.fxml")); // Ajuste a rota
                    Parent card = loader.load();

                    TaskItemController controllerDoCard = loader.getController();

                    controllerDoCard.preencherTask(task, this);

                    vboxTarefas.getChildren().add(card);
                }

                catch (IOException e) {
                    System.out.println("Problema no carregamento de Tasks");
                    e.printStackTrace();
                }
            }
        }
    }

    public void botaoVoltarMenuInicial() {
        Utilitarios.animarClique(btnVoltar, () -> fecharModal()
        );
    }

    public AtividadeService getAtividadeService() {
        return this.atividadeService;
    }

    private void fecharModal() {
        Stage stage = (Stage) painelFundo.getScene().getWindow();
        stage.close();
    }

    public AnchorPane getPainelFundo() {
        return this.painelFundo;
    }
}