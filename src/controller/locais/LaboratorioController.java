package controller.locais;

import application.*;
import com.google.gson.reflect.TypeToken;
import controller.command.ICommand;
import controller.command.PosicionarNPCsCommand;
import controller.command.RevisarMateriaCommand;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.atividades.Evento;
import model.atividades.ResultadoAcao;
import model.interacao.Dialogo;
import model.mapa.Laboratorio;
import model.mapa.TipoLocal;
import repository.IRepository;
import repository.LocalRepository;
import repository.Repository;
import service.InteracaoService;
import service.LocalService;

import javax.print.DocFlavor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class LaboratorioController implements Initializable {

    @FXML private ImageView btnMapa;
    @FXML private ImageView btnComputador;
    @FXML private AnchorPane pane;
    @FXML private ImageView btnInteragir;
    @FXML private ImageView btnRevisao;

    private LocalService localService;
    private InteracaoService interacaoService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        IRepository eventosRepo = new Repository("dados/eventos-bixoquest.json", new TypeToken<ArrayList<Evento>>(){}.getType());
        IRepository dialogoRepo = new Repository("dados/dialogos.json", new TypeToken<ArrayList<Dialogo>>(){}.getType());
        IRepository locaisRepo = new LocalRepository();

        this.localService = new LocalService(eventosRepo, dialogoRepo, locaisRepo);
        this.interacaoService = new InteracaoService();

        double[][] pontosDeSpawn = {
                {440, 220},
                {630, 260},
                {800, 230},
        };

        PosicionarNPCsCommand comando = new PosicionarNPCsCommand(
                this.pane,
                TipoLocal.LABORATORIO,
                pontosDeSpawn,
                interacaoService
        );

        comando.executar();

        AudioManager.getInstancia().tocarMusicaDeFundo("/resources/locais/audio/musica-tema-LEDS.mp3");
    }

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
    public void clicarUsarComputador() {

        Utilitarios.animarClique(btnComputador, () -> {

            int chanceErro = new java.util.Random().nextInt(100);

            if (chanceErro < 20) {
                SceneManager.navegar(RotasFixas.PCQUEBRADO.getRotaFixa());
                return;
            }

            Laboratorio lab = (Laboratorio) localService.buscarLocal("LABORATORIO");
            ResultadoAcao resultado = localService.usarComputadorLab(SessaoSingleton.getInstancia().getGame(), lab);

            if (resultado != null) {

                if (resultado.getSucesso()) {
                    SceneManager.mostrarCardNotificacao(
                            this.pane,
                            "Estudos no Lab",
                            resultado.getTextoNarrativo(),
                            "/resources/icones/interface-icon-sucesso.png"
                    );
                }

                else {
                    SceneManager.mostrarCardNotificacao(
                            this.pane,
                            "Atenção!",
                            resultado.getTextoNarrativo(),
                            "/resources/icones/interface-icon-erro.png"
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
