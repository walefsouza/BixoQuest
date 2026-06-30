package controller.locais;

import application.AudioManager;
import application.RotasFixas;
import application.SceneManager;
import application.Utilitarios;
import controller.command.ICommand;
import controller.command.InteragirSocialmenteCommand;
import controller.command.PassarSemanaCommand;
import controller.command.PosicionarNPCsCommand;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.interacao.Dialogo;
import model.mapa.TipoLocal;
import service.InteracaoService;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class PontoDeOnibusController implements Initializable {

    @FXML private ImageView btnMapa;
    @FXML private ImageView botaoPegarOnibus;
    @FXML private AnchorPane pane;
    @FXML private ImageView btnInteragir;
    @FXML private ImageView btnColega;

    private InteracaoService interacaoService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.interacaoService = new InteracaoService();

        double[][] pontosDeSpawn = {
                {200, 270},
                {540, 220},
                {950, 260},

        };

        PosicionarNPCsCommand comando = new PosicionarNPCsCommand(
                this.pane,
                TipoLocal.PONTO_DE_ONIBUS,
                pontosDeSpawn,
                interacaoService
        );

        comando.executar();

        AudioManager.getInstancia().tocarMusicaDeFundo("/resources/locais/audio/ponto-de-onibus.mp3");
    }

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

    @FXML
    public void botaoColega() {
        Utilitarios.animarClique(btnColega, () -> {
            ICommand comando = new InteragirSocialmenteCommand(this.pane);
            comando.executar();
        });
    }

    @FXML
    public void botaoInteragir() {
        Utilitarios.animarClique(btnInteragir, () -> {
            List<Dialogo> falas = interacaoService.buscarFalasDoLocal(TipoLocal.BOROGODO);

            if (!falas.isEmpty()) {

                Collections.shuffle(falas);
                Dialogo falaSorteada = falas.get(0);

                SceneManager.mostrarDialogoWarn(
                        this.pane,
                        "Alguém diz...",
                        falaSorteada.getTexto(),
                        "/resources/icones/interface-icon-colegas.png"
                );
            }

            else {

                SceneManager.mostrarDialogoWarn(
                        this.pane,
                        "Silêncio",
                        "Parece que não há muito sobre o que conversar aqui agora.",
                        "/resources/icones/interface-icon-erro.png"
                );
            }
        });
    }
}
