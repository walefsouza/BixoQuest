package controller.overlays;

import application.RotasFixas;
import application.SceneManager;
import application.SessaoSingleton;
import application.Utilitarios;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Game;
import model.academico.Semestre;
import repository.IRepository;
import repository.Repository;
import service.GameService;

import java.util.ArrayList;

public class MenuPausaController {

    @FXML private AnchorPane painelFundo;
    @FXML private ImageView btnRetornar;
    @FXML private ImageView btnMenu;
    @FXML private ImageView silenciar;

    // Declarando interfaces genéricas dos repositórios, com especificação do tipo de dado armazenado
    IRepository saves = new Repository("dados/saves.json", new TypeToken<ArrayList<Game>>(){}.getType());
    IRepository semestres = new Repository("dados/semestres.json", new TypeToken<ArrayList<Semestre>>(){}.getType());

    // Instância do GameService
    GameService gameService = new GameService(saves, semestres);

    @FXML
    public void retornarJogo() {
        Utilitarios.animarClique(btnRetornar, () -> fecharModal());
    }

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

    @FXML
    public void silenciarAudio() {
        Utilitarios.animarClique(silenciar, () -> System.out.println("Audio mutado")
        );
    }

    // Método utilitário para fechar a janelinha
    private void fecharModal() {
        Stage stage = (Stage) painelFundo.getScene().getWindow();
        stage.close();
    }
}