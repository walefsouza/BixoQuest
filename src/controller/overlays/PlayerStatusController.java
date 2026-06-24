package controller.overlays;

import application.RotasFixas;
import application.SceneManager;
import application.Utilitarios;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Game;
import model.entidades.Jogador;
import application.SessaoSingleton;

public class PlayerStatusController implements Initializable {

    @FXML private Label nome;
    @FXML private Label mapa;
    @FXML private Label semana;
    @FXML private Label semestre;
    @FXML private Label progresso;

    @FXML private ImageView aparencia;
    @FXML private ImageView btnVoltar;

    @FXML private Label energia;
    @FXML private Label conhecimento;
    @FXML private Label desempenho;
    @FXML private Label saude;
    @FXML private Label motivacao;
    @FXML private Label dinheiro;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarStatus();
    }

    private void carregarStatus() {

        Game jogoAtual = SessaoSingleton.getInstancia().getGame();

        // Se o jogo existe, vamos atualizar todos os dados do card do jogador
        if (jogoAtual != null && jogoAtual.getJogador() != null) {

            Jogador jogador = jogoAtual.getJogador();

            // Informações base
            nome.setText(jogador.getNome());
            mapa.setText(jogoAtual.getNome());
            semana.setText(String.valueOf(jogoAtual.getSemestre().getSemanaAtual()));
            semestre.setText(String.valueOf(jogoAtual.getSemestre().getNumero()));
            progresso.setText(jogoAtual.calcularProgresso() + "%");

            // Atributos do jogador
            energia.setText(String.valueOf(jogador.getEnergia()));
            conhecimento.setText(String.valueOf(jogador.getLevelConhecimento()));
            desempenho.setText(String.valueOf(jogador.getDesempenhoAcademico()));
            saude.setText(String.valueOf(jogador.getSaude()));
            motivacao.setText(String.valueOf(jogador.getMotivacao()));
            dinheiro.setText(String.valueOf(jogador.getDinheiro()));

            // Icone do jogador
            try {
                String caminhoAvatar = jogador.getAparencia();
                Image imgAparencia = new Image(getClass().getResourceAsStream(caminhoAvatar));
                aparencia.setImage(imgAparencia);
            }

            catch (Exception e) {
                System.out.println("Se não carregar, é porque não tem imagem e o banco deu problema");
            }
        }
    }

    public void botaoVoltarMenuInicial() {
        Utilitarios.animarClique(btnVoltar, () ->
                SceneManager.navegar(SessaoSingleton.getInstancia().getUltimaRota())
        );
    }

}