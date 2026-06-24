package controller.command;

import application.RotasFixas;
import application.SceneManager;
import com.google.gson.reflect.TypeToken;
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

    private LocalService localService;
    private AtividadeService atividadeService;

    public ViajarLocalCommand(String destino, String localNome, Game game, LocalService localService, AtividadeService atividadeService) {
        this.destino = destino;
        this.localNome = localNome;
        this.game = game;
        this.localService = localService;
        this.atividadeService = atividadeService;
    }

    @Override
    public void executar() {

        if (destino.isEmpty()) {
            SceneManager.navegar(RotasFixas.MAPACENTRAL.getRotaFixa());
            return;
        }

        Local localDestino = localService.buscarLocal(this.localNome);

        ResultadoAcao resultado = localService.viajar(this.game, localDestino.getTipo(), this.atividadeService);

        if (resultado.getSucesso()) {
            SceneManager.navegar(destino);
        }
    }
}
