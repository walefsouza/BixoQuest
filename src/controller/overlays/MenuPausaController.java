package controller.overlays;

import application.*;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Game;
import model.academico.Semestre;
import repository.IRepository;
import repository.Repository;
import service.GameService;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MenuPausaController implements Initializable {

    // Interface - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @FXML private AnchorPane painelFundo;
    @FXML private ImageView btnRetornar;
    @FXML private ImageView btnMenu;
    @FXML private ImageView silenciar;

    // Declarando interfaces genéricas dos repositórios, com especificação do tipo de dado armazenado
    IRepository saves = new Repository("dados/saves.json", new TypeToken<ArrayList<Game>>(){}.getType());
    IRepository semestres = new Repository("dados/semestres.json", new TypeToken<ArrayList<Semestre>>(){}.getType());

    // Instância do GameService
    GameService gameService = new GameService(saves, semestres);

    // Inicialização - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (AudioManager.getInstancia().getMutado()) {
            silenciar.setImage(CacheManager.getInstancia().getImagem("/resources/botoes/menuPauseDesmutar.png"));
        }
        else {
            silenciar.setImage(CacheManager.getInstancia().getImagem("/resources/botoes/menuPauseSilenciar.png"));
        }
    }

    // Métodos - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    // Fecha tela modal e retorna a tela anterior
    @FXML
    public void retornarJogo() {
        Utilitarios.animarClique(btnRetornar, () -> fecharModal());
    }

    // Direciona o jogador ao menu inicial do jogo
    @FXML
    public void acessarMenuInicial() {

        Game jogoAtual = SessaoSingleton.getInstancia().getGame();

        if (jogoAtual != null) {
            gameService.salvarProgresso(jogoAtual);
        }

        Utilitarios.animarClique(btnMenu, () ->
                fecharModal()
        );

        SceneManager.navegar(RotasFixas.MENUINICIAL.getRotaFixa());
    }

    // Silencia todos os sons do jogo
    @FXML
    public void silenciarAudio() {

        Utilitarios.animarClique(silenciar, () -> {
            AudioManager.getInstancia().alternarMute();

            if (AudioManager.getInstancia().getMutado()) {
                silenciar.setImage(CacheManager.getInstancia().getImagem("/resources/botoes/menuPauseDesmutar.png"));
            }

            else {
                silenciar.setImage(CacheManager.getInstancia().getImagem("/resources/botoes/menuPauseSilenciar.png"));
            }
        });
    }

    // Método utilitário para fechar a janelinha
    private void fecharModal() {
        Stage stage = (Stage) painelFundo.getScene().getWindow();
        stage.close();
    }
}