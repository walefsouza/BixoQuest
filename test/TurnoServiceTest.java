import model.academico.Disciplina;
import model.academico.Semestre;
import model.atividades.CategoriaEvento;
import model.atividades.EventoAvaliacao;
import model.entidades.Jogador;
import model.entidades.Professor;
import model.mapa.SalaDeAula;
import repository.Repository;
import service.AcademicoService;
import service.TurnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TurnoServiceTest {

    private Jogador jogador;
    private Semestre semestre;
    private Disciplina disciplina;
    private TurnoService service;

    @BeforeEach
    public void setUp() {
        jogador = new Jogador("Bento", 30, 50,
                50, 80, 50,
                100.0, null);

        EventoAvaliacao avaliacao = new EventoAvaliacao(
                "Prova", "Programação II", null,
                -10, 0, 0,
                0, 0, 0.0,
                100, CategoriaEvento.OBRIGATORIO,
                new ArrayList<>(), 10
        );

        disciplina = new Disciplina("PBL",
                new Professor("Bianca", 30, 80, 80),
                0, 0, avaliacao,
                new SalaDeAula("MP", "Prática", true));

        semestre = new Semestre(1);
        semestre.getDisciplinas().add(disciplina);

        AcademicoService academicoService = new AcademicoService(new Repository<>(), new Repository<>());
        service = new TurnoService(academicoService);
    }

    // passarSemana

    @Test
    public void passarSemanaRestaurarAtributos() {

        // energia

        service.passarSemana(semestre, jogador);
        assertEquals(100, jogador.getEnergia());

        // saúde

        int saudeAntes = jogador.getSaude();
        service.passarSemana(semestre, jogador);
        assertTrue(jogador.getSaude() > saudeAntes);
    }

    @Test
    public void passarSemanaAvancaSemana() {
        int semanaAntes = semestre.getSemanaAtual();
        service.passarSemana(semestre, jogador);
        assertEquals(semanaAntes + 1, semestre.getSemanaAtual());
    }

    @Test
    public void passarSemanaDispararAvaliacao() {
        semestre.setSemanaAtual(semestre.getSemanaMax() - 1);
        int energiaAntes = jogador.getEnergia();
        service.passarSemana(semestre, jogador);
        assertTrue(jogador.getEnergia() < 100);
    }
}