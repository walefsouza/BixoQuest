package controller.locais;

import application.*;
import controller.command.ICommand;
import controller.command.InteragirSocialmenteCommand;
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

public class BorogodoController implements Initializable {

    // Interface - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @FXML private ImageView btnMapa;
    @FXML private ImageView btnApostar;
    @FXML private AnchorPane pane;
    @FXML private ImageView btnInteragir;
    @FXML private ImageView btnColega;

    // Atributo - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private InteracaoService interacaoService;

    // Inicialização - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.interacaoService = new InteracaoService();

        double[][] pontosDeSpawn = {
                {200, 270},
                {440, 190},
                {670, 200},
                {950, 260},

        };

        // Posicionando NPCs no local
        PosicionarNPCsCommand comando = new PosicionarNPCsCommand(
                this.pane,
                TipoLocal.BOROGODO,
                pontosDeSpawn,
                interacaoService
        );

        comando.executar();

        AudioManager.getInstancia().tocarMusicaDeFundo("/resources/locais/audio/musica-tema-borogodo.mp3");

    }

    // Botões - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    // Mapa
    public void botaoMapa() {
        Utilitarios.animarClique(btnMapa, () ->
                SceneManager.navegar(RotasFixas.MAPACENTRAL.getRotaFixa())
        );
    }

    // Cassino
    public void botaoAposta() {
        Utilitarios.animarClique(btnApostar, () ->
                SceneManager.navegar(RotasFixas.CASSINO.getRotaFixa())
        );
    }

    // Conversar com colega
    @FXML
    public void botaoColega() {
        Utilitarios.animarClique(btnColega, () -> {
            ICommand comando = new InteragirSocialmenteCommand(this.pane);
            comando.executar();
        });
    }

    // Interagir (random)
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
                        falaSorteada.getCategoria().getIcone()
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
