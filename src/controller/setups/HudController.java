package controller.setups;

import application.SessaoSingleton;
import application.Utilitarios;
import controller.command.JogadorStatusCommand;
import controller.command.MenuPausaCommand;
import controller.command.TasksSemanaisCommand;
import controller.overlays.PlayerStatusController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import model.Game;
import model.atividades.Task;

import static application.Utilitarios.configurarClique;


public class HudController implements Initializable {

    @FXML private ImageView botaoTasks;
    @FXML private ImageView iconJogador;
    @FXML private ImageView botaoMenu;

    private Game jogoAtual;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.jogoAtual = SessaoSingleton.getInstancia().getGame();
        carregarAvatar();

        configurarClique(botaoMenu);
        configurarClique(botaoTasks);
        configurarClique(iconJogador);
    }

    // Recarregar Icone do Avatar - - - - - - - - - - - - - - - - - - - - - - - -
    private void carregarAvatar() {
        if (jogoAtual != null && jogoAtual.getJogador() != null) {

            String caminhoImagem = jogoAtual.getJogador().getAparencia();

            try {
                Image avatar = new Image(getClass().getResourceAsStream(caminhoImagem));
                iconJogador.setImage(avatar);
            }

            catch (Exception e) {
                System.out.println("IMagem não encontrada: " + caminhoImagem);
            }
        }
    }

    // Métodos dos botões - - - - - - - - - - - - - - - - - - - - - - - -

    @FXML
    public void clicarTasks(MouseEvent event) {

        Utilitarios.animarClique(botaoTasks, () -> {
            javafx.application.Platform.runLater(() -> {
                new TasksSemanaisCommand().executar();
            });
        });
    }

    @FXML
    public void clicarMenu(MouseEvent event) {

        Utilitarios.animarClique(botaoMenu, () -> {
            javafx.application.Platform.runLater(() -> {
                new MenuPausaCommand().executar();
            });
        });
    }

    @FXML
    public void clicarAvatar(MouseEvent event) {

        Utilitarios.animarClique(iconJogador, () -> {
            javafx.application.Platform.runLater(() -> {
                new JogadorStatusCommand().executar();
            });
        });
    }

}