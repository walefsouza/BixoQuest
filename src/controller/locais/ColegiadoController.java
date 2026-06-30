package controller.locais;

import application.*;
import com.google.gson.reflect.TypeToken;
import controller.command.ICommand;
import controller.command.InteragirSocialmenteCommand;
import controller.command.PosicionarNPCsCommand;
import controller.command.RevisarMateriaCommand;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.entidades.Jogador;
import model.atividades.Evento;
import model.atividades.ResultadoAcao;
import model.interacao.Dialogo;
import model.mapa.Colegiado;
import model.mapa.TipoLocal;
import repository.IRepository;
import repository.LocalRepository;
import repository.Repository;
import service.InteracaoService;
import service.LocalService;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class ColegiadoController implements Initializable {

    @FXML private ImageView btnMapa;
    @FXML private Node btnBurocracia;
    @FXML private AnchorPane pane;
    @FXML private ImageView btnInteragir;
    @FXML private ImageView btnRevisao;

    private InteracaoService interacaoService;
    private LocalService localService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        IRepository eventosRepo = new Repository("dados/eventos-bixoquest.json", new TypeToken<ArrayList<Evento>>(){}.getType());
        IRepository dialogoRepo = new Repository("dados/dialogos.json", new TypeToken<ArrayList<Dialogo>>(){}.getType());
        IRepository locaisRepo = new LocalRepository();

        this.localService = new LocalService(eventosRepo, dialogoRepo, locaisRepo);
        this.interacaoService = new InteracaoService();

        double[][] pontosDeSpawn = {
                {250, 270},
                {670, 200},
                {850, 260},
        };

        PosicionarNPCsCommand comando = new PosicionarNPCsCommand(
                this.pane,
                TipoLocal.COLEGIADO,
                pontosDeSpawn,
                interacaoService
        );

        comando.executar();

        AudioManager.getInstancia().tocarMusicaDeFundo("/resources/locais/audio/musica-tema-colegiado.mp3");
    }


    @FXML
    public void botaoMapa() {
        Utilitarios.animarClique(btnMapa, () ->
                SceneManager.navegar(RotasFixas.MAPACENTRAL.getRotaFixa())
        );
    }

    @FXML
    public void botaoRevisao() {
        Utilitarios.animarClique(btnRevisao, () -> {
            ICommand comando = new RevisarMateriaCommand(this.pane);
            comando.executar();
        });
    }

    @FXML
    public void clicarResolverBurocracia() {

        Utilitarios.animarClique(btnBurocracia, () -> {

            Colegiado colegiado = (Colegiado) localService.buscarLocal("COLEGIADO");
            Jogador jogador = SessaoSingleton.getInstancia().getGame().getJogador();

            ResultadoAcao resultado = localService.resolverBurocracia(jogador, colegiado);

            if (resultado != null) {

                if (resultado.getSucesso()) {
                    SceneManager.mostrarCardNotificacao(
                            this.pane,
                            "Maeli ajudou Você",
                            resultado.getTextoNarrativo(),
                            "/resources/personagens/secretaria-maeli.png"
                    );
                }

                else {
                    SceneManager.mostrarCardNotificacao(
                            this.pane,
                            "Maeli: system off baby",
                            resultado.getTextoNarrativo(),
                            "/resources/personagens/secretaria-maeli.png"
                    );
                }
            }
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