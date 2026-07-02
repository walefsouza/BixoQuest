package controller.setups; // Ajuste conforme seu pacote

import application.*;
import com.google.gson.reflect.TypeToken;
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

    // Interface - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @FXML private VBox vboxTarefas;
    @FXML private ImageView btnVoltar;
    @FXML private AnchorPane painelFundo;

    // Atributos - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private Game game;
    private AtividadeService atividadeService;

    // Inicialização - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Repositórios
        IRepository tasksRepo = new Repository("dados/bancotasks.json", new TypeToken<ArrayList<Task>>(){}.getType());
        IRepository eventosRepo = new Repository("dados/eventos-bixoquest.json", new TypeToken<ArrayList<Evento>>(){}.getType());

        // Services
        this.atividadeService = new AtividadeService(tasksRepo, eventosRepo);
        this.game = SessaoSingleton.getInstancia().getGame();

        Platform.runLater(() -> renderizarTarefas());

        AudioManager.getInstancia().tocarMusicaDeFundo("/resources/locais/audio/musica-geral-game.mp3");
    }

    // Métodos - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    // Renderiza automaticamente após clicar em alguma task
    public void renderizarTarefas() {

        vboxTarefas.getChildren().clear();
        List<Task> tarefasDaSemana = game.getSemestre().getBancoTasks();

        if (tarefasDaSemana == null || tarefasDaSemana.isEmpty()) {
            System.out.println("Lista de tarefas vazia??");
            return;
        }

        // Para cada task, cria um card e adiciona ao painel vbox com scrool pane
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

    // Retorna a tela anterior as tasks
    public void botaoVoltarMenuInicial() {
        Utilitarios.animarClique(btnVoltar, () -> fecharModal()
        );
    }

    // Retorna o service desse controller
    public AtividadeService getAtividadeService() {
        return this.atividadeService;
    }

    // Fecha a tela de sobreposição
    private void fecharModal() {
        Stage stage = (Stage) painelFundo.getScene().getWindow();
        stage.close();
    }

    // Retorna pane do controller de tasks
    public AnchorPane getPainelFundo() {
        return this.painelFundo;
    }
}