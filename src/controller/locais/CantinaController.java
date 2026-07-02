package controller.locais;

import application.AudioManager;
import application.RotasFixas;
import application.SceneManager;
import application.Utilitarios;
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

public class CantinaController implements Initializable {

    // Interface - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @FXML private ImageView btnMapa;
    @FXML private ImageView btnComprar;
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
                {950, 260},
                {440, 190},
                {670, 200},
                {200, 270}

        };

        PosicionarNPCsCommand comando = new PosicionarNPCsCommand(
                this.pane,
                TipoLocal.CANTINA,
                pontosDeSpawn,
                interacaoService
        );

        comando.executar();

        AudioManager.getInstancia().tocarMusicaDeFundo("/resources/locais/audio/musica-tema-cantina.mp3");

    }

    // Barra Lateral - - - - - - - - - - - - - - - - - - - - - - - -

    // Acessar mapa principal
    public void botaoMapa() {
        Utilitarios.animarClique(btnMapa, () ->
                SceneManager.navegar(RotasFixas.MAPACENTRAL.getRotaFixa())
        );
    }

    // Comprar lanche na cantina
    public void botaoComprar() {
        Utilitarios.animarClique(btnComprar, () ->
                SceneManager.navegar(RotasFixas.MENUCANTINA.getRotaFixa())
        );
    }

    // Interagir com colega
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
