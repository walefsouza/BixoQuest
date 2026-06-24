package controller.command;

import application.SceneManager;
import application.SessaoSingleton;
import com.google.gson.reflect.TypeToken;
import javafx.scene.layout.AnchorPane;
import model.Game;
import model.academico.Disciplina;
import model.academico.Semestre;
import model.atividades.Evento;
import model.atividades.Task;
import model.atividades.ResultadoAcao;
import model.interacao.Dialogo;
import model.mapa.Local;
import model.mapa.PontoDeOnibus;
import model.mapa.TipoLocal;
import repository.IRepository;
import repository.LocalRepository;
import repository.Repository;
import service.AcademicoService;
import service.AtividadeService;
import service.TurnoService;
import service.LocalService;
import service.GameService;

import java.util.ArrayList;
import java.util.List;

public class PassarSemanaCommand implements ICommand {

    private AnchorPane telaDoPonto;

    public PassarSemanaCommand(AnchorPane telaDoPonto) {
        this.telaDoPonto = telaDoPonto;
    }

    @Override
    public void executar() {

        Game game = SessaoSingleton.getInstancia().getGame();
        if (game == null) return;

        // Declaração dos Repositórios - - - - - - - - - - - - - - - - - - - - - - - -

        IRepository tasksRepo = new Repository("dados/bancotasks.json", new TypeToken<ArrayList<Task>>(){}.getType());
        IRepository eventosRepo = new Repository("dados/eventos-bixoquest.json", new TypeToken<ArrayList<Evento>>(){}.getType());
        IRepository semestreRepo = new Repository("dados/semestres.json", new TypeToken<ArrayList<Semestre>>(){}.getType());
        IRepository savesRepo = new Repository("dados/saves.json", new TypeToken<ArrayList<Game>>(){}.getType());
        IRepository dialogoRepo = new Repository("dados/dialogos.json", new TypeToken<ArrayList<Dialogo>>(){}.getType());
        IRepository locaisRepo = new LocalRepository();

        // Instâncias dos Services - - - - - - - - - - - - - - - - - - - - - - - -

        AtividadeService atividadeService = new AtividadeService(tasksRepo, eventosRepo);
        AcademicoService academicoService = new AcademicoService(semestreRepo, eventosRepo);
        TurnoService turnoService = new TurnoService(academicoService);
        GameService gameService = new GameService(savesRepo, semestreRepo);
        LocalService localService = new LocalService(eventosRepo,dialogoRepo,locaisRepo);

        // Obtendo objeto local real - - - - - - - - - - - - - - - - - - - - - - - -
        // Porque o método tentar embacar necessida desse objeto para funcionar

        TipoLocal tipoLocalJogador = game.getJogador().getLocal();
        String localJogador = tipoLocalJogador.toString().replace("_", " ");
        Local pontoDeOnibus = null;

        if (tipoLocalJogador == TipoLocal.PONTO_DE_ONIBUS) {

            pontoDeOnibus = (PontoDeOnibus) locaisRepo.buscar("PONTO DE ONIBUS");
        }
        else {
            pontoDeOnibus = (Local) locaisRepo.buscar(localJogador);
        }

        // Passei muito perrengue com null aqui, vamos manter para caso o erro volte
        // mesmo com a correção do problema

        if (pontoDeOnibus == null) {
            SceneManager.mostrarDialogoWarn(
                    telaDoPonto,
                    "Erro de Leitura",
                    "O repositório não encontrou o local. Tente novamente.",
                    "/resources/icones/interface-icon-alerta.png"
            );
            return;
        }

        // Flags do Jogo - - - - - - - - - - - - - - - - - - - - - - - -
        boolean desistiuDaSemana = false; // sempre falsa para esse command
        game.setFlagSemana(false); // reseta flag por segurança

        // Embacar no Onibus - - - - - - - - - - - - - - - - - - - - - - - -
        ResultadoAcao resultadoEmbarque = localService.tentarEmbarcar(game, pontoDeOnibus, desistiuDaSemana);

        // Se a flag não mudou para true, o jogador NÃO embarcou
        if (!game.getFlagSemana()) {
            SceneManager.mostrarDialogoWarn(
                    telaDoPonto,
                    "Aviso",
                    resultadoEmbarque.getTextoNarrativo(),
                    "/resources/icones/interface-icon-alerta.png"
            );

            return; // ele não embarca e vê o aviso na tela
        }

        // Virando a semana - - - - - - - - - - - - - - - - - - - - - - - -

        turnoService.passarSemana(game.getSemestre(), game.getJogador());

        // Reseta o contador de aulas para a nova semana
        if (!game.getSemestre().getDisciplinas().isEmpty()) {
            Disciplina disciplina = game.getSemestre().getDisciplinas().get(0);
            disciplina.setAulasAssistidas(0);
        }

        // Sorteia as tasks da nova semana
        List<Task> novasTasks = atividadeService.escolherTasksDaSemana(game);
        game.getSemestre().setBancoTasks(novasTasks);

        // Salva progresso e desliga a flag
        game.setFlagSemana(false);
        gameService.salvarProgresso(game);

        // Mensagem de passagem de semana
        SceneManager.mostrarDialogoWarn(
                telaDoPonto,
                "Nova Semana!",
                resultadoEmbarque.getTextoNarrativo(),
                "/resources/icones/interface-icon-lore.png"
        );
    }
}