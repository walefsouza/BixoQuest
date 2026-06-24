package controller.setups;

import application.RotasFixas;
import application.SceneManager;
import application.Utilitarios;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import static application.Utilitarios.configurarClique;

public class TelaJogarController {

    @FXML private ImageView btnJogar;
    @FXML private ImageView bixoquest;


    @FXML
    public void initialize() {
        configurarClique(bixoquest);
        configurarClique(btnJogar);
    }
    @FXML
    public void botaoJogarClicado() {
        Utilitarios.animarClique(btnJogar, () ->
                SceneManager.navegar(RotasFixas.MENUINICIAL.getRotaFixa())
        );
    }
}
