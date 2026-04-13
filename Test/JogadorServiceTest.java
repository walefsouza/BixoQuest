import model.entidades.Colega;
import model.entidades.Jogador;
import service.JogadorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JogadorServiceTest {

    private Jogador jogador;
    private Colega colegaInteligente;
    private Colega colegaHumorBaixo;
    private JogadorService service;

    @BeforeEach
    public void setUp() {

        jogador = new Jogador("Maria", 50, 0, 50,
                50, 50, 100.0, null);

        colegaInteligente = new Colega("Dorinha", 20, 80, 4, 3);
        colegaHumorBaixo = new Colega("Padre", 20, 20, 2, 1);
        service = new JogadorService();
    }

    // estudarComColega

    @Test
    public void estudarComColegaComEnergia() {
        boolean resultado = service.estudarComColega(jogador, colegaInteligente);
        assertTrue(resultado);
        assertEquals(40, jogador.getEnergia());
        assertEquals(18, jogador.getLevelConhecimento());
    }

    @Test
    public void estudarComColegaSemEnergia() {
        jogador.setEnergia(5);
        boolean resultado = service.estudarComColega(jogador, colegaInteligente);
        assertFalse(resultado);
        assertEquals(5, jogador.getEnergia());
        assertEquals(0, jogador.getLevelConhecimento());
    }

    // interagirSocialmente

    @Test
    public void interagirSocialmenteColegaHumorAlto() {
        int motivacaoAntes = jogador.getMotivacao();
        boolean resultado = service.interagirSocialmente(jogador, colegaInteligente);
        assertTrue(resultado);
        assertTrue(jogador.getMotivacao() > motivacaoAntes);
    }

    @Test
    public void interagirSocialmenteColegaHumorBaixo() {
        int motivacaoAntes = jogador.getMotivacao();
        boolean resultado = service.interagirSocialmente(jogador, colegaHumorBaixo);
        assertFalse(resultado);
        assertTrue(jogador.getMotivacao() < motivacaoAntes);
    }
}