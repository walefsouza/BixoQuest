package model.atividades;

import model.academico.Semestre;
import model.entidades.Jogador;
import model.mapa.Local;

public class EventoPassarMal extends Evento {

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public EventoPassarMal(String nome, String descricao, Local localAtividade,
                           int impactoEnergia, int impactoConhecimento, int impactoMotivacao,
                           int impactoSaude, int impactoDesempenho, double impactoDinheiro,
                           int probabilidadeAtivacao, CategoriaEvento categoria) {

        super(nome, descricao, localAtividade,
                impactoEnergia, impactoConhecimento, impactoMotivacao,
                impactoSaude, impactoDesempenho, impactoDinheiro,
                probabilidadeAtivacao, categoria);
    }

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - -

    @Override
    public boolean verificarCondicao(Semestre s, Jogador j, int nAleatorio) {
        return nAleatorio < getProbabilidadeAtivacao();
    }
}