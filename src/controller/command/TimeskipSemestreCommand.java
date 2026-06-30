package controller.command;

import application.RotasFixas;
import application.SceneManager;
import application.SessaoSingleton;
import com.google.gson.reflect.TypeToken;
import javafx.scene.layout.AnchorPane;
import model.Game;
import model.academico.Semestre;
import model.atividades.Evento;
import model.atividades.Task;
import model.atividades.ResultadoAcao;
import repository.IRepository;
import repository.Repository;
import service.AcademicoService;
import service.AtividadeService;
import service.GameService;

import java.util.ArrayList;
import java.util.List;

public class TimeskipSemestreCommand implements ICommand {

    private AnchorPane telaDoPonto;

    private static AtividadeService atividadeService = null;
    private static AcademicoService academicoService = null;
    private static GameService gameService = null;

    public TimeskipSemestreCommand(AnchorPane telaDoPonto) {
        this.telaDoPonto = telaDoPonto;
    }

    @Override
    public void executar() {
        Game game = SessaoSingleton.getInstancia().getGame();

        if (atividadeService == null) {
            IRepository tasksRepo = new Repository("dados/bancotasks.json", new TypeToken<ArrayList<Task>>(){}.getType());
            IRepository eventosRepo = new Repository("dados/eventos-bixoquest.json", new TypeToken<ArrayList<Evento>>(){}.getType());
            IRepository semestreRepo = new Repository("dados/semestres.json", new TypeToken<ArrayList<Semestre>>(){}.getType());
            IRepository savesRepo = new Repository("dados/saves.json", new TypeToken<ArrayList<Game>>(){}.getType());

            atividadeService = new AtividadeService(tasksRepo, eventosRepo);
            academicoService = new AcademicoService(semestreRepo, eventosRepo);
            gameService = new GameService(savesRepo, semestreRepo);
        }

        ResultadoAcao resultadoSemestre = academicoService.avancarSemestre(game, true);

        if (resultadoSemestre.getSucesso()) {
            List<Task> novasTasks = atividadeService.escolherTasksDaSemana(game);
            game.getSemestre().setBancoTasks(novasTasks);
        }

        game.setFlagSemana(false);
        game.setImagemFundoAtual(null);
        gameService.salvarProgresso(game);

        SceneManager.mostrarDialogoWarn(
                telaDoPonto,
                "Avanço Rápido (Timeskip)",
                resultadoSemestre.getTextoNarrativo(),
                "/resources/icones/interface-icon-lore.png"
        );


        if (resultadoSemestre.getSucesso()) {
            telaDoPonto.setOnMouseClicked(e -> {
                telaDoPonto.setOnMouseClicked(null);
                SceneManager.navegar(RotasFixas.NOVOSEMESTRE.getRotaFixa());
            });
        }

        else {
            telaDoPonto.setOnMouseClicked(e -> {
                telaDoPonto.setOnMouseClicked(null);
                SceneManager.navegar(RotasFixas.PERDEUSEMESTRE.getRotaFixa());
            });
        }
    }
}