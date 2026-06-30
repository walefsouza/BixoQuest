package controller.overlays;

import application.*;
import com.google.gson.reflect.TypeToken;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.atividades.Evento;
import model.atividades.ResultadoAcao;
import model.entidades.Jogador;
import model.interacao.Dialogo;
import model.mapa.Local;
import repository.IRepository;
import repository.LocalRepository;
import repository.Repository;
import service.LocalService;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ApostasController implements Initializable {

    @FXML private AnchorPane paneRoleta;
    @FXML private AnchorPane pane;
    @FXML private ImageView imgRoleta;
    @FXML private ImageView apostaSelecionada = null;
    @FXML private ImageView btnAposta5, btnAposta10, btnAposta15, btnSair;

    private double valorAposta = 0;
    private LocalService localService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        IRepository eventosRepo = new Repository("dados/eventos-bixoquest.json", new TypeToken<ArrayList<Evento>>(){}.getType());
        IRepository dialogoRepo = new Repository("dados/dialogos.json", new TypeToken<ArrayList<Dialogo>>(){}.getType());
        IRepository locaisRepo = new LocalRepository();

        this.localService = new LocalService(eventosRepo, dialogoRepo, locaisRepo);

        Utilitarios.configurarClique(btnAposta5);
        Utilitarios.configurarClique(btnAposta10);
        Utilitarios.configurarClique(btnAposta15);
        Utilitarios.configurarClique(paneRoleta);
        Utilitarios.configurarClique(btnSair);

        aplicarSombraRoleta(imgRoleta);
        aplicarSombraRoleta(btnAposta5);
        aplicarSombraRoleta(btnAposta10);
        aplicarSombraRoleta(btnAposta15);
        aplicarSombraRoleta(btnSair);

        AudioManager.getInstancia().tocarMusicaDeFundo("/resources/locais/audio/musica-tema-cassino.mp3");
    }

    @FXML
    public void clicarAposta5() {
        Utilitarios.animarClique(btnAposta5, () -> {
            this.valorAposta = 5;
            atualizarDestaque(btnAposta5);
        });
    }

    @FXML
    public void clicarAposta10() {
        Utilitarios.animarClique(btnAposta10, () -> {
            this.valorAposta = 10;
            atualizarDestaque(btnAposta10);
        });
    }

    @FXML
    public void clicarAposta15() {
        Utilitarios.animarClique(btnAposta15, () -> {
            this.valorAposta = 15;
            atualizarDestaque(btnAposta15);
        });
    }

    @FXML
    public void clicarSair() {
        Utilitarios.animarClique(btnSair, () -> {
            this.valorAposta = 0;
            SceneManager.navegar(RotasFixas.BOROGODO.getRotaFixa());
        });
    }


    @FXML
    public void clicarRoleta() {
        Utilitarios.animarClique(paneRoleta, () -> {

            if (valorAposta == 0) {

                SceneManager.mostrarDialogoWarn(
                        this.pane,
                        "LADRÃO! TRAPACEIRO",
                        "Você deve selecionar algum valor para apostar. Não existe almoço grátis.",
                        "/resources/icones/colegiado-icon-problema.png"
                );

                return;
            }

            Local local = localService.buscarLocal("BOROGODO");
            Jogador jogador = SessaoSingleton.getInstancia().getGame().getJogador();
            ResultadoAcao resultado = localService.apostarNoBorogodo(jogador, local, valorAposta);

            if (resultado != null) {
                animacaoRoleta(resultado);

                AudioManager.getInstancia().tocarEfeito("/resources/atividades/som-roleta-cassino.mp3");
            }

        });
    }


    private void atualizarDestaque(ImageView clicado) {

        ImageView[] botoesAposta = {btnAposta5, btnAposta10, btnAposta15};

        if (clicado == apostaSelecionada) {

            for (ImageView btn : botoesAposta) {
                btn.setEffect(null);
                btn.setOpacity(1.0);
            }

            this.valorAposta = 0;
            apostaSelecionada = null;
            return;
        }

        DropShadow borda = new DropShadow();
        borda.setColor(Color.web("#FFD700"));
        borda.setRadius(20);
        borda.setSpread(0.6);

        for (ImageView btn : botoesAposta) {
            btn.setEffect(null);
            btn.setOpacity(0.5);
        }

        clicado.setEffect(borda);
        clicado.setOpacity(1.0);
        apostaSelecionada = clicado;
    }

    private void animacaoRoleta(ResultadoAcao resultado) {

        paneRoleta.setDisable(true);

        double anguloAlvo = resultado.getSucesso()? 90.0 : 270.0;
        double variacao = new java.util.Random().nextInt(20) - 10;
        double anguloFinal = (360 * 5) + anguloAlvo + variacao;

        RotateTransition animacao = new RotateTransition(Duration.seconds(3), imgRoleta);
        animacao.setByAngle(anguloFinal);
        animacao.setInterpolator(Interpolator.EASE_OUT);

        animacao.setOnFinished(evento -> {

            SceneManager.mostrarDialogoWarn(
                    this.pane,
                    resultado.getTitulo(),
                    resultado.getTextoNarrativo(),
                    "/resources/icones/borogodo-icon-aposta.png"
            );

            paneRoleta.setDisable(false);
        });

        animacao.play();
    }

    private void aplicarSombraRoleta(ImageView imagem){

        //ColorAdjust ajusteCor = new ColorAdjust(0.0, -0.12, -0.05, 0.0);

        DropShadow sombra = new DropShadow();
        sombra.setRadius(20);
        sombra.setSpread(0.1);
        sombra.setOffsetX(-4);
        sombra.setOffsetY(8);
        sombra.setColor(Color.rgb(26, 15, 8, 0.55));

        //ajusteCor.setInput(sombra);

        imagem.setEffect(sombra);
    }
}