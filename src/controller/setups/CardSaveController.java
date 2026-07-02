package controller.setups;

import application.*;
import com.google.gson.reflect.TypeToken;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Game;
import model.academico.Semestre;
import model.entidades.Jogador;
import repository.IRepository;
import repository.Repository;
import service.GameService;
import java.util.ArrayList;

public class CardSaveController {

    // Interface - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @FXML private Label nomeJogador;
    @FXML private Label nomeMapa;
    @FXML private Label numeroSemestre;

    @FXML private ImageView iconJogador;
    @FXML private ImageView iconFormado;
    @FXML private ImageView btnDeletar;
    @FXML private AnchorPane cardPane;

    // Atributos - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private IRepository saves = new Repository("dados/saves.json", new TypeToken<ArrayList<Game>>(){}.getType());
    private IRepository semestres = new Repository("dados/semestres.json", new TypeToken<ArrayList<Semestre>>(){}.getType());
    private GameService gameService = new GameService(saves, semestres);
    private TelaSaveController telaJogosSalvos;
    private String gameName;
    private Game gameCard;

    // Inicialização - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @FXML
    public void initialize() {
        configurarClique();
    }

    // Métodos - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    // Monta card de jogo salvo com as informações do jogo salvo no repositório
    public void templateSave(Game game, TelaSaveController telaJogosSalvos) {

        this.telaJogosSalvos = telaJogosSalvos;
        this.gameName = game.getNome();
        this.gameCard = game;

        Jogador jogador = game.getJogador();
        Semestre semeste = game.getSemestre();
        int numero = semeste.getNumero();

        nomeJogador.setText(jogador.getNome().toUpperCase());
        nomeMapa.setText(game.getNome().toUpperCase());
        numeroSemestre.setText(String.valueOf(numero));

        String caminhoAvatar = jogador.getAparencia();
        Image imagemAvatar = CacheManager.getInstancia().getImagem(caminhoAvatar);
        iconJogador.setImage(imagemAvatar);

        // Se o jogador estiver formado, altera icone da medalha
        if (game.getSemestre().getNumero() >= 6) {
            Image formado = CacheManager.getInstancia().getImagem("/resources/botoes/jogos-salvos-formado.png");
            iconFormado.setImage(formado);
        }

        else {
            Image formado = CacheManager.getInstancia().getImagem("/resources/botoes/jogos-salvos-formado-cinza.png");
            iconFormado.setImage(formado);
        }

    }

    // Quando o jogador clica no card, entra no jogo selecionado e seta o game no sessão singleton
    public void entrarNovoJogo() {

        SessaoSingleton.getInstancia().setGame(this.gameCard);
        SceneManager.navegar(RotasFixas.MAPACENTRAL.getRotaFixa());
    }

    // Caso clique na lixeira, o jogo será deletado do repositório
    public void deletarJogoSalvo(MouseEvent clique) {
        clique.consume(); // para o botão funcionar
        Utilitarios.animarClique(btnDeletar, () -> {
            gameService.deletarSave(this.gameName);
            telaJogosSalvos.recarregarSaves();
        });
    }

    // Animação  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private void configurarClique() {

        cardPane.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(100), cardPane);
            st.setToX(1.02);
            st.setToY(1.02);
            st.play();
        });

        cardPane.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(100), cardPane);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });
    }
}