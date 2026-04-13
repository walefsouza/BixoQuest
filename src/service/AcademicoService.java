package service;

import model.atividades.Evento;
import model.Game;
import model.entidades.Jogador;
import model.academico.Semestre;
import model.academico.Disciplina;
import repository.IRepository;

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

    // se chegar a última semana do semestre, dispara o evento de avaliações
    public void verificarInicioAvaliacoes(Semestre semestre, Jogador jogador) {
        if (semestre.getSemanaAtual() == semestre.getSemanaMax()) {
            for (Disciplina d : semestre.getDisciplinas()) {
                d.iniciarAvaliacao(jogador);
            }
        }
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
        else {
            Evento formatura = eventoRepository.buscar("Formatura");

            if (formatura != null) {
                formatura.executar(jogoAtual.getJogador());
            }
            jogoAtual.formarJogador();
        }
    }
}