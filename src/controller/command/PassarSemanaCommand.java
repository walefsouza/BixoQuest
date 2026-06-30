package controller.command;

import application.*;
import com.google.gson.reflect.TypeToken;
import javafx.scene.layout.AnchorPane;
import model.Game;
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

    // Dados em memória RAM - - - - - - - - - - - - - - - - - - - - - - - -
    private static AtividadeService atividadeService = null;
    private static AcademicoService academicoService = null;
    private static TurnoService turnoService = null;
    private static GameService gameService = null;
    private static LocalService localService = null;
    private static IRepository locaisRepo = null;

    // Construtor - - - - - - - - - - - - - - - - - - - - - - - -
    public PassarSemanaCommand(AnchorPane telaDoPonto) {
        this.telaDoPonto = telaDoPonto;
    }

    @Override
    public void executar() {

        Game game = SessaoSingleton.getInstancia().getGame();

        if (atividadeService == null) {

            // Declaração dos Repositórios - - - - - - - - - - - - - - - - - - - - - - - -
            IRepository tasksRepo = new Repository("dados/bancotasks.json", new TypeToken<ArrayList<Task>>(){}.getType());
            IRepository eventosRepo = new Repository("dados/eventos-bixoquest.json", new TypeToken<ArrayList<Evento>>(){}.getType());
            IRepository semestreRepo = new Repository("dados/semestres.json", new TypeToken<ArrayList<Semestre>>(){}.getType());
            IRepository savesRepo = new Repository("dados/saves.json", new TypeToken<ArrayList<Game>>(){}.getType());
            IRepository dialogoRepo = new Repository("dados/dialogos.json", new TypeToken<ArrayList<Dialogo>>(){}.getType());
            locaisRepo = new LocalRepository();

            // Instâncias dos Services - - - - - - - - - - - - - - - - - - - - - - - -
            atividadeService = new AtividadeService(tasksRepo, eventosRepo);
            academicoService = new AcademicoService(semestreRepo, eventosRepo);
            turnoService = new TurnoService(academicoService);
            gameService = new GameService(savesRepo, semestreRepo);
            localService = new LocalService(eventosRepo, dialogoRepo, locaisRepo);
        }

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
        boolean desistiuDaSemana = false;
        game.setFlagSemana(false);

        // Embacar no Onibus - - - - - - - - - - - - - - - - - - - - - - - -
        ResultadoAcao resultadoEmbarque = localService.tentarEmbarcar(game, pontoDeOnibus, desistiuDaSemana);

        if (!game.getFlagSemana()) {
            SceneManager.mostrarDialogoWarn(
                    telaDoPonto,
                    "Aviso",
                    resultadoEmbarque.getTextoNarrativo(),
                    "/resources/icones/interface-icon-alerta.png"
            );

            if (resultadoEmbarque.getTocarAudio() != null) {
                AudioManager.getInstancia().tocarEfeito(resultadoEmbarque.getTocarAudio());
            }

            return;
        }

        // Antes de virar a semana oficialmente, temos que resetar a imagem de fundo para o padrão sem eventos
        game.setImagemFundoAtual(null);

        // Se o jogador estiver na semana 4, o command avalia se ele pode avançar semestre
        if (game.getSemestre().getSemanaAtual() == 4) {

            ResultadoAcao resultadoSemestre = academicoService.avancarSemestre(game, false);

            List<Task> novasTasks = atividadeService.escolherTasksDaSemana(game);
            game.getSemestre().setBancoTasks(novasTasks);
            game.setFlagSemana(false);
            gameService.salvarProgresso(game);

            SceneManager.mostrarDialogoWarn(
                    telaDoPonto,
                    "Fim do Semestre",
                    resultadoSemestre.getTextoNarrativo(),
                    "/resources/icones/interface-icon-lore.png"
            );

            if (resultadoSemestre.getSucesso()) {
                telaDoPonto.setOnMouseClicked(e -> {
                    telaDoPonto.setOnMouseClicked(null);
                    SceneManager.navegar(RotasFixas.NOVOSEMESTRE.getRotaFixa());
                });

                if (resultadoSemestre.getTocarAudio() != null) {
                    AudioManager.getInstancia().tocarEfeito(resultadoSemestre.getTocarAudio());
                }
            }

            else {
                telaDoPonto.setOnMouseClicked(e -> {
                    telaDoPonto.setOnMouseClicked(null);
                    SceneManager.navegar(RotasFixas.PERDEUSEMESTRE.getRotaFixa());
                });
            }

            CacheManager.getInstancia().limparCache();

            return;
        }

        // Virando a semana normal - - - - - - - - - - - - - - - - - - - - - - - -
        turnoService.passarSemana(game.getSemestre(), game.getJogador());

        // Sorteia as tasks da nova semana
        List<Task> novasTasks = atividadeService.escolherTasksDaSemana(game);
        game.getSemestre().setBancoTasks(novasTasks);

        // Salva progresso e desliga a flag
        game.setFlagSemana(false);
        gameService.salvarProgresso(game);

        SceneManager.mostrarDialogoWarn(
                telaDoPonto,
                "Nova Semana!",
                resultadoEmbarque.getTextoNarrativo(),
                "/resources/icones/interface-icon-lore.png"
        );

        if (resultadoEmbarque.getTocarAudio() != null) {
            AudioManager.getInstancia().tocarEfeito(resultadoEmbarque.getTocarAudio());
        }

        telaDoPonto.setOnMouseClicked(e -> {
            telaDoPonto.setOnMouseClicked(null);
            SceneManager.navegar(RotasFixas.NOVASEMANA.getRotaFixa());
        });

        CacheManager.getInstancia().limparCache();
    }
}