package controller.locais;

import application.RotasFixas;
import application.SceneManager;
import application.Utilitarios;
import controller.command.PassarSemanaCommand;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class PontoDeOnibusController {

    @FXML private ImageView btnMapa;
    @FXML private ImageView botaoPegarOnibus;
    @FXML private AnchorPane pane;

    public void botaoMapa() {
        Utilitarios.animarClique(btnMapa, () ->
                SceneManager.navegar(RotasFixas.MAPACENTRAL.getRotaFixa())
        );
    }

    @FXML
    public void clicarPegarOnibus() {

        Utilitarios.animarClique(botaoPegarOnibus, () -> {
            javafx.application.Platform.runLater(() -> {
                new PassarSemanaCommand(pane).executar();
            });
        });
    }
}
