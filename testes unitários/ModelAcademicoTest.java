import model.academico.Disciplina;
import model.academico.Semestre;
import model.atividades.CategoriaEvento;
import model.atividades.EventoAvaliacao;
import model.atividades.RequisitoEvento;
import model.entidades.Jogador;
import model.entidades.Professor;
import model.mapa.SalaDeAula;
import model.mapa.TipoLocal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class ModelAcademicoTest {

    private Jogador jogador;
    private Professor professor;
    private SalaDeAula sala;
    private EventoAvaliacao avaliacao;
    private Disciplina disciplina;
    private Semestre semestre;

    @BeforeEach
    public void setUp() {
        jogador = new Jogador("Jogador", 50,
                0, 50,
                50, 50,
                100.0, null, "img.png");

        professor = new Professor("Professor", 40, 80, "img.png", 50);

        sala = new SalaDeAula("Sala 1", "Sala de teste", "img", "audio", true);

        avaliacao = new EventoAvaliacao(
                "Prova", "Avaliação I", TipoLocal.SALA_DE_AULA,
                CategoriaEvento.OBRIGATORIO, RequisitoEvento.NENHUM, "icone",
                new ArrayList<>(), new ArrayList<>(), 10
        );

        disciplina = new Disciplina("PBL", professor, "icone", avaliacao, sala.getTipo());

        semestre = new Semestre(1);
        semestre.getDisciplinas().add(disciplina);
    }

    // Semestre - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void avancarSemanaNoLimite() {
        semestre.setSemanaAtual(1);
        semestre.avancarSemana();
        assertEquals(2, semestre.getSemanaAtual());
    }

    @Test
    public void avancarSemanaForaDoLimite() {
        semestre.setSemanaAtual(semestre.getSemanaMax());
        semestre.avancarSemana();
        assertEquals(semestre.getSemanaMax(), semestre.getSemanaAtual());
    }

    // Disciplina — participarAula - - - - - - - - - - - - - - - - - - -

    @Test
    public void participarAulaAumentarFrequencia() {
        disciplina.participarAula(jogador);
        assertEquals(1, disciplina.getFrequencia());
    }

    @Test
    public void participarAulaAumentaConhecimento() {
        disciplina.participarAula(jogador);
        assertEquals(5, jogador.getLevelConhecimento());
    }

    @Test
    public void participarAulaAumentaCredibilidadeProfessor() {
        int antesParticipar = professor.getCredibilidade();
        disciplina.participarAula(jogador);
        assertTrue(professor.getCredibilidade() > antesParticipar);
    }

    // Disciplina — concluirDisciplina - - - - - - - - - - - - - - - - -

    @Test
    public void concluirDisciplinaSendoAprovado() {
        avaliacao.setRealizada(true);
        avaliacao.setNotaObtida(8);
        disciplina.participarAula(jogador);
        disciplina.participarAula(jogador);
        disciplina.participarAula(jogador);
        disciplina.concluirDisciplina();
        assertTrue(disciplina.getAprovado());
    }

    @Test
    public void concluirDisciplinaSendoReprovado() {
        avaliacao.setRealizada(true);
        avaliacao.setNotaObtida(2);
        disciplina.concluirDisciplina();
        assertFalse(disciplina.getAprovado());
    }

    @Test
    public void concluirDisciplinaSemFazerProva() {
        avaliacao.setRealizada(false);
        disciplina.concluirDisciplina();
        assertFalse(disciplina.getAprovado());
    }

    // Disciplina — resetarDisciplina - - - - - - - - - - - - - - - - - -

    @Test
    public void resetarDisciplinaZerarFrequencia() {
        disciplina.participarAula(jogador);
        disciplina.participarAula(jogador);
        disciplina.participarAula(jogador);
        assertEquals(3, disciplina.getFrequencia());
        disciplina.resetarDisciplina();
        assertEquals(0, disciplina.getFrequencia());
    }

    @Test
    public void resetarDisciplinaZerarNotaAvaliacao() {
        avaliacao.setNotaObtida(8);
        disciplina.resetarDisciplina();
        assertEquals(0, avaliacao.getNotaObtida());
    }

    @Test
    public void resetarDisciplinaAvaliacaoNaoRealizada() {
        avaliacao.setRealizada(true);
        disciplina.resetarDisciplina();
        assertFalse(avaliacao.getRealizada());
    }

    @Test
    public void resetarDisciplinaRemoverAprovacao() {
        avaliacao.setRealizada(true);
        avaliacao.setNotaObtida(8);
        disciplina.concluirDisciplina(); // O jogador é aprovado
        disciplina.resetarDisciplina(); // O reset da disciplina
        assertFalse(disciplina.getAprovado()); // Verifica se a aprovação foi removida
    }
}