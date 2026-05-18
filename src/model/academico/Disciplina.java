package model.academico;

import model.atividades.EventoAvaliacao;
import model.entidades.Jogador;
import model.entidades.Professor;
import model.mapa.Local;
import model.mapa.SalaDeAula;
import model.mapa.TipoLocal;
import repository.IGeneralGetNome;

public class Disciplina implements IGeneralGetNome {

    private String nome;
    private Professor professor;
    private int frequencia;
    private TipoLocal local;
    private int mediaFinal;
    private EventoAvaliacao avaliacao;
    private boolean aprovado;
    private String icone;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public Disciplina(String nome, Professor professor, String icone,
                      EventoAvaliacao avaliacao, TipoLocal sala) {

        this.nome = nome;
        this.professor = professor;
        this.frequencia = 0;
        this.mediaFinal = 0;
        this.avaliacao = avaliacao;
        this.local = sala;
        this.icone = icone;
    }

    // Setters  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    // Getters  - - - - - - - - - - - - - - - - - - - - - - - -

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

    public String getIcone(){
        return this.icone;
    }

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - -

    @Override
    public String capturarNome() {
        return this.getNome();
    }

    // Participar da aula para aumentar frequência
    public void participarAula(Jogador j) {
        j.aumentarLevelConhecimento(5);
        this.frequencia += 1;

        // Se houver um professor, incrementa pontos de credibilidade

        if (this.professor != null) {
            this.professor.aumentarCredibilidade(this.professor.getCredibilidade() + 2);
        }
    }

    // Calcula a média final da disciplina com base na frequência com peso 10 e avaliação com peso 6
    public int calcularFinal() {
        int somaPesos = this.frequencia * 10+avaliacao.getNotaObtida() * 6;
        this.mediaFinal = somaPesos/10; // o máximo da soma dos pesos é 100, isso resulta em 0 <= x <= 10
        return this.mediaFinal;
    }

    // Concluir disciplina se estiver aprovado
    public void concluirDisciplina() {

        if (avaliacao.getRealizada() == true && calcularFinal() >= 5) {
            this.aprovado = true;
            return;
        }

        this.aprovado = false;
    }

    // Reseta a disciplina em caso de recomeçar o semestre
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
