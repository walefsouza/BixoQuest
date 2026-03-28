package Model.Atividades;
import Model.Mapa.Local;

public class Atividade {

    private String nome;
    private String descricao;
    private int progressoAtual;
    private int progressoMax;
    private Local localAtividade;

    public Atividade (String nome,String descricao, int progressoAtual, int progressoMax,Local localAtividade) {
        this.nome = nome;
        this.descricao = descricao;
        this.progressoAtual = progressoAtual;
        this.progressoMax = progressoMax;
        this.localAtividade = localAtividade;
    }

    // métodos

    /*PENSAR EM COMO INICIAR O EVENTO*/


    // setters

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setProgressoAtual(int progressoAtual) {
        this.progressoAtual = progressoAtual;
    }

    public void setProgressoMax(int progressoMax) {
        this.progressoMax = progressoMax;
    }

    public void setLocalAtividade(Local localAtividade) {
        this.localAtividade = localAtividade;
    }

    // getters

    public String getNome() {
        return this.nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public int getProgressoAtual() {
        return progressoAtual;
    }

    public int getProgressoMax() {
        return progressoMax;
    }

    public Local getLocalAtividade() {
        return localAtividade;
    }
}
