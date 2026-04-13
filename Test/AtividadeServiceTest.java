import model.atividades.*;
import model.entidades.Jogador;
import model.Game;
import model.academico.Semestre;
import model.mapa.UniversidadeMapa;
import repository.IRepository;
import repository.Repository;
import service.AtividadeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

public class AtividadeServiceTest {

    private IRepository<Task> taskRepo;
    private IRepository<Evento> eventoRepo;
    private AtividadeService service;
    private Jogador jogador;
    private Task taskComCusto;
    private Task taskSemCusto;
    private Game jogo;


    @BeforeEach
    public void setUp() {
        jogador = new Jogador("Padeiro", 50, 50,
                50, 50, 50,
                100.0, null);

        taskComCusto = new Task(
                "Exercícios", "Estudar, estudar, escrever, codificar", null,
                -20, 10, 0,
                0, 10, 0.0,
                false, CategoriaTask.ACADEMICO
        );

        taskSemCusto = new Task(
                "Descansar", "Durma bem anjo, você precisa.", null,
                0, 0, 10,
                0, 0, 0.0,
                false, CategoriaTask.BEM_ESTAR
        );

        taskRepo = new Repository();
        eventoRepo = new Repository();
        service = new AtividadeService(taskRepo, eventoRepo);

        jogo = new Game("Partida", jogador, new Semestre(1), new UniversidadeMapa("UEFS", new ArrayList<>()));
    }

    // tasks da semana

    @Test
    public void escolherTasksDaSemanaEmRepoVazio() {
        List<Task> resultado = service.escolherTasksDaSemana();
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void escolherTasksDaSemanaComMenorDe6NoBanco() {
        taskRepo.salvar(taskComCusto);
        taskRepo.salvar(taskSemCusto);
        List<Task> resultado = service.escolherTasksDaSemana();
        assertEquals(2, resultado.size());
    }

    @Test
    public void escolherTasksDaSemanaComMais6NoBanco() {
        for (int i = 0; i < 10; i++) {
            taskRepo.salvar(new Task(
                    "Task " + i, "ABC", null,
                    0, 0, 0,
                    0, 0, 0.0,
                    false, CategoriaTask.EXTRA
            ));
        }
        List<Task> resultado = service.escolherTasksDaSemana();
        assertEquals(6, resultado.size());
    }

    //executar atividade

    @Test
    public void executarTaskComEnergiaSuficiente() {
        boolean resultado = service.executarTask(taskComCusto, jogador);
        assertTrue(resultado);
        assertEquals(30, jogador.getEnergia());
    }

    @Test
    public void executarTaskSemEnergiaSuficiente() {
        jogador.setEnergia(5);
        boolean resultado = service.executarTask(taskComCusto, jogador);
        assertFalse(resultado);
        assertEquals(5, jogador.getEnergia());
    }

    @Test
    public void processarEventosDoDiaSemEventosDisponiveis() {
        boolean resultado = service.processarEventosDoDia(jogo);
        assertFalse(resultado);
    }
}