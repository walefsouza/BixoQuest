package controller.command;

import application.RotasFixas;
import application.SceneManager;
import com.google.gson.reflect.TypeToken;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import model.Game;
import model.academico.Semestre;
import model.atividades.Evento;
import model.atividades.ResultadoAcao;
import model.atividades.Task;
import model.interacao.Dialogo;
import model.mapa.Local;
import repository.IRepository;
import repository.LocalRepository;
import repository.Repository;
import service.AtividadeService;
import service.LocalService;

import java.util.ArrayList;

public class ViajarLocalCommand implements ICommand {

    private String destino;
    private String localNome;

    private Game game;
    private AnchorPane pane;

    private LocalService localService;
    private AtividadeService atividadeService;

    public ViajarLocalCommand(String destino, String localNome, Game game, LocalService localService, AtividadeService atividadeService, AnchorPane node) {
        this.destino = destino;
        this.localNome = localNome;
        this.game = game;
        this.localService = localService;
        this.atividadeService = atividadeService;
        this.pane = node;
    }

    @Override
    public void executar() {

        if (destino == null || destino.isEmpty()) {
            SceneManager.navegar(RotasFixas.MAPACENTRAL.getRotaFixa());
            return;
        }

        Local localDestino = localService.buscarLocal(this.localNome);
        ResultadoAcao resultado = localService.viajar(this.game, localDestino.getTipo(), this.atividadeService);

        // Se ele não conseguir viajar, é porque está cansado
        if (!resultado.getSucesso()) {
            SceneManager.mostrarCardNotificacao(
                    this.pane,
                    "Cansaço Extremo",
                    resultado.getTextoNarrativo(),
                    "/resources/icones/interface-icon-bronca.png"
            );
            return;
        }

        // ainda preciso pensar no sistema de som

        // Exibe card informativo
        boolean mensagem = resultado.getTextoNarrativo() != null && !resultado.getTextoNarrativo().trim().isEmpty();

        if (mensagem) {

            SceneManager.mostrarCardNotificacao(
                    this.pane,
                    "Atenção!",
                    resultado.getTextoNarrativo(),
                    "/resources/icones/icon-evento-pergaminho.png"
            );

            this.pane.setOnMouseClicked(e -> {
                this.pane.setOnMouseClicked(null);
                SceneManager.navegar(destino);
            });

        }

        else {

            SceneManager.navegar(destino);
            // pensar na lógica de audio tbm
            // pensar em escurecer tela/piscar/algo visual
        }
    }
}
