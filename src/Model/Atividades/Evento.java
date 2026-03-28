package Model.Atividades;

import Model.Mapa.Local;

public class Evento extends Atividade{

    private int probalidadeAtivacao;
    private int dificuldade;
    private boolean condicaoAtivacao;
    private String categoria;

    public Evento(String nome, String descricao, int progressoAtual, int progressoMax, Local localAtividade,
                  int probalidadeAtivacao, int dificuldade, boolean condicaoAtivacao, String categoria) {

        super(nome, descricao, progressoAtual, progressoMax, localAtividade);
        this.probalidadeAtivacao = probalidadeAtivacao;
        this.dificuldade = dificuldade;
        this.condicaoAtivacao = condicaoAtivacao;
        this.categoria = categoria;
    }

    // setters

    public void setProbalidadeAtivacao(int probalidadeAtivacao) {
        this.probalidadeAtivacao = probalidadeAtivacao;
    }

    public void setDificuldade(int dificuldade) {
        this.dificuldade = dificuldade;
    }

    public void setCondicaoAtivacao(boolean condicaoAtivacao) {
        this.condicaoAtivacao = condicaoAtivacao;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // getters

    public boolean getCondicaoAtivacao() {
        return condicaoAtivacao;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getProbalidadeAtivacao() {
        return probalidadeAtivacao;
    }

    public int getDificuldade(){
        return dificuldade;
    }
}
