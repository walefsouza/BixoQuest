package Model.Academico;

import Model.Atividades.EventoAvaliacao;
import Model.Entidades.Jogador;
import Model.Entidades.Professor;
import Model.Mapa.SalaDeAula;

public class Disciplina {

    private String nome;
    private Professor professor;
    private int frequencia;
    private SalaDeAula local;
    private double mediaFinal;
    private EventoAvaliacao avaliacao;
    private boolean aprovado;

    public Disciplina(String nome, Professor professor, int frequencia,
                      double mediaFinal, EventoAvaliacao avaliacao, SalaDeAula sala) {

        this.nome = nome;
        this.professor = professor;
        this.frequencia = frequencia;
        this.mediaFinal = mediaFinal;
        this.avaliacao = avaliacao;
        this.local = sala;
    }

    //setters

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public void setMediaFinal(double media){
        this.mediaFinal = media;
    }

    // getters

    public String getCodigoSala() {
        return local.getCodigoSala();
    }

    public Professor getProfessor() {
        return this.professor;
    }

    public int getFrequencia(){
        return this.frequencia;
    }

    public String getNome(){
        return this.nome;
    }

    public double getMediaFinal(){
        return this.mediaFinal;
    }

    public boolean getAprovado(){
        return this.aprovado;
    }

    // métodos

    public void participarAula(Jogador j){
        j.aumentarLevelConhecimento(5);
        this.frequencia+=1;
    }

    public void iniciarAvaliacao(Jogador j) {
        avaliacao.executarEvento(j);
    }

    public int calcularFinal() {
        int resultado = frequencia * 4 + avaliacao.getNotaObtida() * 6;
        this.mediaFinal = resultado;
        return resultado;
    }

    public void concluirDisciplina() {

        if (avaliacao.getRealizada() == true && calcularFinal() >= 5) {
            this.aprovado = true;
            return;
        }

        this.aprovado = false;
    }

}
