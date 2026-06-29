package controller.locais;

import application.RotasFixas;
import application.SceneManager;
import application.SessaoSingleton;
import application.Utilitarios;
import controller.command.PosicionarNPCsCommand;
import controller.command.VerAulaCommand;
import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.Game;
import model.academico.Disciplina;
import model.atividades.EventoAvaliacao;
import model.interacao.Dialogo;
import model.mapa.TipoLocal;
import service.InteracaoService;

import java.util.Collections;
import java.util.List;

public class SalaDeAulaController {

    @FXML private ImageView btnMapa;
    @FXML private ImageView btnQuadro;
    @FXML private AnchorPane pane;
    @FXML private ImageView btnInteragir;

    private InteracaoService interacaoService;

    @FXML
    public void initialize() {

        Game game = SessaoSingleton.getInstancia().getGame();
        Disciplina disciplina = game.getSemestre().getDisciplinas().get(0);
        EventoAvaliacao avaliacao = disciplina.getAvaliacao();

        if (game.getSemestre().getSemanaAtual() == 4 && !avaliacao.getRealizada()) {
            DropShadow alertaVermelho = new DropShadow();
            alertaVermelho.setColor(Color.RED);
            alertaVermelho.setRadius(25.0);
            alertaVermelho.setSpread(0.3);

            btnQuadro.setEffect(alertaVermelho);
        }

        else {
            btnQuadro.setEffect(null);
        }

        this.interacaoService = new InteracaoService();

        double[][] pontosDeSpawn = {
                {200, 270},
                {440, 190},
                {670, 200},
                {950, 260},

        };

        PosicionarNPCsCommand comando = new PosicionarNPCsCommand(
                this.pane,
                TipoLocal.SALA_DE_AULA,
                pontosDeSpawn,
                interacaoService
        );

        comando.executar();
    }

    public void botaoMapa() {
        Utilitarios.animarClique(btnMapa, () ->
                SceneManager.navegar(RotasFixas.MAPACENTRAL.getRotaFixa())
        );
    }

    @FXML
    public void assistirAula() {

        Game game = SessaoSingleton.getInstancia().getGame();
        Disciplina disciplina = game.getSemestre().getDisciplinas().get(0);
        EventoAvaliacao avaliacao = disciplina.getAvaliacao();

        if (game.getSemestre().getSemanaAtual() == 4 && !avaliacao.getRealizada()) {

            SceneManager.mostrarDialogoWarn(
                    pane,
                    "Semana de Avaliação",
                    "Prepare-se! A sua avaliação chegará em breve.",
                    "/resources/icones/interface-icon-avaliacao.png"
            );

            pane.setOnMouseClicked(e -> {
                pane.setOnMouseClicked(null);
                SceneManager.navegar(RotasFixas.AVALIACAO.getRotaFixa());
            });
        }

        else {
            new VerAulaCommand(pane).executar();
        }
    }

    @FXML
    public void botaoInteragir() {
        Utilitarios.animarClique(btnInteragir, () -> {
            List<Dialogo> falas = interacaoService.buscarFalasDoLocal(TipoLocal.BOROGODO);

            if (!falas.isEmpty()) {

                Collections.shuffle(falas);
                Dialogo falaSorteada = falas.get(0);

                SceneManager.mostrarDialogoWarn(
                        this.pane,
                        "Alguém diz...",
                        falaSorteada.getTexto(),
                        "/resources/icones/interface-icon-colegas.png"
                );
            }

            else {

                SceneManager.mostrarDialogoWarn(
                        this.pane,
                        "Silêncio",
                        "Parece que não há muito sobre o que conversar aqui agora.",
                        "/resources/icones/interface-icon-erro.png"
                );
            }
        });
    }
}
