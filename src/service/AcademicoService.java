package service;

import model.atividades.Evento;
import model.Game;
import model.atividades.EventoAvaliacao;
import model.entidades.Jogador;
import model.academico.Semestre;
import model.academico.Disciplina;
import repository.IRepository;

import java.util.ArrayList;
import java.util.List;

public class AcademicoService {

    private IRepository<Semestre> semestreRepository;
    private IRepository<Evento> eventoRepository;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public AcademicoService(IRepository<Semestre> semestreRepository, IRepository<Evento> eventoRepository) {
        this.semestreRepository = semestreRepository;
        this.eventoRepository = eventoRepository;
    }

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - -

    public boolean assistirAula(Jogador jogador, Disciplina disciplina) {
        if (jogador.getEnergia() < 10) {
            return false; // não pode assitir aula cansado
        }

        disciplina.participarAula(jogador);
        jogador.decrementarEnergia(10);

        return true;
    }

    // se chegar a última semana do semestre, dispara o evento de avaliações e retorna
    // a lista de provas que o jogador precisa fazer
    public List<EventoAvaliacao> verificarInicioAvaliacoes(Semestre semestre) {

        List<EventoAvaliacao> avaliacoesDaSemana = new ArrayList<>();

        // Se for a semana de provas...
        if (semestre.getSemanaAtual() == semestre.getSemanaMax()) {

            // Pega a prova de cada disciplina e coloca na lista
            for (Disciplina d : semestre.getDisciplinas()) {
                avaliacoesDaSemana.add(d.getAvaliacao());
            }
        }

        // Devolve a lista. Se não for semana de prova, devolve uma lista vazia.
        return avaliacoesDaSemana;
    }
    public boolean avancarSemestre(Game jogoAtual, boolean timeskip) {

        Jogador jogador = jogoAtual.getJogador();
        Semestre semestreAtual = jogoAtual.getSemestre();

        // o timeskip pula semestres por escolha do jogador/admin
        if (timeskip) {
            conduzirTransicao(jogoAtual, semestreAtual);
            return true;
        }

        boolean todasAprovadas = true;

        // se todas as diciplinas estiverem aprovadas...
        for (Disciplina d : semestreAtual.getDisciplinas()) {
            d.concluirDisciplina();

            if (!d.getAprovado()) {
                todasAprovadas = false;
            }
        }

        boolean desempenhoOk = jogador.getDesempenhoAcademico() >= 70;

        // Aprovando semestre
        if (todasAprovadas && desempenhoOk) {
            conduzirTransicao(jogoAtual, semestreAtual);
            semestreAtual.setConcluido(true);
            return true;
        }

        // Reprovando semestre
        else {
            aplicarReprovacao(jogador, semestreAtual);
            return false;
        }
    }

    // A reprovação diminui os atributos e reseta as diciplinas para refazer o semestre
    private void aplicarReprovacao(Jogador jogador, Semestre semestreAtual) {

        semestreAtual.setSemanaAtual(1);
        jogador.decrementarMotivacao(30);
        jogador.decrementarLevelConhecimento(30);
        jogador.decrementarDesempenhoAcademico(40);

        for (Disciplina d : semestreAtual.getDisciplinas()) {
            d.resetarDisciplina();
        }
    }

    // Avança para o próximo semestre e verifica se é p último para realziar a formatura
    private void conduzirTransicao(Game jogoAtual, Semestre semestreAtual) {

        int numeroAtual = Integer.parseInt(semestreAtual.capturarNome());
        String proximoId = String.valueOf(numeroAtual + 1);

        Semestre proximoSemestre = semestreRepository.buscar(proximoId);

        // Avançando para o próximo semestre
        if (proximoSemestre != null) {
            proximoSemestre.setSemanaAtual(1);
            jogoAtual.setSemestre(proximoSemestre);
        }

        // Se não houver próximo semestre, o jogo acabou.
        /*else {
            Evento formatura = eventoRepository.buscar("Formatura");

            if (formatura != null) {
                formatura.executar(jogoAtual.getJogador());
            }
            jogoAtual.formarJogador();
        }*/
    }
}