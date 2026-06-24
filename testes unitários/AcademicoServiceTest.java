import model.Game;
import model.academico.Disciplina;
import model.academico.Semestre;
import model.atividades.CategoriaEvento;
import model.atividades.Evento;
import model.atividades.EventoAvaliacao;
import model.atividades.Pergunta;
import model.atividades.ResultadoAcao;
import model.entidades.Jogador;
import model.entidades.Professor;
import model.mapa.Local;
import model.mapa.SalaDeAula;
import model.mapa.UniversidadeMapa;
import repository.IRepository;
import service.AcademicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AcademicoServiceTest {

    private Jogador jogador;
    private Disciplina disciplina;
    private Semestre semestre;
    private Game jogo;
    private AcademicoService service;
    private IRepository<Semestre> semestreRepoFake;
    private IRepository<Evento> eventoRepoFake;

    @BeforeEach
    public void setUp() {
        jogador = new Jogador("Chicó", 50, 0, 50,
                50, 70, 100.0, null, "img.png");

        Professor professor = new Professor("Florzinha", 40, 80, "img.png", 50);

        SalaDeAula sala = new SalaDeAula("Sala 1", "Sala teste", "img", "audio", true);

        EventoAvaliacao avaliacao = new EventoAvaliacao(
                "Prova", "Prova teste", null,
                CategoriaEvento.OBRIGATORIO, null, "icone",
                new ArrayList<>(), new ArrayList<>(), 10
        );

        disciplina = new Disciplina("PBL", professor, "icone", avaliacao, sala.getTipo());

        semestre = new Semestre(1);
        semestre.getDisciplinas().add(disciplina);

        semestreRepoFake = mock(IRepository.class);
        eventoRepoFake = mock(IRepository.class);

        Semestre proximoSemestre = new Semestre(2);
        when(semestreRepoFake.buscar("2")).thenReturn(proximoSemestre);

        jogo = new Game("Partida1", jogador, semestre);

        service = new AcademicoService(semestreRepoFake, eventoRepoFake);
    }

    // Assistir Aula  - - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void assistirAulaComEnergiaSuficiente() {

        ResultadoAcao aula = service.assistirAula(jogo, disciplina);
        assertNotNull(aula);
        assertEquals(40, jogador.getEnergia()); // Gastou 10 de energia
        assertEquals(1, disciplina.getFrequencia()); // Aumentou frequência
        assertEquals(10, jogador.getLevelConhecimento()); // Aumentou conhecimento
    }

    @Test
    void assistirAulaSemEnergiaSuficiente() {

        jogador.decrementarEnergia(45); // O jogador tinha 50, agora tem 5
        ResultadoAcao aula = service.assistirAula(jogo, disciplina);

        assertNotNull(aula);
        assertEquals(5, jogador.getEnergia()); // A energia não foi gasta
        assertEquals(0, disciplina.getFrequencia()); // A frequência não aumentou
    }

    // Estudar Disciplina  - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void estudarDisciplinaComEnergiaSuficiente() {
        ResultadoAcao resultado = service.estudarDisciplina(jogo, disciplina);

        assertNotNull(resultado);
        assertEquals(35, jogador.getEnergia()); // Gastou 15 de energia
        assertEquals(55, jogador.getMotivacao()); // Ganhou 5 de motivação
    }

    @Test
    public void estudarDisciplinaSemEnergiaSuficiente() {

        jogador.decrementarEnergia(40); // Fica com 10, precisa de 15
        ResultadoAcao resultado = service.estudarDisciplina(jogo, disciplina);

        assertNotNull(resultado);
        assertEquals(10, jogador.getEnergia()); // Não gastou
        assertEquals(50, jogador.getMotivacao()); // Não ganhou
    }

    // Verificar Avaliações  - - - - - - - - - - - - - - - - - - - - - -

    @Test
    void verificarInicioAvaliacoesNaSemanaDeProvas() {

        semestre.setSemanaAtual(semestre.getSemanaMax()); // Semana 4
        List<EventoAvaliacao> avaliacoes = service.verificarInicioAvaliacoes(semestre);

        assertFalse(avaliacoes.isEmpty());
        assertEquals(1, avaliacoes.size()); // A disciplina cadastrada no setUp()
    }

    @Test
    void verificarInicioAvaliacoesForaDaSemanaDeProvas() {

        semestre.setSemanaAtual(1); // Longe da semana de provas
        List<EventoAvaliacao> avaliacoes = service.verificarInicioAvaliacoes(semestre);

        assertTrue(avaliacoes.isEmpty());
    }

    // Corrigir Avaliação  - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    void corrigirAvaliacaoCalculaNotaRealizadaCorretamente() {

        // Criando a prova com duas perguntas
        List<Pergunta> perguntas = new ArrayList<>();
        perguntas.add(new Pergunta("Pergunta 1?", new String[]{"A", "B"}, 0)); // Resposta correta: 0
        perguntas.add(new Pergunta("Pergunta 2?", new String[]{"A", "B"}, 1)); // Resposta correta: 1
        disciplina.getAvaliacao().setPerguntas(perguntas);

        // Simulando o jogador acertando a primeira e errando a segunda
        List<Integer> respostasDoJogador = new ArrayList<>();
        respostasDoJogador.add(0); // Acertou
        respostasDoJogador.add(0); // Errou

        ResultadoAcao resultado = service.corrigirAvaliacao(jogo, disciplina, respostasDoJogador);

        assertNotNull(resultado);
        assertEquals(10, disciplina.getAvaliacao().getNotaObtida()); // 1 acerto * 10
        assertTrue(disciplina.getAvaliacao().getRealizada());
        assertTrue(resultado.getTextoNarrativo().contains("Sua nota foi: 10")); // Verifica texto narrativo
    }

    // Avançar Semestre  - - - - - - - - - - - - - - - - - - - - - - - -

    // Timeskip

    @Test
    void avancarSemestreComTimeskip() {
        ResultadoAcao resultado = service.avancarSemestre(jogo, true);

        assertNotNull(resultado);
        assertEquals(2, jogo.getSemestre().getNumero());
    }

    // Aprovado

    @Test
    void avancarSemestreAprovado() {

        disciplina.getAvaliacao().setRealizada(true);
        disciplina.getAvaliacao().setNotaObtida(8);
        disciplina.participarAula(jogador);
        disciplina.participarAula(jogador);
        jogador.aumentarDesempenhoAcademico(70);
        jogador.aumentarLevelConhecimento(50);

        ResultadoAcao resultado = service.avancarSemestre(jogo, false);

        assertNotNull(resultado);
        assertTrue(semestre.getConcluido());
        assertEquals(2, jogo.getSemestre().getNumero());
    }

    // Reprovado

    @Test
    void avancarSemestreReprovado() {

        disciplina.getAvaliacao().setRealizada(true);
        disciplina.getAvaliacao().setNotaObtida(2);
        jogador.aumentarDesempenhoAcademico(70);
        jogador.aumentarMotivacao(50);
        jogador.aumentarLevelConhecimento(50);
        semestre.setSemanaAtual(4);

        int motivacaoAntes = jogador.getMotivacao();
        int conhecimentoAntes = jogador.getLevelConhecimento();

        ResultadoAcao resultado = service.avancarSemestre(jogo, false);

        assertNotNull(resultado);
        assertEquals(1, semestre.getSemanaAtual()); // Resetou a semana
        assertTrue(jogador.getMotivacao() < motivacaoAntes); // Sofreu as punições
        assertTrue(jogador.getLevelConhecimento() < conhecimentoAntes);
        assertFalse(disciplina.getAprovado());
    }

    // Formatura

    @Test
    void avancarSemestreUltimoSemestreFormaJogador() {

        // Simulando que o proximo semestre é null
        when(semestreRepoFake.buscar("2")).thenReturn(null);

        disciplina.getAvaliacao().setRealizada(true);
        disciplina.getAvaliacao().setNotaObtida(8);
        disciplina.participarAula(jogador);
        jogador.aumentarDesempenhoAcademico(70);

        ResultadoAcao resultado = service.avancarSemestre(jogo, false);

        assertNotNull(resultado);
        assertTrue(jogo.verificarFormado());
        assertTrue(resultado.getTextoNarrativo().contains("VOCÊ VENCEU A UEFS E SE FORMOU!"));
    }
}