package Model.Mapa;

import Model.Academico.Semestre;
import Model.Atividades.Evento;
import Model.Atividades.Pergunta;
import Model.Entidades.Jogador;

import java.util.List;
import java.util.Random;

public class EventoPassarMal extends Evento {

    public EventoPassarMal(String nome, String descricao, Local localAtividade,
                           int impactoEnergia, int impactoConhecimento, int impactoMotivacao,
                           int impactoSaude, int impactoDesempenho, double impactoDinheiro,
                           int probabilidadeAtivacao, String categoria) {

        super(nome, descricao, localAtividade,
                impactoEnergia, impactoConhecimento, impactoMotivacao,
                impactoSaude, impactoDesempenho, impactoDinheiro,
                probabilidadeAtivacao, categoria);
    }

    @Override
    public boolean verificarCondicao(Semestre s, Jogador j) {
        return new Random().nextInt(100) < getProbabilidadeAtivacao();
    }
}