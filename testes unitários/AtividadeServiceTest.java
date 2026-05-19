import model.Game;
import model.academico.Semestre;
import model.atividades.*;
import model.entidades.Jogador;
import model.mapa.TipoLocal;
import model.mapa.UniversidadeMapa;
import repository.IRepository;
import service.AtividadeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AtividadeServiceTest {

    private IRepository<Task> taskRepoFake;
    private IRepository<Evento> eventoRepoFake;
    private AtividadeService service;
    private Jogador jogador;
    private Task taskComCusto;
    private Task taskSemCusto;
    private Game jogo;

    @BeforeEach
    public void setUp() {
        jogador = new Jogador("Padeiro", 50, 50,
                50, 50, 50,
                100.0, null, "img");

        taskComCusto = new Task(
                "Exercícios", "Estudar", TipoLocal.QUALQUER_LUGAR,
                false, CategoriaTask.ACADEMICO, "icone", new ArrayList<>()
        );

        taskSemCusto = new Task(
                "Descansar", "Durma bem", TipoLocal.QUALQUER_LUGAR,
                false, CategoriaTask.BEM_ESTAR, "icone", new ArrayList<>()
        );

        taskRepoFake = mock(IRepository.class);
        eventoRepoFake = mock(IRepository.class);

        service = new AtividadeService(taskRepoFake, eventoRepoFake);

        jogo = new Game("Partida", jogador, new Semestre(1), new UniversidadeMapa("UEFS", new ArrayList<>(), "img", "audio"));
    }

    // Tasks  - - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void escolherTasksDaSemanaEmRepoVazio() {
        when(taskRepoFake.listar()).thenReturn(new ArrayList<>());

        List<Task> resultado = service.escolherTasksDaSemana(jogo);
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void escolherTasksDaSemanaComTasksDiferentes() {
        List<Task> listaFake = Arrays.asList(taskComCusto, taskSemCusto);
        when(taskRepoFake.listar()).thenReturn(listaFake);

        List<Task> resultado = service.escolherTasksDaSemana(jogo);
        assertEquals(2, resultado.size());
    }

    @Test
    public void escolherTasksDaSemanaRetornaMaximoDeCincoCategorias() {
        List<Task> listaFake = new ArrayList<>();

        listaFake.add(new Task("T1", "A", null, false, CategoriaTask.ACADEMICO, "ic", null));
        listaFake.add(new Task("T2", "B", null, false, CategoriaTask.ACADEMICO, "ic", null));
        listaFake.add(new Task("T3", "C", null, false, CategoriaTask.BEM_ESTAR, "ic", null));
        listaFake.add(new Task("T4", "D", null, false, CategoriaTask.SOCIAL, "ic", null));
        listaFake.add(new Task("T5", "E", null, false, CategoriaTask.FINANCEIRO, "ic", null));
        listaFake.add(new Task("T6", "F", null, false, CategoriaTask.EXTRA, "ic", null));

        when(taskRepoFake.listar()).thenReturn(listaFake);

        List<Task> resultado = service.escolherTasksDaSemana(jogo);

        assertEquals(5, resultado.size());
    }

    // Executar  - - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void executarTaskComEnergiaSuficiente() {
        ResultadoAcao resultado = service.executarTask(taskComCusto, jogo);

        assertNotNull(resultado);

        // O custo da Categoria Acadêmico é 15. Jogador tinha 50.
        assertEquals(35, jogador.getEnergia());
        assertTrue(jogo.getTaskRealizada("Exercícios")); // Verifica se salvou no histórico
    }

    @Test
    public void executarTaskSemEnergiaSuficiente() {
        jogador.decrementarEnergia(45); // Reduz energia de 50 para 5

        ResultadoAcao resultado = service.executarTask(taskComCusto, jogo);

        assertNotNull(resultado);
        assertEquals(5, jogador.getEnergia()); // Não gastou energia
        assertFalse(jogo.getTaskRealizada("Exercícios")); // Não foi para o histórico
    }

    // Processar Eventos - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void processarEventosObrigatoriosSemEventos() {
        when(eventoRepoFake.listar()).thenReturn(new ArrayList<>());

        ResultadoAcao resultado = service.processarEventosObrigatorios(jogo);
        assertNull(resultado); // Null pois não há nenhum evento no mock
    }

    @Test
    public void processarEventosObrigatoriosDisparaEventoCorreto() {

        // Criando um evento obrigatório
        Evento eventoObrigatorio = new Evento(
                "Boas Vindas", "Você chegou na UEFS", TipoLocal.QUALQUER_LUGAR,
                CategoriaEvento.OBRIGATORIO, RequisitoEvento.NENHUM, "icone", new ArrayList<>()
        );

        when(eventoRepoFake.listar()).thenReturn(Arrays.asList(eventoObrigatorio));

        ResultadoAcao resultado = service.processarEventosObrigatorios(jogo);

        assertNotNull(resultado); // O evento deve ser processado e retornar o DTO
        assertTrue(jogo.getEventoRealizado("Boas Vindas")); // Marcado como realizado
    }

    @Test
    public void processarEventosAleatoriosSemEventosDisponiveis() {

        // Simulando que não há eventos aleatórios mapeados para a Cantina
        when(eventoRepoFake.listar()).thenReturn(new ArrayList<>());

        ResultadoAcao resultado = service.processarEventosAleatorios(jogo, TipoLocal.CANTINA);

        assertNull(resultado); // Retorna null
    }
}