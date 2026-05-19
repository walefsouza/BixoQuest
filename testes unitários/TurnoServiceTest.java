import model.academico.Disciplina;
import model.academico.Semestre;
import model.atividades.*;
import model.entidades.Jogador;
import model.entidades.Professor;
import model.mapa.SalaDeAula;
import model.mapa.TipoLocal;
import service.AcademicoService;
import service.TurnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TurnoServiceTest {

    private Jogador jogador;
    private Semestre semestre;
    private TurnoService service;
    private AcademicoService academicoServiceFake;

    @BeforeEach
    public void setUp() {

        jogador = new Jogador("Bento", 30, 50,
                50, 80, 50,
                100.0, null, "img_bento.png");

        EventoAvaliacao avaliacao = new EventoAvaliacao(
                "Prova", "Programação II", TipoLocal.SALA_DE_AULA,
                CategoriaEvento.OBRIGATORIO, RequisitoEvento.SEMANA4,
                "icone.png", new ArrayList<>(), new ArrayList<>(), 10
        );

        Disciplina disciplina = new Disciplina("PBL",
                new Professor("Bianca", 30, 80, "img_bianca.png", 80),
                "icon.png", avaliacao, TipoLocal.SALA_DE_AULA);

        semestre = new Semestre(1);
        semestre.getDisciplinas().add(disciplina);

        academicoServiceFake = mock(AcademicoService.class);
        service = new TurnoService(academicoServiceFake);
    }

    // Passar Semana - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void passarSemanaRestaurarAtributos() {
        // Ao passar a semana, o turno service deve restaurar energia e saúde
        ResultadoAcao resultado = service.passarSemana(semestre, jogador);

        assertNotNull(resultado);
        assertEquals(100, jogador.getEnergia());
        assertTrue(jogador.getSaude() > 80);
    }

    @Test
    public void passarSemanaAvancaSemana() {
        int semanaAntes = semestre.getSemanaAtual();
        service.passarSemana(semestre, jogador);

        assertEquals(semanaAntes + 1, semestre.getSemanaAtual());
    }
}