package Model.Atividades;

import Model.Entidades.Jogador;
import Model.Mapa.Local;

public class Task extends Atividade {

    private boolean realizada;
    private String categoria;

    public Task(String nome, String descricao, Local localAtividade, int impactoEnergia,
                int impactoConhecimento, int impactoMotivacao, int impactoSaude,
                int impactoDesempenho, double impactoDinheiro, boolean realizada, String categoria) {

        super(nome, descricao, localAtividade, impactoEnergia, impactoConhecimento,
                impactoMotivacao, impactoSaude, impactoDesempenho, impactoDinheiro);
        this.realizada = realizada;
        this.categoria = categoria;
    }

    // getters

    public String getCategoria() {
        return this.categoria;
    }

    public boolean getRealizada() {
        return this.realizada;
    }

    // setters

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setRealizada(boolean realizada) {
        this.realizada = realizada;
    }

    // métodos

    public void executarTask(Jogador j) {
        super.executar(j);
        setRealizada(true);
    }
}
