import model.Game;
import model.academico.Semestre;
import model.atividades.*;
import model.entidades.Jogador;
import model.mapa.TipoLocal;
import model.mapa.UniversidadeMapa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ModelAtividadesTest {

    private Game jogo;
    private Jogador jogador;
    private Pergunta pergunta;
    private EventoAvaliacao avaliacao;

    @BeforeEach
    public void setUp() {

        jogador = new Jogador("jogador",
                50, 50, 50, 50, 50,
                100.0, null, "img.png");

        Semestre semestre = new Semestre(1);
        UniversidadeMapa mapa = new UniversidadeMapa("UEFS", new ArrayList<>(), "img", "audio");
        jogo = new Game("Save", jogador, semestre);

        pergunta = new Pergunta(
                "Quanto é 2+2?",
                new String[]{"1", "2", "3", "4"},
                3
        );

        List<Pergunta> perguntas = new ArrayList<>();
        perguntas.add(pergunta);

        List<Efeito> efeitos = new ArrayList<>();
        efeitos.add(new EfeitoAtributos(-10, 0, 0, 0, 0, 0.0));

        avaliacao = new EventoAvaliacao(
                "Prova", "Semana de Provas!", TipoLocal.SALA_DE_AULA,
                CategoriaEvento.OBRIGATORIO, RequisitoEvento.SEMANA4,
                "icone.png", efeitos, perguntas, 10
        );
    }

    // EventoAvaliacao — corrigirResposta - - - - - - - - - - - - - - - -

    @Test
    public void corrigirRespostaIncrementaNotaCorretamente() {

        avaliacao.corrigirResposta(0, 3);
        assertEquals(1, avaliacao.getNotaObtida());
    }

    @Test
    public void corrigirRespostaErradaNaoIncrementaNota() {
        avaliacao.corrigirResposta(0, 1);
        assertEquals(0, avaliacao.getNotaObtida());
    }

    // Atividade — executar - - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void executarAtividadeAplicaEfeitosERetornaDTO() {
        int energiaAntes = jogador.getEnergia(); // 50

        // O método executar retorna o ResultadoAcao (DTO) e aplica os efeitos
        ResultadoAcao resultado = avaliacao.executar(jogo);

        assertNotNull(resultado);
        assertEquals(energiaAntes - 10, jogador.getEnergia()); // Gastou energia
    }

    // Evento — verificarCondicao - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void verificarCondicaoRequisitoSemana4Atendido() {

        // Setando semana 4 e conseguindo verificar avaliações
        jogo.getSemestre().setSemanaAtual(4);
        assertTrue(avaliacao.verificarCondicao(jogo));
    }

    @Test
    public void verificarCondicaoRequisitoSemana4NaoAtendido() {

        // Setando semana 1 e não conseguindo ver avaliações
        jogo.getSemestre().setSemanaAtual(1);
        assertFalse(avaliacao.verificarCondicao(jogo));
    }

    @Test
    public void verificarCondicaoRequisitoNenhumSempreAtende() {

        Evento eventoImprevisto = new Evento(
                "Passar Mal", "A comida da cantina fez mal", TipoLocal.QUALQUER_LUGAR,
                CategoriaEvento.IMPREVISTO, RequisitoEvento.NENHUM, "icone.png", new ArrayList<>()
        );

        // Requisito NENHUM deve retornar true
        assertTrue(eventoImprevisto.verificarCondicao(jogo));
    }
}