package Model.Atividades;

import Model.Entidades.Jogador;
import Model.Mapa.Local;

import java.util.List;

public class Avaliacao extends Evento {

    private List<Pergunta> perguntas;
    private int notaMaxima;
    private int notaObtida;
    private boolean realizada;

    public Avaliacao(String nome, String descricao, Local localAtividade,
                     int impactoEnergia, int impactoConhecimento, int impactoMotivacao,
                     int impactoSaude, int impactoDesempenho, double impactoDinheiro,
                     int probabilidadeAtivacao, boolean condicaoAtivacao,
                     int dificuldade, String categoria,
                     List<Pergunta> perguntas, int notaMaxima) {

        super(nome, descricao, localAtividade,
                impactoEnergia, impactoConhecimento, impactoMotivacao,
                impactoSaude, impactoDesempenho, impactoDinheiro,
                probabilidadeAtivacao, condicaoAtivacao, dificuldade, categoria);

        this.perguntas = perguntas;
        this.notaMaxima = notaMaxima;
        this.notaObtida = 0;
        this.realizada = false;
    }

    // setters

    public void setPerguntas(List<Pergunta> perguntas) {
        this.perguntas = perguntas;
    }

    public void setNotaMaxima(int notaMaxima) {
        this.notaMaxima = notaMaxima;
    }

    public void setNotaObtida(int notaObtida) {
        this.notaObtida = notaObtida;
    }

    public void setRealizada(boolean realizada) {
        this.realizada = realizada;
    }

    // getters

    public List<Pergunta> getPerguntas() {
        return this.perguntas;
    }

    public int getNotaMaxima() {
        return this.notaMaxima;
    }

    public int getNotaObtida() {
        return this.notaObtida;
    }

    public boolean getRealizada() {
        return this.realizada;
    }

    // métodos

    public int corrigirResposta(int indicePergunta, int resposta) {
        if (indicePergunta >= 0 && indicePergunta < perguntas.size()) {
            return getNotaObtida();
        }

        Pergunta p = perguntas.get(indicePergunta);

        if (p.verificarResposta(resposta)) {
            setNotaObtida(getNotaObtida() + 1);
        }

        return getNotaObtida();
    }

    @Override
    public void executarEvento(Jogador j) {
        super.executarEvento(j);
        setRealizada(true);
    }

}
