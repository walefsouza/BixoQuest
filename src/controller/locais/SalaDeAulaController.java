package controller.locais;

import application.RotasFixas;
import application.SceneManager;
import application.SessaoSingleton;
import application.Utilitarios;
import controller.command.VerAulaCommand;
import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.Game;

public class SalaDeAulaController {

    @FXML private ImageView btnMapa;
    @FXML private ImageView btnQuadro;
    @FXML private AnchorPane pane;

    @FXML
    public void initialize() {

        Game game = SessaoSingleton.getInstancia().getGame();

        if (game.getSemestre().getSemanaAtual() == 4) {
            DropShadow alertaVermelho = new DropShadow();
            alertaVermelho.setColor(Color.RED);
            alertaVermelho.setRadius(25.0);
            alertaVermelho.setSpread(0.3);

            btnQuadro.setEffect(alertaVermelho);
        }
    }

    public void botaoMapa() {
        Utilitarios.animarClique(btnMapa, () ->
                SceneManager.navegar(RotasFixas.MAPACENTRAL.getRotaFixa())
        );
    }

    @FXML
    public void assistirAula() {

        Game game = SessaoSingleton.getInstancia().getGame();

        if (game.getSemestre().getSemanaAtual() == 4) {

            // Fazer interface de avaliações com modal
            SceneManager.mostrarDialogoWarn(
                    pane,
                    "Semana de Avaliação",
                    "Prepare-se! A sua avaliação chegará em breve.",
                    "/resources/icones/interface-icon-avaliacao.png"
            );
        }

        else {
            new VerAulaCommand(pane).executar();
        }
    }
}
