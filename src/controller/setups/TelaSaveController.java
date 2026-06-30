package controller.setups;

import application.AudioManager;
import application.RotasFixas;
import application.SceneManager;
import application.Utilitarios;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import model.Game;
import model.academico.Semestre;
import repository.IRepository;
import repository.Repository;
import service.GameService;


public class TelaSaveController implements Initializable {

    @FXML private VBox vboxListaSaves;
    @FXML private ImageView btnVoltar;

    IRepository saves = new Repository("dados/saves.json", new TypeToken<ArrayList<Game>>(){}.getType());
    IRepository semestres = new Repository("dados/semestres.json", new TypeToken<ArrayList<Semestre>>(){}.getType());

    GameService gameService = new GameService(saves, semestres);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<Game> jogosSalvos = gameService.listarJogos();
        carregarJogosSalvos(jogosSalvos);

        AudioManager.getInstancia().tocarMusicaDeFundo("/resources/locais/audio/musica-geral-game.mp3");
    }

    public void carregarJogosSalvos(List<Game> jogosSalvos) {

        vboxListaSaves.getChildren().clear();

        if (jogosSalvos == null || jogosSalvos.isEmpty()) {
            return;
        }

        try {

            for (Game jogoSalvo : jogosSalvos) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/iniciais/cardSave.fxml"));
                AnchorPane cardVisual = loader.load();

                CardSaveController controllerDoCard = loader.getController();
                controllerDoCard.templateSave(jogoSalvo, this);

                vboxListaSaves.getChildren().add(cardVisual);
            }

        } catch (Exception e) {

            e.printStackTrace();

            Platform.runLater(() -> {
                Alert avisoDeErro = new Alert(Alert.AlertType.ERROR);
                avisoDeErro.setTitle("Erro no Jogo");
                avisoDeErro.setHeaderText("Ops! Ocorreu um problema.");
                avisoDeErro.setContentText("Não foi possível montar os cards de saves.");
                avisoDeErro.showAndWait();
            });
        }
    }

    public void recarregarSaves() {

        List<Game> listaAtualizada = gameService.listarJogos();
        carregarJogosSalvos(listaAtualizada);
    }

    public void botaoVoltarMenuInicial() {
        Utilitarios.animarClique(btnVoltar, () ->
                SceneManager.navegar(RotasFixas.MENUINICIAL.getRotaFixa())
        );
    }
}