package model.atividades;
import model.entidades.Jogador;
import model.mapa.Local;
import repository.IGeneralGetNome;

public abstract class Atividade implements IGeneralGetNome {

    private String nome;
    private String descricao;
    private Local localAtividade;

    private int impactoEnergia;
    private int impactoConhecimento;
    private int impactoMotivacao;
    private int impactoSaude;
    private int impactoDesempenho;
    private double impactoDinheiro;


    public Atividade (String nome,String descricao,Local localAtividade, int impactoEnergia,
        int impactoConhecimento, int impactoMotivacao, int impactoSaude, int impactoDesempenho, double impactoDinheiro ) {

        this.nome = nome;
        this.descricao = descricao;
        this.localAtividade = localAtividade;

        this.impactoEnergia = impactoEnergia;
        this.impactoConhecimento = impactoConhecimento;
        this.impactoMotivacao = impactoMotivacao;
        this.impactoSaude = impactoSaude;
        this.impactoDesempenho = impactoDesempenho;
        this.impactoDinheiro = impactoDinheiro;
    }

    // métodos

    public void executar(Jogador j) {

        // Energia
        if (impactoEnergia > 0)
            j.aumentarEnergia(impactoEnergia);
        else
            j.decrementarEnergia(Math.abs(impactoEnergia));

        // Conhecimento
        if (impactoConhecimento > 0)
            j.aumentarLevelConhecimento(impactoConhecimento);
        else
            j.decrementarLevelConhecimento(Math.abs(impactoConhecimento));

        // Motivação
        if (impactoMotivacao > 0)
            j.aumentarMotivacao(impactoMotivacao);
        else
            j.decrementarMotivacao(Math.abs(impactoMotivacao));

        // Saúde
        if (impactoSaude > 0)
            j.aumentarSaude(impactoSaude);
        else
            j.decrementarSaude(Math.abs(impactoSaude));

        // Desempenho
        if (impactoDesempenho > 0)
            j.aumentarDesempenhoAcademico(impactoDesempenho);
        else
            j.decrementarDesempenhoAcademico(Math.abs(impactoDesempenho));

        // Dinheiro (double)
        if (impactoDinheiro > 0)
            j.aumentarDinheiro(impactoDinheiro);
        else
            j.decrementarDinheiro(Math.abs(impactoDinheiro));
    }

    // setters

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public Local getLocalAtividade() {
        return localAtividade;
    }

    public int getImpactoEnergia(){
        return this.impactoEnergia;
    }

    // métodos

    @Override
    public String capturarNome() {
        return this.getNome();
    }


}
