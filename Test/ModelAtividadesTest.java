import model.atividades.CategoriaEvento;
import model.atividades.EventoAvaliacao;
import model.atividades.EventoPassarMal;
import model.atividades.Pergunta;
import model.entidades.Jogador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ModelAtividadesTest {

    private Pergunta pergunta;
    private EventoAvaliacao avaliacao;
    private Jogador jogador;

    @BeforeEach
    public void setUp() {
        jogador = new Jogador("jogador",
                50, 50, 50, 50, 50,
                100.0, null);

        pergunta = new Pergunta(
                "Quanto é 2+2?",
                new String[]{"1", "2", "3", "4"},
                3
        );

        List<Pergunta> perguntas = new ArrayList<>();
        perguntas.add(pergunta);

        avaliacao = new EventoAvaliacao(
                "Prova", "TEXTO", null,
                -10, 0, 0,
                0, 10, 0.0,
                100, CategoriaEvento.OBRIGATORIO,
                perguntas, 10
        );
    }

    // Pergunta

    @Test
    public void verificarRespostaCorreta() {
        assertTrue(pergunta.verificarResposta(3));
    }

    @Test
    public void verificarRespostaFalsa() {
        assertFalse(pergunta.verificarResposta(1));
    }

    // EventoAvaliacao — corrigirResposta

    @Test
    public void corrigirRespostaIncrementarNota() {

        // resposta certa aumenta

        avaliacao.corrigirResposta(0, 3);
        assertEquals(1, avaliacao.getNotaObtida());

        //resposta errada não aumenta

        avaliacao.corrigirResposta(0, 1);
        assertEquals(1, avaliacao.getNotaObtida());
    }

    // EventoAvaliacao — executarEvento

    @Test
    public void executarEventoAvaliacaoRealizacao() {
        avaliacao.executarEvento(jogador);
        assertTrue(avaliacao.getRealizada());
        int energiaAntes = jogador.getEnergia();
        avaliacao.executarEvento(jogador);
        assertTrue(jogador.getEnergia() < energiaAntes);
    }

    // EventoPassarMal — verificarCondicao

    @Test
    public void verificarCondicaoSorteioMenorQueProbabilidade() {
        EventoPassarMal evento = new EventoPassarMal(
                "Passar Mal", "texto", null,
                0, 0, 0,
                -20, 0, 0.0,
                50, CategoriaEvento.IMPREVISTO
        );
        assertTrue(evento.verificarCondicao(null, jogador, 30));
    }

    @Test
    public void verificarCondicaoSorteioMaiorQueProbabilidade() {
        EventoPassarMal evento = new EventoPassarMal(
                "Passar Mal", "desc", null,
                0, 0, 0,
                -20, 0, 0.0,
                50, CategoriaEvento.IMPREVISTO
        );
        assertFalse(evento.verificarCondicao(null, jogador, 80));
    }
}