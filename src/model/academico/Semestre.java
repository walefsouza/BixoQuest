package model.academico;

import model.atividades.Task;
import repository.IGeneralGetNome;

import java.util.ArrayList;
import java.util.List;

public class Semestre implements IGeneralGetNome {

    private int numero;
    private List<Disciplina> disciplinas;
    private boolean concluido;
    private int semanaAtual;
    private int semanaMax;
    private List<Task> bancoTasks;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public Semestre (int numero) {
        this.numero = numero;
        this.semanaMax = 4;
        this.semanaAtual = 1;
        this.concluido = false;
        this.disciplinas = new ArrayList<>();
        this.bancoTasks = new ArrayList<>();
    }

    // Getters  - - - - - - - - - - - - - - - - - - - - - - - -

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

    // Setters  - - - - - - - - - - - - - - - - - - - - - - - -

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

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - -

    @Override
    public String capturarNome() {
        return String.valueOf(this.numero);
    }

    public void avancarSemana() {
        if (this.semanaAtual < this.semanaMax) {
            this.semanaAtual++;
        }
    }

}
