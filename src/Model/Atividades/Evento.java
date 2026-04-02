package Model.Atividades;

import Model.Entidades.Jogador;
import Model.Mapa.Local;
import Model.Academico.Semestre;

public abstract class Evento extends Atividade {

    private int probabilidadeAtivacao;
    private boolean condicaoAtivacao;
    private int dificuldade;
    private String categoria;

    public Evento(String nome, String descricao, Local localAtividade,
                  int impactoEnergia, int impactoConhecimento, int impactoMotivacao,
                  int impactoSaude, int impactoDesempenho, double impactoDinheiro,
                  int probabilidadeAtivacao, boolean condicaoAtivacao,
                  int dificuldade, String categoria) {

        super(nome, descricao, localAtividade,
                impactoEnergia, impactoConhecimento, impactoMotivacao,
                impactoSaude, impactoDesempenho, impactoDinheiro);

        this.probabilidadeAtivacao = probabilidadeAtivacao;
        this.condicaoAtivacao = condicaoAtivacao;
        this.dificuldade = dificuldade;
        this.categoria = categoria;
    }

    // getters

    public int getProbabilidadeAtivacao() {
        return probabilidadeAtivacao;
    }

    public boolean getCondicaoAtivacao() {
        return condicaoAtivacao;
    }

    public int getDificuldade() {
        return dificuldade;
    }

    public String getCategoria() {
        return categoria;
    }

    // setters

    public void setProbabilidadeAtivacao(int probabilidadeAtivacao) {
        this.probabilidadeAtivacao = probabilidadeAtivacao;
    }

    public void setCondicaoAtivacao(boolean condicaoAtivacao) {
        this.condicaoAtivacao = condicaoAtivacao;
    }

    public void setDificuldade(int dificuldade) {
        this.dificuldade = dificuldade;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // métodos

    public abstract boolean verificarCondicao(Semestre s, Jogador j);

    public void executarEvento(Jogador j) {
        super.executar(j);
    }
}
