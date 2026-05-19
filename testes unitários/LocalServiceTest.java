import model.Game;
import model.academico.Semestre;
import model.atividades.Evento;
import model.atividades.ResultadoAcao;
import model.entidades.Jogador;
import model.interacao.Dialogo;
import model.mapa.*;
import repository.IRepository;
import service.AtividadeService;
import service.LocalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LocalServiceTest {

    private Jogador jogador;
    private Semestre semestre;
    private Game jogo;

    private Cantina cantina;
    private Laboratorio laboratorio;
    private Colegiado colegiado;
    private PontoDeOnibus ponto;
    private Borogodo borogodo;

    private IRepository<Evento> eventoRepoFake;
    private IRepository<Dialogo> dialogoRepoFake;
    private IRepository<Local> localRepoFake;
    private AtividadeService atividadeServiceFake;

    private LocalService service;

    @BeforeEach
    public void setUp() {

        jogador = new Jogador("Silvano Sales", 50, 50, 50,
                50, 50, 100.0, null, "img.png");
        semestre = new Semestre(1);

        cantina = new Cantina("Cantina", "cantina", "img", "audio", 0);
        laboratorio = new Laboratorio("Lab", "lab", "img", "audio", 3, 2.0);
        colegiado = new Colegiado("Colegiado", "colegiado", "img", "audio", true);
        ponto = new PontoDeOnibus("Ponto", "ponto de ônibus", "img", "audio");
        borogodo = new Borogodo("Borogodó", "Cassino", "img", "audio", 0, true);

        jogo = new Game("Save1", jogador, semestre, new UniversidadeMapa("UEFS", new ArrayList<>(), "img", "audio"));

        eventoRepoFake = mock(IRepository.class);
        dialogoRepoFake = mock(IRepository.class);
        localRepoFake = mock(IRepository.class);
        atividadeServiceFake = mock(AtividadeService.class);

        service = new LocalService(eventoRepoFake, dialogoRepoFake, localRepoFake);
    }

    // Viajar - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void viajarComEnergiaSuficienteEEventoAleatorioNulo() {

        when(atividadeServiceFake.processarEventosAleatorios(jogo, cantina.getTipo())).thenReturn(null);

        ResultadoAcao resultado = service.viajar(jogo, cantina, atividadeServiceFake);

        assertNotNull(resultado);
        assertEquals(45, jogador.getEnergia()); // Gastou 5 de energia na viagem
        assertEquals(cantina, jogador.getLocal()); // O local atualizou com sucesso
    }

    @Test
    public void viajarSemEnergiaLocalNormal() {
        jogador.decrementarEnergia(50); // Fica com 0

        ResultadoAcao resultado = service.viajar(jogo, cantina, atividadeServiceFake);

        assertNotNull(resultado);
        assertNotEquals(cantina, jogador.getLocal()); // Viagem não ocorreu
    }

    @Test
    public void viajarSemEnergiaParaPontoDeOnibusEExcecao() {
        jogador.decrementarEnergia(50); // Fica com 0

        ResultadoAcao resultado = service.viajar(jogo, ponto, atividadeServiceFake);

        assertNotNull(resultado);

        // O jogador só pode voltar sem energia ao ponto de ônibus
        assertEquals(ponto, jogador.getLocal());
        assertEquals(0, jogador.getEnergia());
    }

    // Comprar Lanche - - - - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void comprarLancheComDinheiroEFilaZero() {

        // Jogador inicia com 100 de dinheiro, Coxinha custa 10.
        ResultadoAcao resultado = service.comprarLanche(jogo, cantina, Cardapio.COXINHA);


        assertNotNull(resultado);
        assertEquals(90.0, jogador.getDinheiro());

        // Como não tem fila, além de saúde ganha energia
        assertTrue(jogador.getSaude() > 50);
        assertTrue(jogador.getEnergia() <= 55);
    }

    @Test
    public void comprarLancheComFilaGastaEnergia() {

        int energiaAntes = jogador.getEnergia(); // 50

        service.comprarLanche(jogo, cantina, Cardapio.COXINHA);

        assertTrue(jogador.getEnergia() < energiaAntes); // A energia caiu

    }

    @Test
    public void comprarLancheSemDinheiro() {
        jogador.decrementarDinheiro(100.0); // Zera o dinheiro

        ResultadoAcao resultado = service.comprarLanche(jogo, cantina, Cardapio.COXINHA);

        assertNotNull(resultado);
        assertEquals(0.0, jogador.getDinheiro()); // Compra não efetuada
    }

    // Usar Computador no Laboratório - - - - - - - - - - - - - - - - - -

    @Test
    public void usarComputadorLabComEnergiaComputador() {
        ResultadoAcao resultado = service.usarComputadorLab(jogo, laboratorio);

        assertNotNull(resultado);
        assertEquals(35, jogador.getEnergia()); // Gastou 15
        assertTrue(jogador.getLevelConhecimento() > 50); // Aumentou o conhecimento
        assertEquals(2, laboratorio.getComputadoresDisponiveis()); // Ocupou uma máquina
    }

    @Test
    public void usarComputadorLabSemEnergia() {
        jogador.decrementarEnergia(45); // Fica com 5 de energia
        ResultadoAcao resultado = service.usarComputadorLab(jogo, laboratorio);

        assertNotNull(resultado);
        assertEquals(5, jogador.getEnergia()); // Não gastou
        assertEquals(3, laboratorio.getComputadoresDisponiveis()); // Não ocupou a máquina
    }

    @Test
    public void usarComputadorLabSemComputadores() {
        laboratorio.setComputadoresDisponiveis(0);
        ResultadoAcao resultado = service.usarComputadorLab(jogo, laboratorio);

        assertNotNull(resultado);
    }

    // Resolver Burocracia no Colegiado - - - - - - - - - - - - - - - - -

    @Test
    public void resolverBurocraciaSistemaAtivo() {
        ResultadoAcao resultado = service.resolverBurocracia(jogador, colegiado);

        assertNotNull(resultado);
        assertEquals(40, jogador.getEnergia()); // Gastou 10
    }

    @Test
    public void resolverBurocraciaSemSistema() {
        colegiado.setSistemaAtivo(false); // Derrubou o Sagres
        ResultadoAcao resultado = service.resolverBurocracia(jogador, colegiado);

        assertNotNull(resultado);

        // O jogador perde a energia porque ficou lá atoa
        assertEquals(40, jogador.getEnergia());
    }

    @Test
    public void resolverBurocraciaSemEnergia() {
        jogador.decrementarEnergia(45); // Fica com 5
        ResultadoAcao resultado = service.resolverBurocracia(jogador, colegiado);

        assertNotNull(resultado);
        assertEquals(5, jogador.getEnergia()); // Não processou
    }

    // Tentar Embarcar (Ponto de Ônibus) - - - - - - - - - - - - - - - -

    @Test
    public void tentarEmbarcarEnergiaBaixaSucesso() {
        jogador.decrementarEnergia(40); // Fica com 10 (<= 20 é o requisito do ônibus)

        ResultadoAcao resultado = service.tentarEmbarcar(jogo, ponto, false);

        assertNotNull(resultado);
        assertTrue(jogo.getFlagSemana()); // A semana vai virar
    }

    @Test
    public void tentarEmbarcarEnergiaAltaFalha() {
        jogador.aumentarEnergia(50); // Está com muita energia

        ResultadoAcao resultado = service.tentarEmbarcar(jogo, ponto, false);

        assertNotNull(resultado);
        assertEquals(90, jogador.getEnergia()); // -10 de penalidade por esperar
        assertFalse(jogo.getFlagSemana()); // O ônibus não o levou
    }

    // Apostar no Borogodó - - - - - - - - - - - - - - - - - - - -

    @Test
    public void apostarNoBorogodoSemDinheiroSuficiente() {
        jogador.decrementarDinheiro(95.0); // 5 de saldo

        ResultadoAcao resultado = service.apostarNoBorogodo(jogador, borogodo);

        assertNotNull(resultado);
        assertEquals(5.0, jogador.getDinheiro()); // A aposta não foi realizada
    }
}