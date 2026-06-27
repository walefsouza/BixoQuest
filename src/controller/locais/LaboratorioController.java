package controller.locais;

import application.RotasFixas;
import application.SceneManager;
import application.SessaoSingleton;
import application.Utilitarios;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.atividades.Evento;
import model.atividades.ResultadoAcao;
import model.interacao.Dialogo;
import model.mapa.Laboratorio;
import repository.IRepository;
import repository.LocalRepository;
import repository.Repository;
import service.LocalService;

import javax.print.DocFlavor;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LaboratorioController implements Initializable {

    @FXML private ImageView btnMapa;
    @FXML private ImageView btnComputador;
    @FXML private AnchorPane pane;
    private LocalService localService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        IRepository eventosRepo = new Repository("dados/eventos-bixoquest.json", new TypeToken<ArrayList<Evento>>(){}.getType());
        IRepository dialogoRepo = new Repository("dados/dialogos.json", new TypeToken<ArrayList<Dialogo>>(){}.getType());
        IRepository locaisRepo = new LocalRepository();

        this.localService = new LocalService(eventosRepo, dialogoRepo, locaisRepo);
    }

    public void botaoMapa() {
        Utilitarios.animarClique(btnMapa, () ->
                SceneManager.navegar(RotasFixas.MAPACENTRAL.getRotaFixa())
        );
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
}
