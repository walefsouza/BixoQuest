package model.atividades;

import model.entidades.Jogador;
import model.mapa.Local;
import model.academico.Semestre;

public abstract class Evento extends Atividade {

    private int probabilidadeAtivacao;
    private CategoriaEvento categoria;

    public Evento(String nome, String descricao, Local localAtividade,
                  int impactoEnergia, int impactoConhecimento, int impactoMotivacao,
                  int impactoSaude, int impactoDesempenho, double impactoDinheiro,
                  int probabilidadeAtivacao, CategoriaEvento categoria) {

        super(nome, descricao, localAtividade,
                impactoEnergia, impactoConhecimento, impactoMotivacao,
                impactoSaude, impactoDesempenho, impactoDinheiro);

        this.probabilidadeAtivacao = probabilidadeAtivacao;
        this.categoria = categoria;
    }

    // getters

    public int getProbabilidadeAtivacao() {
        return probabilidadeAtivacao;
    }

    public CategoriaEvento getCategoria() {
        return categoria;
    }

    // setters

    public void setProbabilidadeAtivacao(int probabilidadeAtivacao) {
        this.probabilidadeAtivacao = probabilidadeAtivacao;
    }

    public void setCategoria(CategoriaEvento categoria) {
        this.categoria = categoria;
    }

    // métodos

    public abstract boolean verificarCondicao(Semestre s, Jogador j, int nAleatorio);

    public void executarEvento(Jogador j) {
        super.executar(j);
    }
}
