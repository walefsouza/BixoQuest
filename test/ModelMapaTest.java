import model.mapa.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ModelMapaTest {

    private Cantina cantina;
    private Borogodo borogodo;
    private Colegiado colegiado;
    private Laboratorio laboratorio;
    private PontoDeOnibus ponto;

    @BeforeEach
    public void setUp() {

        cantina = new Cantina("Cantina", "Compre comida", 5);
        borogodo = new Borogodo("Borogodó", "Praça local", 3, true);
        colegiado = new Colegiado("Colegiado", "Burocracia Acadêmica", true);
        laboratorio = new Laboratorio("Lab", "Experimentos", 3, 1.5);
        ponto = new PontoDeOnibus("Ponto", "Pegue o ônibus e volte energizado");
    }

    // Cantina

    @Test
    public void calcularTempoDeEsperaTamanhoFila() {
        // com fila
        assertEquals(10, cantina.calcularTempoDeEspera());

        // sem fila
        cantina.setTamanhoFila(0);
        assertEquals(0, cantina.calcularTempoDeEspera());
    }

    // Colegiado

    @Test
    public void verificarAtendimentoSistemaColegiadoAtivo() {

        // sistema ativo
        assertTrue(colegiado.verificarAtendimento());

        //sistema falso
        colegiado.setSistemaAtivo(false);
        assertFalse(colegiado.verificarAtendimento());
    }

    // Laboratorio

    @Test
    public void temComputadorLivreNoLaboratorio() {
        // tem = true
        assertTrue(laboratorio.temComputadorLivre());

        // não tem = false
        laboratorio.setComputadoresDisponiveis(0);
        assertFalse(laboratorio.temComputadorLivre());
    }

    // PontoDeOnibus

    @Test
    public void onibusEstaNoPontoEsperandoOJogador() {
        // se ele não tiver energia, checkpoint
        assertTrue(ponto.onibusEstaNoPonto(10));

        // se tiver, ônibus = false
        assertFalse(ponto.onibusEstaNoPonto(50));
    }

    // Borogodo

    @Test
    public void sorteNoTriguinhoSemAtividadeAtiva() {
        borogodo.setAtividadeAtiva(false);
        assertEquals(50, borogodo.sorteNoTriguinho(50));
    }
}