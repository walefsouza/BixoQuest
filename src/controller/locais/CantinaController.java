package controller.locais;

import application.RotasFixas;
import application.SceneManager;
import application.Utilitarios;
import controller.command.LancheComprarCommand;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.mapa.Cardapio;

public class CantinaController {

    @FXML private ImageView btnMapa;
    @FXML private ImageView btnComprar;

    // Barra Lateral - - - - - - - - - - - - - - - - - - - - - - - -

    public void botaoMapa() {
        Utilitarios.animarClique(btnMapa, () ->
                SceneManager.navegar(RotasFixas.MAPACENTRAL.getRotaFixa())
        );
    }

    public void botaoComprar() {
        Utilitarios.animarClique(btnComprar, () ->
                SceneManager.navegar(RotasFixas.MENUCANTINA.getRotaFixa())
        );
    }


}
