package model.mapa;

import model.atividades.Evento;
import model.atividades.Task;
import model.entidades.Entidade;
import repository.IGeneralGetNome;

import java.util.List;

public abstract class Local implements IGeneralGetNome {

    private String nome;
    private List<Task> tasks;
    private List<Evento> eventos;
    private String descricao;
    private List<Entidade> entidades;

    public Local (String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    // setters

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public void setEntidades(List<Entidade> entidades) {
        this.entidades = entidades;
    }

    // getters

    public String getNome(){
        return this.nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public List<Evento> getEventos() {
        return this.eventos;
    }

    public List<Entidade> getEntidades() {
        return this.entidades;
    }

    public abstract TipoLocal getTipo();

    // métodos

    //public abstract Entidade chamarEntidade();

    @Override
    public String capturarNome() {
        return this.getNome();
    }

    public void adicionarTask(Task t) {
        if (tasks != null)
            tasks.add(t);
    }

    public void removerTask(Task t) {
        if (tasks != null)
            tasks.remove(t);
    }

    public void adicionarEvento(Evento e) {
        if (eventos != null)
            eventos.add(e);
    }

    public void removerEvento(Evento e) {
        if (eventos != null)
            eventos.remove(e);
    }


}
