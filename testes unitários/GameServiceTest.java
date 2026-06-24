import model.Game;
import model.academico.Semestre;
import model.mapa.Local;
import repository.IRepository;
import repository.LocalRepository;
import service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameServiceTest {

    private IRepository<Game> gameRepoFake;
    private IRepository<Semestre> semestreRepoFake;
    private GameService service;

    @BeforeEach
    public void setUp() {

        gameRepoFake = mock(IRepository.class);
        semestreRepoFake = mock(IRepository.class);

        Semestre semestreInicial = new Semestre(1);
        when(semestreRepoFake.buscar("1")).thenReturn(semestreInicial);

        service = new GameService(gameRepoFake, semestreRepoFake);
    }

    // Iniciar Novo Jogo  - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void iniciarNovoJogoRetornaObjetoValido() {
        List<Local> locaisMapa = new LocalRepository().listar();
        Game jogo = service.iniciarNovoJogo("Partida1", "Katniss", locaisMapa, "SemAparencia");

        assertNotNull(jogo);
        assertEquals("Partida1", jogo.getNome());
        assertEquals("Katniss", jogo.getJogador().getNome());
        assertEquals("1", jogo.getSemestre().capturarNome());
    }

    @Test
    public void iniciarNovoJogoSalvarNoRepositorio() {
        List<Local> locaisMapa = new LocalRepository().listar();
        Game jogo = service.iniciarNovoJogo("Partida1", "Katniss", locaisMapa, "SemAparência");

        // Com o verify, verificamos se o método salvar() do repositório foi chamado
        // pelo menos uma vez passando qualquer objeto do tipo Game.
        verify(gameRepoFake, times(1)).salvar(any(Game.class));
    }

    @Test
    public void iniciarNovoJogoLancaExcecaoSeSemestreNaoExistir() {

        // Simulando um erro no banco de dados onde o Semestre 1 não foi encontrado
        when(semestreRepoFake.buscar("1")).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.iniciarNovoJogo("Partida1", "Katniss", new ArrayList<>(), "SemAP");
        });

        assertEquals("Banco de semestres não encontrado!", exception.getMessage());
    }

    @Test
    public void iniciarNovoJogoJogadorComAtributosIniciais() {
        Game jogo = service.iniciarNovoJogo("Partida1", "Katniss", new ArrayList<>(), "SemAparencia");

        // Verificando os status iniciais do jogador estão sendo setados
        assertEquals(100, jogo.getJogador().getEnergia());
        assertEquals(100, jogo.getJogador().getSaude());
        assertEquals(100, jogo.getJogador().getMotivacao());
        assertEquals(50, jogo.getJogador().getLevelConhecimento());
        assertEquals(50.0, jogo.getJogador().getDinheiro());
    }

    // Listar Jogos  - - - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void listarJogosListaVazia() {
        when(gameRepoFake.listar()).thenReturn(new ArrayList<>());

        List<Game> jogos = service.listarJogos();
        assertTrue(jogos.isEmpty());
    }

    @Test
    public void listarJogosComJogosNaLista() {

        // Criando uma lista fake com dois jogos com o mockito
        List<Game> jogosFakes = Arrays.asList(mock(Game.class), mock(Game.class));
        when(gameRepoFake.listar()).thenReturn(jogosFakes);

        List<Game> jogosRetornados = service.listarJogos();
        assertEquals(2, jogosRetornados.size());
    }

    // Carregar e Salvar Jogo  - - - - - - - - - - - - - - - - - - - -

    @Test
    public void carregarJogoExistente() {
        Game jogoFake = mock(Game.class);
        when(gameRepoFake.buscar("Partida1")).thenReturn(jogoFake);

        Game resultado = service.carregarJogo("Partida1");
        assertNotNull(resultado);
        assertEquals(jogoFake, resultado);
    }

    @Test
    public void carregarJogoInexistenteRetornaNull() {
        when(gameRepoFake.buscar("PartidaFantasma")).thenReturn(null);

        Game resultado = service.carregarJogo("PartidaFantasma");
        assertNull(resultado);
    }

    @Test
    public void salvarProgressoComSucesso() {
        Game jogoFake = mock(Game.class);

        boolean resultado = service.salvarProgresso(jogoFake);

        assertTrue(resultado);
        verify(gameRepoFake, times(1)).salvar(jogoFake);
    }

    @Test
    public void salvarProgressoJogoNuloRetornaFalso() {
        boolean resultado = service.salvarProgresso(null);

        assertFalse(resultado);
        verify(gameRepoFake, never()).salvar(any(Game.class));
    }

    // Deletar Save  - - - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void deletarSave() {
        when(gameRepoFake.remover("Partida1")).thenReturn(true);

        boolean resultado = service.deletarSave("Partida1");

        assertTrue(resultado);
        verify(gameRepoFake, times(1)).remover("Partida1");
    }

    // Consultar Progresso  - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void consultarProgressoJogo() {
        Game jogo = service.iniciarNovoJogo("Partida1", "Katniss", new ArrayList<>(), "SemAparencia");

        // Semestre 1 / 6 semestres totais = 16% de progresso
        assertEquals(16, service.consultarProgresso(jogo));
    }
}