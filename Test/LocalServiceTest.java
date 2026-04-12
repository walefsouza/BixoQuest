import model.academico.Semestre;
import model.atividades.CategoriaEvento;
import model.atividades.Evento;
import model.atividades.EventoPassarMal;
import model.entidades.Jogador;
import model.mapa.*;
import repository.IRepository;
import repository.Repository;
import service.LocalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LocalServiceTest {

    private Jogador jogador;
    private Semestre semestre;
    private Cantina cantina;
    private Laboratorio laboratorio;
    private Colegiado colegiado;
    private PontoDeOnibus ponto;
    private IRepository<Evento> eventoRepo;
    private LocalService service;

    @BeforeEach
    void setUp() {
        jogador = new Jogador("Silvano Sales", 50, 50, 50, 50, 50, 100.0, null);
        semestre = new Semestre(1);
        cantina = new Cantina("Cantina", "cantina", 0);
        laboratorio = new Laboratorio("Lab", "lab", 3, 2.0);
        colegiado = new Colegiado("Colegiado", "colegiado", true);
        ponto = new PontoDeOnibus("Ponto", "ponto de ônibus");
        eventoRepo = new Repository<>();
        service = new LocalService(eventoRepo, new Repository<>());
    }

    // viajar

    @Test
    public void viajarComEnergiaSuficiente() {
        boolean resultado = service.viajar(semestre, jogador, cantina);
        assertTrue(resultado);
        assertEquals(45, jogador.getEnergia());
        assertEquals(cantina, jogador.getLocal());
    }

    @Test
    public void viajarSemEnergiaLocalNormal() {
        jogador.setEnergia(0);
        boolean resultado = service.viajar(semestre, jogador, cantina);
        assertFalse(resultado);
    }

    @Test
    public void viajarSemEnergiaParaPontoDeOnibus() {
        jogador.setEnergia(0);
        boolean resultado = service.viajar(semestre, jogador, ponto);
        assertTrue(resultado);
        assertEquals(ponto, jogador.getLocal());
        assertEquals(0, jogador.getEnergia());
    }

    // comprarLanche

    @Test
    public void comprarLancheComDinheiroEFilaZero() {
        boolean resultado = service.comprarLanche(semestre, jogador, cantina, Cardapio.COXINHA);
        assertTrue(resultado);
        assertEquals(90.0, jogador.getDinheiro());
        assertEquals(60, jogador.getSaude());
        assertEquals(55, jogador.getEnergia());
    }

    @Test
    public void comprarLancheComFila() {
        cantina.setTamanhoFila(3);
        service.comprarLanche(semestre, jogador, cantina, Cardapio.COXINHA);
        assertEquals(47, jogador.getEnergia());
        assertEquals(2, cantina.getTamanhoFila());
    }

    @Test
    public void comprarLancheSemDinheiro() {
        jogador.setDinheiro(0.0);
        boolean resultado = service.comprarLanche(semestre, jogador, cantina, Cardapio.COXINHA);
        assertFalse(resultado);
    }

    // usarComputadorLab

    @Test
    public void usarComputadorLabComEnergiaComputador() {
        boolean resultado = service.usarComputadorLab(jogador, laboratorio);
        assertTrue(resultado);
        assertEquals(35, jogador.getEnergia());
        assertEquals(70, jogador.getLevelConhecimento());
        assertEquals(2, laboratorio.getComputadoresDisponiveis());
    }

    @Test
    public void usarComputadorLabSemEnergia() {
        jogador.setEnergia(10);
        boolean resultado = service.usarComputadorLab(jogador, laboratorio);
        assertFalse(resultado);
    }

    @Test
    public void usarComputadorLabSemComputadores() {
        laboratorio.setComputadoresDisponiveis(0);
        boolean resultado = service.usarComputadorLab(jogador, laboratorio);
        assertFalse(resultado);
    }

    // resolverBurocracia

    @Test
    public void resolverBurocraciaSistemaAtivo() {
        boolean resultado = service.resolverBurocracia(jogador, colegiado);
        assertTrue(resultado);
        assertEquals(40, jogador.getEnergia());
        assertEquals(55, jogador.getLevelConhecimento());
    }

    @Test
    public void resolverBurocraciaSemSistema() {
        colegiado.setSistemaAtivo(false);
        boolean resultado = service.resolverBurocracia(jogador, colegiado);
        assertFalse(resultado);
        assertEquals(40, jogador.getEnergia());
    }

    @Test
    public void resolverBurocraciaSemEnergia() {
        jogador.setEnergia(5);
        boolean resultado = service.resolverBurocracia(jogador, colegiado);
        assertFalse(resultado);
        assertEquals(5, jogador.getEnergia());
    }

    // tentarEmbarcar

    @Test
    public void tentarEmbarcarEnergiaBaixa() {
        jogador.setEnergia(10);
        assertTrue(service.tentarEmbarcar(jogador, ponto, false));
    }

    @Test
    public void tentarEmbarcarEnergiaAlta() {
        jogador.setEnergia(50);
        service.tentarEmbarcar(jogador, ponto, false);
        assertEquals(40, jogador.getEnergia());
    }
}