package Model.Academico;

import Model.Atividades.Task;
import Model.Entidades.Jogador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Semestre {

    private int numero;
    private List<Disciplina> disciplinas;
    private boolean concluido;
    private int semanaAtual;
    private int semanaMax;
    private List<Task> bancoTasks;

    // construtor
    public Semestre (int numero) {
        this.numero = numero;
        this.semanaMax = 4;

        this.semanaAtual = 1;
        this.concluido = false;

        this.disciplinas = new ArrayList<>();
        this.bancoTasks = new ArrayList<>();
    }

    // métodos
    public boolean avancarSemestre(Jogador j, boolean timeskip) {

        //timeskip ignora todas as condições
        if (timeskip) {
            this.concluido = true;
            return true;
        }

        //precisa cumprir os requisitos
        boolean todasAprovadas = true;

        for (Disciplina d : disciplinas) {
            d.concluirDisciplina();
            if (!d.getAprovado()) {
                todasAprovadas = false;
            }
        }

        boolean desempenhoOk = j.getDesempenhoAcademico() >= 70;

        if (todasAprovadas && desempenhoOk) {
            this.concluido = true;
            return true;
        }

        this.concluido = false;
        return false;
    }

    public void avancarSemana(Jogador j) {

        if (semanaAtual < semanaMax) {
            semanaAtual++;
            gerarTasksDaSemana();
            return;
        }

        if (semanaAtual == semanaMax) {
            for (Disciplina d : disciplinas) {
                d.iniciarAvaliacao(j);
            }
        }
    }

    public List<Task> gerarTasksDaSemana() {

        int quantidade = Math.min(2, bancoTasks.size());

        Collections.shuffle(bancoTasks);

        return new ArrayList<>(bancoTasks.subList(0, quantidade));
    }

    // getters

    public int getNumero() {
        return this.numero;
    }

    public List<Disciplina> getDisciplinas() {
        return this.disciplinas;
    }

    public boolean getConcluido() {
        return this.concluido;
    }

    public int getSemanaAtual() {
        return this.semanaAtual;
    }

    public int getSemanaMax() {
        return this.semanaMax;
    }

    public List<Task> getBancoTasks() {
        return this.bancoTasks;
    }

    // setters

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    public void setSemanaAtual(int s) {
        this.semanaAtual = s;
    }

    public void setSemanaMax(int s) {
        this.semanaMax = s;
    }

    public void setBancoTasks(List<Task> t) {
        this.bancoTasks = t;
    }
}
