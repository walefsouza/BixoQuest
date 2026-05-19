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
    private SalaDeAula sala;

    @BeforeEach
    public void setUp() {

        cantina = new Cantina("Cantina", "Compre comida", "img_cantina.png", "som_cantina.mp3", 5);
        borogodo = new Borogodo("Borogodó", "Praça local", "img_borogodo.png", "som_borogodo.mp3", 3, true);
        colegiado = new Colegiado("Colegiado", "Burocracia Acadêmica", "img_colegiado.png", "som_colegiado.mp3", true);
        laboratorio = new Laboratorio("Lab", "Experimentos", "img_lab.png", "som_lab.mp3", 3, 1.5);
        ponto = new PontoDeOnibus("Ponto", "Pegue o ônibus e volte energizado", "img_ponto.png", "som_ponto.mp3");
        sala = new SalaDeAula("Sala 1", "Sala de aula padrão", "img_sala.png", "som_sala.mp3", true);
    }

    // Validação Estrutural (Tipos de Locais) - - - - - - - - - - - - - - - - -

    @Test
    public void verificarRetornoCorretoDoTipoLocalDasSubclasses() {
        assertEquals(TipoLocal.CANTINA, cantina.getTipo());
        assertEquals(TipoLocal.BOROGODO, borogodo.getTipo());
        assertEquals(TipoLocal.COLEGIADO, colegiado.getTipo());
        assertEquals(TipoLocal.LABORATORIO, laboratorio.getTipo());
        assertEquals(TipoLocal.PONTO_DE_ONIBUS, ponto.getTipo());
        assertEquals(TipoLocal.SALA_DE_AULA, sala.getTipo());
    }

    // Cantina - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void calcularTempoDeEsperaTamanhoFila() {
        // com fila (tamanho 5 * 2 = 10)
        assertEquals(10, cantina.calcularTempoDeEspera());

        // sem fila
        cantina.setTamanhoFila(0);
        assertEquals(0, cantina.calcularTempoDeEspera());
    }

    // Colegiado - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void verificarAtendimentoSistemaColegiadoAtivo() {
        // sistema ativo
        assertTrue(colegiado.verificarAtendimento());

        // sistema falso
        colegiado.setSistemaAtivo(false);
        assertFalse(colegiado.verificarAtendimento());
    }

    // Laboratorio - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void temComputadorLivreNoLaboratorio() {
        // tem computadores (3) = true
        assertTrue(laboratorio.temComputadorLivre());

        // não tem computadores = false
        laboratorio.setComputadoresDisponiveis(0);
        assertFalse(laboratorio.temComputadorLivre());
    }

    // PontoDeOnibus - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void onibusEstaNoPontoEsperandoOJogador() {
        // se ele tiver energia < 20 , consegue pegar
        assertTrue(ponto.onibusEstaNoPonto(10));

        // teste de limite exato (20)
        assertTrue(ponto.onibusEstaNoPonto(20));

        // se tiver energia sobrando, ônibus = false (não está no ponto)
        assertFalse(ponto.onibusEstaNoPonto(50));
    }

    // Sala de Aula  - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void verificarStatusDeOcupacaoDaSalaDeAula() {

        // A sala foi setada como livre
        assertTrue(sala.getSalaLivre());

        // Alterando para ocupada
        sala.setSalaLivre(false);
        assertFalse(sala.getSalaLivre());
    }
}