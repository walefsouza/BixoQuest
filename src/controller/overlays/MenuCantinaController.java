package controller.overlays;

import application.RotasFixas;
import application.SceneManager;
import application.Utilitarios;
import controller.command.LancheComprarCommand;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.mapa.Cardapio;

import static application.Utilitarios.configurarClique;

public class MenuCantinaController {

    @FXML private ImageView btnVoltar;
    @FXML private ImageView btnCoxinha;
    @FXML private ImageView btnBeiju;
    @FXML private ImageView btnCafe;
    @FXML private ImageView btnSuco;
    @FXML private ImageView btnPastel;

    @FXML
    public void initialize() {
        configurarClique(btnCoxinha);
        configurarClique(btnPastel);
        configurarClique(btnSuco);
        configurarClique(btnBeiju);
        configurarClique(btnCafe);
    }

    // Comprar Lanches - - - - - - - - - - - - - - - - - - - - - - - -

    @FXML
    public void clicarCoxinha() {
        Utilitarios.animarClique(btnCoxinha, () ->
                new LancheComprarCommand(Cardapio.COXINHA).executar()
        );
    }

    @FXML
    public void clicarCafe() {
        Utilitarios.animarClique(btnCafe, () ->
                new LancheComprarCommand(Cardapio.CAFE).executar()
        );
    }

    @FXML
    public void clicarPastel() {
        Utilitarios.animarClique(btnPastel, () ->
                new LancheComprarCommand(Cardapio.PASTEL).executar()
        );
    }

    @FXML
    public void clicarSuco() {
        Utilitarios.animarClique(btnSuco, () ->
                new LancheComprarCommand(Cardapio.SUCO).executar()
        );
    }

    @FXML
    public void clicarBeiju() {
        Utilitarios.animarClique(btnBeiju, () ->
                new LancheComprarCommand(Cardapio.BEIJU).executar()
        );
    }

    public void botaoVoltarMenuInicial() {
        Utilitarios.animarClique(btnVoltar, () ->
                SceneManager.navegar(RotasFixas.CANTINA.getRotaFixa())
        );
    }
}
