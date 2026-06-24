package controller.command;

import application.RotasFixas;
import application.SessaoSingleton;
import application.SceneManager;
import com.google.gson.reflect.TypeToken;
import model.Game;
import model.academico.Semestre;
import model.atividades.ResultadoAcao;
import model.entidades.Jogador;
import model.atividades.Evento;
import model.atividades.Task;
import repository.IRepository;
import repository.Repository;
import service.AcademicoService;
import service.AtividadeService;
import service.TurnoService;

import java.util.ArrayList;
import java.util.List;

public class PassarSemanaCommand implements ICommand {

    @Override
    public void executar() {

        IRepository tasksRepo = new Repository("dados/bancotasks.json", new TypeToken<ArrayList<Task>>(){}.getType());
        IRepository eventosRepo = new Repository("dados/eventos-bixoquest.json", new TypeToken<ArrayList<Evento>>(){}.getType());
        IRepository semestreRepo = new Repository("dados/semestres.json", new TypeToken<ArrayList<Semestre>>(){}.getType());

        AtividadeService atividadeService = new AtividadeService(tasksRepo, eventosRepo);
        AcademicoService academicoService = new AcademicoService(semestreRepo, eventosRepo);
        TurnoService turnoService = new TurnoService(academicoService);

        Game game = SessaoSingleton.getInstancia().getGame();

        if (game == null) {
            return;
        }

        ResultadoAcao resultado = turnoService.passarSemana(game.getSemestre(), game.getJogador());

        List<Task> novasTasks = atividadeService.escolherTasksDaSemana(game);
        game.getSemestre().setBancoTasks(novasTasks);

        SceneManager.navegar(RotasFixas.MAPACENTRAL.getRotaFixa());
    }
}