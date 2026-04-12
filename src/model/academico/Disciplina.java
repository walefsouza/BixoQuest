package model.academico;

import model.atividades.EventoAvaliacao;
import model.entidades.Jogador;
import model.entidades.Professor;
import model.mapa.SalaDeAula;
import repository.IGeneralGetNome;

public class Disciplina implements IGeneralGetNome {

    private String nome;
    private Professor professor;
    private int frequencia;
    private SalaDeAula local;
    private int mediaFinal;
    private EventoAvaliacao avaliacao;
    private boolean aprovado;

    public Disciplina(String nome, Professor professor, int frequencia,
                      int mediaFinal, EventoAvaliacao avaliacao, SalaDeAula sala) {

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

    // getters

    public String getNomeSala() {
        return local.getNome();
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

    public int getMediaFinal(){
        return this.mediaFinal;
    }

    public boolean getAprovado(){
        return this.aprovado;
    }

    public EventoAvaliacao getAvaliacao(){
        return this.avaliacao;
    }

    // métodos

    @Override
    public String capturarNome() {
        return this.getNome();
    }

    public void participarAula(Jogador j) {
        j.aumentarLevelConhecimento(5);
        this.frequencia += 1;

        if (this.professor != null) {
            this.professor.setCredibilidade(this.professor.getCredibilidade() + 2);
        }
    }

    public void iniciarAvaliacao(Jogador j) {
        avaliacao.executarEvento(j);
    }

    public int calcularFinal() {
        int somaPesos = this.frequencia * 10+avaliacao.getNotaObtida() * 6;
        this.mediaFinal = somaPesos/10;
        return this.mediaFinal;
    }

    public void concluirDisciplina() {

        if (avaliacao.getRealizada() == true && calcularFinal() >= 5) {
            this.aprovado = true;
            return;
        }

        this.aprovado = false;
    }

    public void resetarDisciplina() {

        this.frequencia = 0;
        this.mediaFinal = 0;
        this.aprovado = false;

        if (this.avaliacao != null) {
            this.avaliacao.setRealizada(false);
            this.avaliacao.setNotaObtida(0);
        }
    }

}
