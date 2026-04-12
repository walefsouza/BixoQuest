import model.Game;
import model.academico.Disciplina;
import model.academico.Semestre;
import model.atividades.CategoriaEvento;
import model.atividades.Evento;
import model.atividades.EventoAvaliacao;
import model.entidades.Jogador;
import model.entidades.Professor;
import model.mapa.Local;
import model.mapa.SalaDeAula;
import model.mapa.UniversidadeMapa;
import repository.IRepository;
import repository.Repository;
import service.AcademicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class AcademicoServiceTest {

    private Jogador jogador;
    private Disciplina disciplina;
    private Semestre semestre;
    private Game jogo;
    private AcademicoService service;
    private IRepository<Semestre> semestreRepo;
    private IRepository<Evento> eventoRepo;

    @BeforeEach
    void setUp() {
        jogador = new Jogador("Chicó", 50, 0, 50, 50, 70, 100.0, null);

        Professor professor = new Professor("Florzinha", 40, 80, 50);
        SalaDeAula sala = new SalaDeAula("Sala 1", "Sala teste", true);
        EventoAvaliacao avaliacao = new EventoAvaliacao(
                "Prova", "Prova teste", null,
                -10, 0, 0, 0, 10, 0.0,
                100, CategoriaEvento.OBRIGATORIO,
                new ArrayList<>(), 10
        );

        disciplina = new Disciplina("PBL", professor, 0, 0, avaliacao, sala);
        semestre = new Semestre(1);
        semestre.getDisciplinas().add(disciplina);

        semestreRepo = new Repository<>();
        eventoRepo = new Repository<>();

        Semestre proximoSemestre = new Semestre(2);
        semestreRepo.salvar(proximoSemestre);

        jogo = new Game("Partida1", jogador, semestre, new UniversidadeMapa("UEFS", new ArrayList<Local>()));

        service = new AcademicoService(semestreRepo, eventoRepo);
    }

    // assistirAula

    @Test
    public void assistirAulaComEnergiaSuficiente() {
        boolean aula = service.assistirAula(jogador, disciplina);
        assertTrue(aula);
        assertEquals(40, jogador.getEnergia());
        assertEquals(1, disciplina.getFrequencia());
    }

    @Test
    void assistirAulaSemEnergiaSuficiente() {
        jogador.setEnergia(5);
        boolean aula = service.assistirAula(jogador, disciplina);
        assertFalse(aula);
        assertEquals(5, jogador.getEnergia());
        assertEquals(0, disciplina.getFrequencia());
    }

    // avancarSemestre — timeskip

    @Test
    void avancarSemestreComTimeskip() {
        boolean resultado = service.avancarSemestre(jogo, true);
        assertTrue(resultado);
        assertEquals(2, jogo.getSemestre().getNumero());
    }

// avancarSemestre — aprovado

    @Test
    void avancarSemestreAprovado() {
        disciplina.getAvaliacao().setRealizada(true);
        disciplina.getAvaliacao().setNotaObtida(8);
        disciplina.participarAula(jogador);
        disciplina.participarAula(jogador);
        jogador.setDesempenhoAcademico(70);
        jogador.setLevelConhecimento(50);

        boolean resultado = service.avancarSemestre(jogo, false);

        assertTrue(resultado);
        assertTrue(semestre.getConcluido());
        assertEquals(2, jogo.getSemestre().getNumero());
    }

// avancarSemestre — reprovado

    @Test
    void avancarSemestreReprovado() {
        disciplina.getAvaliacao().setRealizada(true);
        disciplina.getAvaliacao().setNotaObtida(2);
        jogador.setDesempenhoAcademico(70);
        jogador.setMotivacao(50);
        jogador.setLevelConhecimento(50);
        semestre.setSemanaAtual(4);

        int motivacaoAntes = jogador.getMotivacao();
        int conhecimentoAntes = jogador.getLevelConhecimento();

        boolean resultado = service.avancarSemestre(jogo, false);

        assertFalse(resultado);
        assertEquals(1, semestre.getSemanaAtual());
        assertTrue(jogador.getMotivacao() < motivacaoAntes);
        assertTrue(jogador.getLevelConhecimento() < conhecimentoAntes);
        assertFalse(disciplina.getAprovado());
    }

    // formatura

    @Test
    void avancarSemestreUltimoSemestreFormaJogador() {
        Semestre ultimo = new Semestre(8);
        EventoAvaliacao av = new EventoAvaliacao(
                "Prova Final", "desc", null,
                0, 0, 0, 0, 0, 0.0,
                100, CategoriaEvento.OBRIGATORIO,
                new ArrayList<>(), 10
        );
        av.setRealizada(true);
        av.setNotaObtida(8);
        Disciplina d = new Disciplina("TCC",
                new Professor("Prof", 40, 80, 50), 0, 0, av,
                new SalaDeAula("Sala", "desc", true));
        ultimo.getDisciplinas().add(d);
        jogador.setDesempenhoAcademico(70);

        Game jogoFinal = new Game("Final", jogador, ultimo, new UniversidadeMapa("UEFS", new ArrayList<>()));
        service.avancarSemestre(jogoFinal, true);

        assertTrue(jogoFinal.verificarFormado());
    }

}