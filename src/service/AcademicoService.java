package service;

import model.atividades.Evento;
import model.Game;
import model.atividades.EventoAvaliacao;
import model.atividades.ResultadoAcao;
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

    // O jogador deve assistir as aulas das disciplinas durante o semestre
    public ResultadoAcao assistirAula(Game game, Disciplina disciplina) {

        Jogador jogador = game.getJogador();

        // Deve-se ter ao menos 10 pontos de energia para conseguir assistir uma aula
        if (jogador.getEnergia() < 10) {

            ResultadoAcao resultado = new ResultadoAcao("Você até tentou, mas dormiu  e perdeu a aula de " + disciplina.getNome() + "!");
            resultado.setTocarAudio("src/resources/atividades/audio/som-sem-energia.mp3");
            resultado.setEmbacarTela(true);
            return resultado;
        }

        disciplina.participarAula(jogador);
        jogador.decrementarEnergia(10);
        jogador.aumentarLevelConhecimento(5);
        jogador.aumentarDesempenhoAcademico(5);

        ResultadoAcao resultado = new ResultadoAcao("Você assistiu à aula de " + disciplina.getNome() + " e fez boas anotações.");
        resultado.setTocarAudio("src/resources/atividades/audio/som-teclado-digitando.mp3");
        return resultado;
    }

    // O jogador pode estudar para uma disciplina na sala de aula em prol do seu desempenho acadêmico
    public ResultadoAcao estudarDisciplina(Game game, Disciplina disciplina) {

        Jogador jogador = game.getJogador();

        // O jogador não pode estudar cansado
        if (jogador.getEnergia() < 15) {

            ResultadoAcao resultado = new ResultadoAcao("Sua cabeça dói. Você não consegue focar" +
                    " nos livros de " + disciplina.getNome() + ".");

            resultado.setTocarAudio("src/resources/atividades/audio/som-sem-energia.mp3");
            return resultado;
        }

        jogador.decrementarEnergia(15);
        jogador.aumentarMotivacao(5);
        jogador.aumentarDesempenhoAcademico(10);

        ResultadoAcao resultado = new ResultadoAcao("Você devorou os livros de " +
                disciplina.getNome() + "! Seu desempenho acadêmico e sua motivação aumentaram.");

        resultado.setTocarAudio("src/resources/atividades/audio/som-passar-página.mp3");

        return resultado;
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

    // Envia a disciplina e as respostas da prova realizada pelo jogador para correção
    public ResultadoAcao corrigirAvaliacao(Game game, Disciplina disciplina, List<Integer> respostasDoJogador) {

        EventoAvaliacao avaliacao = disciplina.getAvaliacao();
        int acertos = 0;

        // Loop de correção
        for (int i = 0; i < respostasDoJogador.size(); i++) {
            int respostaEscolhida = respostasDoJogador.get(i);
            acertos = avaliacao.corrigirResposta(i, respostaEscolhida);
        }

        ResultadoAcao resultadoFinal = avaliacao.executar(game);

        // Converte os acertos em nota de 0 a 10 para o peso da disciplina
        int nota = (acertos * 10);
        avaliacao.setNotaObtida(nota);
        disciplina.calcularFinal();

        // Anexa o texto da nota ao texto base do evento
        resultadoFinal.setTextoNarrativo(resultadoFinal.getTextoNarrativo() + "\n\nVocê acertou " + acertos
                + " de " + avaliacao.getPerguntas().size() + " perguntas. Sua nota foi: " + nota);

        return resultadoFinal;
    }

    // Lógica para avançar o semestre ao finalizar a semana de avaliações
    public ResultadoAcao avancarSemestre(Game jogoAtual, boolean timeskip) {

        Jogador jogador = jogoAtual.getJogador();
        Semestre semestreAtual = jogoAtual.getSemestre();
        ResultadoAcao resultado;

        // o timeskip pula semestres por escolha do jogador/admin
        if (timeskip) {

            semestreAtual.setConcluido(true);
            boolean temProximo = conduzirTransicao(jogoAtual, semestreAtual);

            if (temProximo) {
                resultado = new ResultadoAcao("Espertinho! Passando os semestres sem jogar?");
                resultado.setTocarAudio("src/resources/atividades/audio/som-swoosh-transicao.mp3");
            }

            else {
                resultado = new ResultadoAcao("INACREDITÁVEL! VOCÊ VENCEU A UEFS HACKEANDO A MATRIX!");
                resultado.setMudarImagemFundo("imagem_formatura.png");
                resultado.setTocarAudio("src/resources/atividades/audio/som-ihaaaaaa.mp3");
                jogoAtual.formarJogador();
            }

            return resultado;
        }

        boolean todasAprovadas = true;

        // se todas as disciplinas estiverem aprovadas...
        for (Disciplina d : semestreAtual.getDisciplinas()) {
            d.concluirDisciplina();

            if (!d.getAprovado()) {
                todasAprovadas = false;
            }
        }

        boolean desempenhoOk = jogador.getDesempenhoAcademico() >= 70;

        // Aprovando semestre
        if (todasAprovadas && desempenhoOk) {


            boolean proximo = conduzirTransicao(jogoAtual, semestreAtual);

            // Se houver próximo semestre
            if (proximo) {
                semestreAtual.setConcluido(true);
                resultado = new ResultadoAcao("Parabéns! Você sobreviveu ao Semestre " + semestreAtual.capturarNome() + "!");
                resultado.setTocarAudio("src/resources/atividades/audio/som-att-realizada.mp3");
            }

            // Se não, acontece a formatura do jogador
            else {
                resultado = new ResultadoAcao("INACREDITÁVEL! VOCÊ VENCEU A UEFS E SE FORMOU!");
                resultado.setMudarImagemFundo("imagem_formatura.png");
                resultado.setTocarAudio("src/resources/atividades/audio/som-ihaaaaaa.mp3");
                jogoAtual.formarJogador();
            }
        }

        // Reprovando semestre
        else {
            aplicarReprovacao(jogador, semestreAtual);
            resultado = new ResultadoAcao("Você rodou hahaha! Estude mais infeliz. Reprovado no Semestre " + semestreAtual.capturarNome() + ".");
            resultado.setTocarAudio("src/resources/atividades/audio/som-end-reprovado.mp3");
            resultado.setEscurecerTela(true);
        }

        return resultado;
    }

    // A reprovação diminui os atributos e reseta as disciplinas para refazer o semestre
    private void aplicarReprovacao(Jogador jogador, Semestre semestreAtual) {

        semestreAtual.setSemanaAtual(1);
        jogador.decrementarMotivacao(30);
        jogador.decrementarLevelConhecimento(30);
        jogador.decrementarDesempenhoAcademico(40);

        for (Disciplina d : semestreAtual.getDisciplinas()) {
            d.resetarDisciplina();
        }
    }

    // Avança para o próximo semestre e verifica se é o último para realizar a formatura
    private boolean conduzirTransicao(Game jogoAtual, Semestre semestreAtual) {

        int numeroAtual = Integer.parseInt(semestreAtual.capturarNome());
        String proximoId = String.valueOf(numeroAtual + 1);

        Semestre proximoSemestre = semestreRepository.buscar(proximoId);

        // Avançando para o próximo semestre
        if (proximoSemestre != null) {
            proximoSemestre.setSemanaAtual(1);
            jogoAtual.setSemestre(proximoSemestre);
            return true;
        }

        return false;
    }
}