package service;

import model.game.Game;
import model.entidades.Jogador;
import model.academico.Semestre;
import model.atividades.Task;
import model.atividades.Evento;
import repository.IRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AtividadeService {

    private IRepository<Task> taskRepository;
    private IRepository<Evento> eventoRepository;
    private Random random;

    public AtividadeService(IRepository<Task> taskRepository, IRepository<Evento> eventoRepository) {
        this.taskRepository = taskRepository;
        this.eventoRepository = eventoRepository;
        this.random = new Random();
    }

    public List<Task> escolherTasksDaSemana() {
        List<Task> todasAsTasks = taskRepository.listar();

        List<Task> copiaTasks = new ArrayList<>(todasAsTasks);
        Collections.shuffle(copiaTasks);

        int quantidade = Math.min(6, copiaTasks.size());
        return new ArrayList<>(copiaTasks.subList(0, quantidade));
    }

    public boolean executarTask(Task task, Jogador jogador) {

        int custo = task.getImpactoEnergia();

        if (custo < 0 && jogador.getEnergia() < Math.abs(custo)) {
            return false;
        }

        task.executar(jogador);

        return true;
    }

    public boolean processarEventosDoDia(Game jogoAtual) {

        int sorteioDoDia = random.nextInt(100);

        Jogador j = jogoAtual.getJogador();
        Semestre s = jogoAtual.getSemestre();

        for (Evento evento : eventoRepository.listar()) {
            if (evento.verificarCondicao(s, j, sorteioDoDia)) {
                evento.executar(j);
                return true;
            }
        }
        return false;
    }
}