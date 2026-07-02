package controller.setups;

import application.SceneManager;
import application.SessaoSingleton;
import application.Utilitarios;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import model.Game;
import model.atividades.ResultadoAcao;
import model.atividades.Task;
import static application.Utilitarios.configurarClique;

public class TaskItemController {

    // Interface - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @FXML private Label nome;
    @FXML private Label local;
    @FXML private Label custo;
    @FXML private AnchorPane taskPane;
    @FXML private Tooltip tooltipNome;

    // Atributos - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private Game game = SessaoSingleton.getInstancia().getGame();
    private Task task;
    private TasksSemanaisController controller; // Guarda quem é o Pai

    // Inicialização - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public void initialize() {

        configurarClique(taskPane);

        // para caso o nome seja maior que a caixa
        if (tooltipNome != null) {
            tooltipNome.setShowDelay(Duration.ZERO);
        }
    }

    // Preenche os cards da task com base na task passada como parâmetro
    public void preencherTask(Task task, TasksSemanaisController controller) {

        this.task = task;
        this.controller = controller;

        this.nome.setText(task.getNome());
        this.tooltipNome.setText(task.getNome());

        this.local.setText(task.getLocalAtividade().toString());
        this.custo.setText(String.valueOf(task.getCustoEnergia()));
    }

    // Quando o jogador clica no pane da task, ela é marcada como realizada
    @FXML
    public void clicarPane(MouseEvent event) {
        Utilitarios.animarClique(taskPane, () -> {

            ResultadoAcao resultado = controller.getAtividadeService().executarTask(task, game);

            if (resultado != null) {

                // Mensagem de sucesso
                if (resultado.getSucesso()) {
                    SceneManager.mostrarCardNotificacao(
                            controller.getPainelFundo(),
                            resultado.getTitulo(),
                            resultado.getTextoNarrativo(),
                            "/resources/icones/interface-icon-task.png"
                    );
                }

                // Mensagem de erro
                else {
                    SceneManager.mostrarCardNotificacao(
                            controller.getPainelFundo(),
                            resultado.getTitulo(),
                            resultado.getTextoNarrativo(),
                            "/resources/icones/interface-icon-erro.png"
                    );
                }

                controller.renderizarTarefas();
            }
        });
    }

}