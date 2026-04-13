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

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public Disciplina(String nome, Professor professor, int frequencia,
                      int mediaFinal, EventoAvaliacao avaliacao, SalaDeAula sala) {

        this.nome = nome;
        this.professor = professor;
        this.frequencia = frequencia;
        this.mediaFinal = mediaFinal;
        this.avaliacao = avaliacao;
        this.local = sala;
    }

    // Setters  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    // Getters  - - - - - - - - - - - - - - - - - - - - - - - -

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

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - -

    @Override
    public String capturarNome() {
        return this.getNome();
    }

    public void participarAula(Jogador j) {
        j.aumentarLevelConhecimento(5);
        this.frequencia += 1;

        // se houver um professor, incrementa pontos de credibilidade
        if (this.professor != null) {
            this.professor.setCredibilidade(this.professor.getCredibilidade() + 2);
        }
    }

    public void iniciarAvaliacao(Jogador j) {
        avaliacao.executarEvento(j);
    }

    // Calcula a média final da disciplina com base na frequência com peso 10 e avaliação com peso 6
    public int calcularFinal() {
        int somaPesos = this.frequencia * 10+avaliacao.getNotaObtida() * 6;
        this.mediaFinal = somaPesos/10; // o máximo da soma dos pesos é 100, isso resulta em 0 <= x <= 10
        return this.mediaFinal;
    }

    public void concluirDisciplina() {

        if (avaliacao.getRealizada() == true && calcularFinal() >= 5) {
            this.aprovado = true;
            return;
        }

        this.aprovado = false;
    }

    // reinicia a disciplina se for necessário
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
