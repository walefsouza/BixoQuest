package controller.setups;

import application.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

import static application.Utilitarios.configurarClique;

public class MenuInicialController implements Initializable {

    // Interface - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @FXML private ImageView btnNovoJogo;
    @FXML private ImageView btnJogosSalvos;
    @FXML private ImageView btnOpcoes;

    // Inicialização - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Animação de zoom
        configurarClique(btnNovoJogo);
        configurarClique(btnJogosSalvos);
        configurarClique(btnOpcoes);

        AudioManager.getInstancia().tocarMusicaDeFundo("/resources/locais/audio/musica-geral-game.mp3");

        // Verifica se o jogo já foi o não mutado
        if(AudioManager.getInstancia().getMutado()) {
            btnOpcoes.setImage(CacheManager.getInstancia().getImagem("/resources/botoes/inicial-opcoes-mutar.png"));
        }

        else {
            btnOpcoes.setImage(CacheManager.getInstancia().getImagem("/resources/botoes/inicial-opcoes.png"));
        }
    }

    // Métodos - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    // Direciona para aba de criar um novo jogo
    @FXML
    public void botaoNovoJogo() {
        Utilitarios.animarClique(btnNovoJogo, () ->
                SceneManager.navegar(RotasFixas.NOVOJOGO.getRotaFixa())
        );
    }

    // Acessa aba de jogos salvos
    @FXML
    public void botaoJogosSalvos() {
        Utilitarios.animarClique(btnJogosSalvos, () ->
                SceneManager.navegar(RotasFixas.JOGOSSALVOS.getRotaFixa())
        );
    }

    // Silencia todos os sons do jogo
    @FXML
    public void silenciarAudio() {

        Utilitarios.animarClique(btnOpcoes, () -> {
            AudioManager.getInstancia().alternarMute();

            if (AudioManager.getInstancia().getMutado()) {
                btnOpcoes.setImage(CacheManager.getInstancia().getImagem("/resources/botoes/inicial-opcoes-mutar.png"));
            }

            else {
                btnOpcoes.setImage(CacheManager.getInstancia().getImagem("/resources/botoes/inicial-opcoes.png"));
            }
        });
    }
}
