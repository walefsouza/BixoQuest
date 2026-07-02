package controller.setups;

import application.AudioManager;
import application.RotasFixas;
import application.SceneManager;
import application.Utilitarios;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import static application.Utilitarios.configurarClique;

public class TelaJogarController {

    // Interface - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @FXML private ImageView btnJogar;
    @FXML private ImageView bixoquest;

    // Inicialização - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @FXML
    public void initialize() {

        configurarClique(bixoquest);
        configurarClique(btnJogar);

        AudioManager.getInstancia().tocarMusicaDeFundo("/resources/locais/audio/musica-geral-game.mp3");
    }

    // Direciona o jogador ao menu inicial do jogo
    @FXML
    public void botaoJogarClicado() {
        Utilitarios.animarClique(btnJogar, () ->
                SceneManager.navegar(RotasFixas.MENUINICIAL.getRotaFixa())
        );
    }
}
