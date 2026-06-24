package controller.locais;

import application.RotasFixas;
import application.SceneManager;
import application.Utilitarios;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class BorogodoController {

    @FXML private ImageView btnMapa;

    public void botaoMapa() {
        Utilitarios.animarClique(btnMapa, () ->
                SceneManager.navegar(RotasFixas.MAPACENTRAL.getRotaFixa())
        );
    }
}
