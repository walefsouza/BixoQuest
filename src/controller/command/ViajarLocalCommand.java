package controller.command;

import application.RotasFixas;
import application.SceneManager;
import javafx.scene.layout.AnchorPane;
import model.Game;
import model.atividades.ResultadoAcao;
import model.mapa.Local;
import service.AtividadeService;
import service.LocalService;

import java.util.ArrayList;

public class ViajarLocalCommand implements ICommand {

    // Atributos - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private String destino;
    private String localNome;

    private Game game;
    private AnchorPane pane;

    private LocalService localService;
    private AtividadeService atividadeService;

    // Construtor - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public ViajarLocalCommand(String destino, String localNome, Game game, LocalService localService, AtividadeService atividadeService, AnchorPane node) {
        this.destino = destino;
        this.localNome = localNome;
        this.game = game;
        this.localService = localService;
        this.atividadeService = atividadeService;
        this.pane = node;
    }

    // Implementação - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    public void executar() {

        // Verificando se o destino é válido
        if (destino == null || destino.isEmpty()) {
            SceneManager.navegar(RotasFixas.MAPACENTRAL.getRotaFixa());
            return;
        }

        // Acessando objeto de viajem no repositório e viajando via service
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

        // Exibe card informativo
        boolean mensagem = resultado.getTextoNarrativo() != null && !resultado.getTextoNarrativo().trim().isEmpty();

        // Exibindo mensagem e viajando
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

        // Se não tem mensagem, apenas viaja
        else {
            SceneManager.navegar(destino);
        }
    }
}
