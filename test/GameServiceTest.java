import model.Game;
import model.academico.Semestre;
import repository.Repository;
import service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {

    private Repository<Game> gameRepo;
    private Repository<Semestre> semestreRepo;
    private GameService service;

    @BeforeEach
    public void setUp() {

        gameRepo = new Repository<>();

        semestreRepo = new Repository<>();
        semestreRepo.salvar(new Semestre(1));

        service = new GameService(gameRepo, semestreRepo);
    }

    // iniciarNovoJogo

    @Test
    public void iniciarNovoJogo() {
        Game jogo = service.iniciarNovoJogo("Partida1", "Katniss");
        assertNotNull(jogo);
        assertEquals("Partida1", jogo.getNome());
        assertEquals("Katniss", jogo.getJogador().getNome());
    }

    @Test
    public void iniciarNovoJogoSalvarNoRepositorio() {
        service.iniciarNovoJogo("Partida1", "Katniss");
        assertNotNull(gameRepo.buscar("Partida1"));
    }

    @Test
    public void iniciarNovoJogoJogadorComAtributosIniciais() {
        Game jogo = service.iniciarNovoJogo("Partida1", "Katniss");
        assertEquals(100, jogo.getJogador().getEnergia());
        assertEquals(50, jogo.getJogador().getSaude());
    }

    // listarJogos

    @Test
    public void listarJogosListaVazia() {
        List<Game> jogos = service.listarJogos();
        assertTrue(jogos.isEmpty());
    }

    @Test
    public void listarJogosComJogosNaLista() {
        service.iniciarNovoJogo("Partida1", "Katniss");
        service.iniciarNovoJogo("Partida2", "Peeta");
        assertEquals(2, service.listarJogos().size());
    }

    // buscarJogo

    @Test
    public void buscarJogoExistente() {
        service.iniciarNovoJogo("Partida1", "Katniss");
        assertNotNull(service.buscarJogo("Partida1"));
    }

    // encerrarJogo

    @Test
    public void encerrarJogo() {
        service.iniciarNovoJogo("Partida1", "Katniss");
        assertTrue(service.encerrarJogo("Partida1"));
        assertNull(gameRepo.buscar("Partida1"));
    }

    // consultarProgresso

    @Test
    public void consultarProgressoJogo() {
        Game jogo = service.iniciarNovoJogo("Partida1", "Katniss");
        assertEquals(12, service.consultarProgresso(jogo));
    }
}