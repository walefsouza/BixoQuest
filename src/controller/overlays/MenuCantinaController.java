package controller.overlays;

import application.RotasFixas;
import application.SceneManager;
import application.Utilitarios;
import com.google.gson.reflect.TypeToken;
import controller.command.LancheComprarCommand;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.atividades.Evento;
import model.interacao.Dialogo;
import model.mapa.Cardapio;
import repository.IRepository;
import repository.LocalRepository;
import repository.Repository;
import service.LocalService;

import java.util.ArrayList;

import static application.Utilitarios.configurarClique;

public class MenuCantinaController {

    @FXML private AnchorPane pane;

    @FXML private ImageView btnVoltar;
    @FXML private ImageView btnCoxinha;
    @FXML private ImageView btnBeiju;
    @FXML private ImageView btnCafe;
    @FXML private ImageView btnSuco;
    @FXML private ImageView btnPastel;

    private LocalService localService;

    @FXML
    public void initialize() {

        configurarClique(btnCoxinha);
        configurarClique(btnPastel);
        configurarClique(btnSuco);
        configurarClique(btnBeiju);
        configurarClique(btnCafe);

        IRepository eventos = new Repository("dados/eventos-bixoquest.json", new TypeToken<ArrayList<Evento>>(){}.getType());
        IRepository dialogos = new Repository("dados/dialogos.json", new TypeToken<ArrayList<Dialogo>>(){}.getType());
        IRepository locais = new LocalRepository();

        localService = new LocalService(eventos, dialogos, locais);
    }

    // Comprar Lanches - - - - - - - - - - - - - - - - - - - - - - - -

    @FXML
    public void clicarCoxinha() {
        Utilitarios.animarClique(btnCoxinha, () ->
                new LancheComprarCommand(Cardapio.COXINHA, localService, pane).executar()
        );
    }

    @FXML
    public void clicarCafe() {
        Utilitarios.animarClique(btnCafe, () ->
                new LancheComprarCommand(Cardapio.CAFE, localService, pane).executar()
        );
    }

    @FXML
    public void clicarPastel() {
        Utilitarios.animarClique(btnPastel, () ->
                new LancheComprarCommand(Cardapio.PASTEL, localService, pane).executar()
        );
    }

    @FXML
    public void clicarSuco() {
        Utilitarios.animarClique(btnSuco, () ->
                new LancheComprarCommand(Cardapio.SUCO, localService, pane).executar()
        );
    }

    @FXML
    public void clicarBeiju() {
        Utilitarios.animarClique(btnBeiju, () ->
                new LancheComprarCommand(Cardapio.BEIJU, localService, pane).executar()
        );
    }

    public void botaoVoltarMenuInicial() {
        Utilitarios.animarClique(btnVoltar, () ->
                SceneManager.navegar(RotasFixas.CANTINA.getRotaFixa())
        );
    }
}
