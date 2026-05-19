import model.atividades.ResultadoAcao;
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
                50, 50, 100.0, null, "img_maria.png");

        colegaInteligente = new Colega("Dorinha", 20, 80, "img.png", 4, 3);
        colegaHumorBaixo = new Colega("Padre", 20, 20, "img.png", 2, 1);

        service = new JogadorService();
    }

    // Estudar com Colega - - - - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void estudarComColegaComEnergia() {
        ResultadoAcao resultado = service.estudarComColega(jogador, colegaInteligente);

        assertNotNull(resultado);

        assertEquals(40, jogador.getEnergia()); // Gastou 10
        assertEquals(13, jogador.getLevelConhecimento()); // Ganhou 5 base + 8 do bônus de inteligência
    }

    @Test
    public void estudarComColegaSemEnergia() {
        jogador.decrementarEnergia(45); // Baixa a energia de 50 para 5

        ResultadoAcao resultado = service.estudarComColega(jogador, colegaInteligente);

        assertNotNull(resultado);
        assertEquals(5, jogador.getEnergia()); // Não gastou energia
        assertEquals(0, jogador.getLevelConhecimento()); // Não ganhou conhecimento
    }

    // Interagir Socialmente - - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void interagirSocialmenteColegaHumorAlto() {
        int motivacaoAntes = jogador.getMotivacao(); // Era 50

        ResultadoAcao resultado = service.interagirSocialmente(jogador, colegaInteligente);

        assertNotNull(resultado);

        // Dorinha tem carisma 3 (3 * 3 = 9 de bônus de motivação)
        assertEquals(motivacaoAntes + 9, jogador.getMotivacao());
    }

    @Test
    public void interagirSocialmenteColegaHumorBaixo() {
        int motivacaoAntes = jogador.getMotivacao();

        ResultadoAcao resultado = service.interagirSocialmente(jogador, colegaHumorBaixo);

        assertNotNull(resultado);

        // O Padre tem humor 20. Qualquer humor abaixo de 40 gera um impacto negativo fixo de -10.
        assertTrue(jogador.getMotivacao() < motivacaoAntes);
        assertEquals(motivacaoAntes - 10, jogador.getMotivacao());
    }
}