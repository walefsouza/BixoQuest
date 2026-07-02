package controller.command;

import application.AudioManager;
import application.SceneManager;
import application.SessaoSingleton;
import com.google.gson.reflect.TypeToken;
import javafx.scene.layout.AnchorPane;
import model.Game;
import model.academico.Disciplina;
import model.academico.Semestre;
import model.atividades.Evento;
import model.atividades.ResultadoAcao;
import repository.IRepository;
import repository.Repository;
import service.AcademicoService;

import java.util.ArrayList;

public class VerAulaCommand implements ICommand {

    // Atributo - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private AnchorPane telaDaSala;
    IRepository eventosRepo = new Repository("dados/eventos-bixoquest.json", new TypeToken<ArrayList<Evento>>(){}.getType());
    IRepository semestreRepo = new Repository("dados/semestres.json", new TypeToken<ArrayList<Semestre>>(){}.getType());

    // Construtor - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public VerAulaCommand(AnchorPane telaDaSala) {
        this.telaDaSala = telaDaSala;
    }

    // Implementação - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    public void executar() {

        Game game = SessaoSingleton.getInstancia().getGame();

        if (game == null){
            return;
        }

        // Como só temos uma disciplina na lista, pegamos na posição 0
        Disciplina disciplina = game.getSemestre().getDisciplinas().get(0);

        // O limite de participações na aula é 2 por semana
        if (disciplina.getAulasAssistidas() >= 2) {

            SceneManager.mostrarDialogoWarn(
                    telaDaSala,
                    disciplina.getNome(),
                    "Você já assistiu a todas as aulas desta semana! Vá fazer suas tarefas ou descansar.",
                    disciplina.getIcone()
            );

            return;
        }

        AcademicoService academicoService = new AcademicoService(semestreRepo, eventosRepo);
        ResultadoAcao resultado = academicoService.assistirAula(game, disciplina);

        // Se ele puder assistir, monta texto avisando que assistiu
        if (resultado.getSucesso()) {

            disciplina.setAulasAssistidas(disciplina.getAulasAssistidas() + 1);

            SceneManager.mostrarDialogoWarn(
                    telaDaSala,
                    disciplina.getProfessor().getNome(),
                    resultado.getTextoNarrativo(),
                    disciplina.getProfessor().getAparencia()
            );

            if (resultado.getTocarAudio() != null) {
                AudioManager.getInstancia().tocarEfeito(resultado.getTocarAudio());
            }
        }

        // Se não, também retorna texto do DTO. Normalmente não assiste quando tá sem energia
        else {
            SceneManager.mostrarDialogoWarn(
                    telaDaSala,
                    "Sistema",
                    resultado.getTextoNarrativo(),
                    disciplina.getProfessor().getAparencia()
            );

            if (resultado.getTocarAudio() != null) {
                AudioManager.getInstancia().tocarEfeito(resultado.getTocarAudio());
            }
        }
    }
}