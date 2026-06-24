package controller.setups;

import application.RotasFixas;
import application.SceneManager;
import application.Utilitarios;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class MenuInicialController {

    @FXML private ImageView btnNovoJogo;
    @FXML private ImageView btnJogosSalvos;
    @FXML private ImageView btnOpcoes;
    @FXML private ImageView btnCreditos;


    @FXML
    public void botaoNovoJogo(){
        Utilitarios.animarClique(btnNovoJogo, () ->
                SceneManager.navegar(RotasFixas.NOVOJOGO.getRotaFixa())
        );
    }

    @FXML
    public void botaoJogosSalvos(){
        Utilitarios.animarClique(btnJogosSalvos, () ->
                SceneManager.navegar(RotasFixas.JOGOSSALVOS.getRotaFixa())
        );
    }

    @FXML
    public void botaoOpcoes(){

    }

    @FXML
    public void botaoCreditos(){

    }
}
