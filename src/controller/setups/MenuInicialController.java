package controller.setups;

import application.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

import static application.Utilitarios.configurarClique;

public class MenuInicialController implements Initializable {

    @FXML private ImageView btnNovoJogo;
    @FXML private ImageView btnJogosSalvos;
    @FXML private ImageView btnOpcoes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        configurarClique(btnNovoJogo);
        configurarClique(btnJogosSalvos);
        configurarClique(btnOpcoes);

        AudioManager.getInstancia().tocarMusicaDeFundo("/resources/locais/audio/musica-geral-game.mp3");

        if(AudioManager.getInstancia().isMutado()) {
            btnOpcoes.setImage(CacheManager.getInstancia().getImagem("/resources/botoes/inicial-opcoes-mutar.png"));
        }

        else {
            btnOpcoes.setImage(CacheManager.getInstancia().getImagem("/resources/botoes/inicial-opcoes.png"));
        }
    }

    @FXML
    public void botaoNovoJogo() {
        Utilitarios.animarClique(btnNovoJogo, () ->
                SceneManager.navegar(RotasFixas.NOVOJOGO.getRotaFixa())
        );
    }

    @FXML
    public void botaoJogosSalvos() {
        Utilitarios.animarClique(btnJogosSalvos, () ->
                SceneManager.navegar(RotasFixas.JOGOSSALVOS.getRotaFixa())
        );
    }

    @FXML
    public void silenciarAudio() {

        Utilitarios.animarClique(btnOpcoes, () -> {
            AudioManager.getInstancia().alternarMute();

            if (AudioManager.getInstancia().isMutado()) {
                btnOpcoes.setImage(CacheManager.getInstancia().getImagem("/resources/botoes/inicial-opcoes-mutar.png"));
            }

            else {
                btnOpcoes.setImage(CacheManager.getInstancia().getImagem("/resources/botoes/inicial-opcoes.png"));
            }
        });
    }
}
