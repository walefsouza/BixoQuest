package service;

import model.Game;
import model.atividades.ResultadoAcao;
import model.entidades.Jogador;
import model.interacao.Dialogo;
import model.mapa.*;
import model.atividades.Evento;
import repository.IRepository;

import java.util.List;
import java.util.Random;

public class LocalService {

    private IRepository<Evento> eventoRepository;
    private IRepository<Dialogo> dialogoRepository;
    private IRepository<Local> localRepository;
    private Random random;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public LocalService(IRepository<Evento> eventoRepository,
                        IRepository<Dialogo> dialogoRepository,
                        IRepository<Local> localRepository) {

        this.eventoRepository = eventoRepository;
        this.dialogoRepository = dialogoRepository;
        this.localRepository = localRepository;
        this.random = new Random();
    }

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - -

    // Navegação pelos locais do mapa
    public ResultadoAcao viajar(Game game, TipoLocal destino, AtividadeService atividadeService) {

        Jogador jogador = game.getJogador();
        int custoViagem = 5;

        // Se o jogador possui energia, ele poderá viajar e lidar com a possibilidade de ter um evento aleatório
        if (jogador.getEnergia() >= custoViagem) {

            jogador.decrementarEnergia(custoViagem);
            jogador.mudarLocal(destino);

            // Verifica se há algum evento aleatório ao jogador viajar para o local
            ResultadoAcao evento = atividadeService.processarEventosAleatorios(game, destino);

            if (evento != null) {
                evento.setTextoNarrativo("Você chegou em: " + destino.getLocalNome() + ", mas... " + evento.getTextoNarrativo());
                evento.setSucesso(true);
                return evento;
            }

            // Se não acontecer nenhum evento, o jogador viaja sem imprevistos.
            ResultadoAcao viagem = new ResultadoAcao("");
            viagem.setTocarAudio("/resources/atividades/audio/som-swoosh-transicao.mp3");
            viagem.setSucesso(true);
            return viagem;
        }

        // Se o jogador não possui energia, ele só pode viajar para o ponto de ônibus.
        else {

            if (destino == TipoLocal.PONTO_DE_ONIBUS) {

                jogador.decrementarEnergia(jogador.getEnergia()); // zera a energia
                jogador.mudarLocal(destino);

                ResultadoAcao resultado = new ResultadoAcao("Você se arrastou até o Ponto de Ônibus depois de um dia exaustante.");
                resultado.setTocarAudio("/resources/atividades/audio/som-sem-energia.mp3");
                resultado.setSucesso(true);
                return resultado;
            }

            // Se tentar ir pra qualquer outro lugar sem energia
            ResultadoAcao resultado = new ResultadoAcao("Você está exausto! O único lugar que consegue ir agora é o Ponto de Ônibus.");
            resultado.setTocarAudio("/resources/atividades/audio/som-buzina-onibus.mp3");
            resultado.setSucesso(false);
            return resultado;
        }
    }

    //  Comprar Lanche na Cantina
    public ResultadoAcao comprarLanche(Game game, Local localAtual, Cardapio lanche) {

        ResultadoAcao resultado;
        Jogador jogador = game.getJogador();

        // Verificando se o local realmente é uma cantina
        if (localAtual.getTipo() != TipoLocal.CANTINA) {
            resultado = new ResultadoAcao("Não há onde comprar lanches aqui!");
            resultado.setSucesso(false);
            return resultado;
        }

        // Verificando saldo do jogador
        if (jogador.getDinheiro() < lanche.getPreco()) {
            resultado = new ResultadoAcao("Você não tem dinheiro suficiente para o " + lanche.getNome() + ".");
            resultado.setTocarAudio("/resources/atividades/audio/som-sem-energia.mp3");
            resultado.setSucesso(false);
            return resultado;
        }

        Cantina cantina = (Cantina) localAtual;
        int fila = random.nextInt(5);
        cantina.setTamanhoFila(fila);

        // A fila gasta energia do jogador
        if (fila > 0) {
            jogador.decrementarEnergia(fila);
            cantina.setTamanhoFila(fila - 1);
            resultado = new ResultadoAcao("Você enfrentou uma fila terrível, " +
                    "perdeu energia, mas comprou: " + lanche.getNome() + "!");
            resultado.setSucesso(true);
        }

        // Se não tiver fila ele recebe um bônus
        else {
            jogador.aumentarEnergia(5);
            resultado = new ResultadoAcao("Sorte grande! A cantina estava vazia. " +
                    "Você comprou: " + lanche.getNome() + "!");
            resultado.setSucesso(true);
        }

        jogador.decrementarDinheiro(lanche.getPreco());
        jogador.aumentarSaude(5);
        resultado.setTocarAudio("/resources/atividades/audio/som-caixa-registradora.mp3");

        return resultado;
    }

    // Estudar no laboratório faz com que você ganhe muito conhecimento e perca energia
    // Estudar no laboratório faz com que você ganhe muito conhecimento e perca energia
    public ResultadoAcao usarComputadorLab(Game game, Local localAtual) {

        ResultadoAcao resultado;
        Jogador jogador = game.getJogador();

        if (localAtual.getTipo() != TipoLocal.LABORATORIO) {
            resultado = new ResultadoAcao("Não há computadores aqui.");
            resultado.setSucesso(false); // Adicionado
            return resultado;
        }

        Laboratorio lab = (Laboratorio) localAtual;

        if (jogador.getEnergia() < 15) {
            resultado = new ResultadoAcao("Você está exausto demais para olhar para uma tela agora.");
            resultado.setSucesso(false); // Adicionado
            return resultado;
        }

        if (lab.getComputadoresDisponiveis() <= 0) {
            resultado = new ResultadoAcao("Todos os computadores estão ocupados por pessoas jogando Rocket League.");
            resultado.setTocarAudio("/resources/atividades/audio/som-sem-energia.mp3");
            resultado.setSucesso(false); // Adicionado
            return resultado;
        }

        lab.setComputadoresDisponiveis(lab.getComputadoresDisponiveis() - 1);
        jogador.decrementarEnergia(15);

        double ganho = 10.0 * lab.getMultiplicadorEstudo(); // bônus de estudo do laboratório
        jogador.aumentarLevelConhecimento((int) ganho);

        resultado = new ResultadoAcao("Você conseguiu um PC! Rendeu bastante, mas seus olhos estão ardendo.");
        resultado.setTocarAudio("/resources/atividades/audio/som-teclado-digitando.mp3");
        resultado.setEmbacarTela(true);
        resultado.setSucesso(true); // Adicionado

        return resultado;
    }

    // O jogador usa o colegiado para resolver burocracias, isso cansa muito!
    public ResultadoAcao resolverBurocracia(Jogador jogador, Local localAtual) {

        if (localAtual.getTipo() != TipoLocal.COLEGIADO) {
            ResultadoAcao erroLocal = new ResultadoAcao("Não há como resolver burocracias sem Maeli!");
            erroLocal.setSucesso(false);
            return erroLocal;
        }

        Colegiado colegiado = (Colegiado) localAtual;

        if (jogador.getEnergia() < 10) {
            ResultadoAcao exausto = new ResultadoAcao("Você está exausto demais para resolver seu problema.");
            exausto.setTocarAudio("/resources/atividades/audio/som-sem-energia.mp3");
            exausto.setSucesso(false);
            return exausto;
        }

        jogador.decrementarEnergia(10);

        // Se o sistema estiver funcionando, ele ganha um bônus de conhecimento pela perda de energia
        int chanceSistema = random.nextInt(100);

        if (chanceSistema < 70) {
            jogador.aumentarLevelConhecimento(10);
            ResultadoAcao sucesso = new ResultadoAcao("Você conseguiu ser atendido por Maeli e recebeu 10 pontos de conhecimento pelo empenho!");
            sucesso.setTocarAudio("/resources/atividades/audio/som-att-realizada.mp3");
            sucesso.setSucesso(true);
            return sucesso;
        }
        // Se o sistema cair, retorna mensagem de erro
        else {
            ResultadoAcao erro = new ResultadoAcao("Não foi dessa vez... o sistema do colegiado CAIU! Volte depois.");
            erro.setEscurecerTela(true);
            erro.setTremerTela(true);
            erro.setTocarAudio("/resources/atividades/audio/som-erro-sistema.mp3");
            erro.setSucesso(false);
            return erro;
        }
    }

    // Mecânica de apostas no Borogodó
    public ResultadoAcao apostarNoBorogodo(Jogador jogador, Local localAtual, double valorAposta) {

        if (localAtual.getTipo() != TipoLocal.BOROGODO) {
            ResultadoAcao erroLocal = new ResultadoAcao("Shhh! Não procure apostadores fora do Borogodó.");
            erroLocal.setSucesso(false);
            erroLocal.setTitulo("LOCAL INCORRETO");
            return erroLocal;
        }

        //double valorAposta = 10.0;

        if (jogador.getDinheiro() < valorAposta) {
            ResultadoAcao semDinheiro = new ResultadoAcao("Os veteranos não deixam você jogar fiado. Faltou dinheiro!");
            semDinheiro.setSucesso(false);
            semDinheiro.setTitulo("A POBREZA FALA MAIS ALTO");
            return semDinheiro;
        }

        jogador.decrementarDinheiro(valorAposta);

        // Você tem 30% de chance de ganhar a aposta
        int sorteio = random.nextInt(100);

        if (sorteio < 30) {
            jogador.aumentarDinheiro(valorAposta * 2); // Ganha o dobro
            jogador.aumentarMotivacao(10);

            ResultadoAcao vitoria = new ResultadoAcao("SHOW! Você ganhou R$ " + valorAposta*2 +" na aposta!");
            vitoria.setTocarAudio("/resources/atividades/audio/som-caixa-registradora.mp3"); // Caminho ajustado sem o "src"
            vitoria.setSucesso(true);
            vitoria.setTitulo("EAE CAMPEÃO. PARABÉNS!");
            return vitoria;
        }
        // Aqui você perde a aposta
        else {
            jogador.decrementarMotivacao(10);

            ResultadoAcao derrota = new ResultadoAcao("LAZARENTO! Você perdeu R$ "+ valorAposta +" no jogo. Que vergonha...");
            derrota.setSucesso(false); // Derrota no jogo reflete como alerta na interface
            derrota.setTitulo("TINHA QUE SER VOCÊ");
            return derrota;
        }
    }

    // O jogador pode escolher desistir da semana, zerando suas energias e fazendo avanças sem
    // desenvolver seus atributos. Isso será uma escolha e ele lida com as consequências sozinho
    public ResultadoAcao tentarEmbarcar(Game game, Local localAtual, boolean desistiuDaSemana) {

        Jogador jogador = game.getJogador();

        if (localAtual.getTipo() != TipoLocal.PONTO_DE_ONIBUS) {
            return new ResultadoAcao("Você precisa estar no Ponto de Ônibus para tentar embarcar.");
        }

        PontoDeOnibus ponto = (PontoDeOnibus) localAtual;

        // Zera a energia do jogador como consequência da desistência
        if (desistiuDaSemana) {

            jogador.decrementarEnergia(jogador.getEnergia());

            game.setFlagSemana(true);

            ResultadoAcao desistencia = new ResultadoAcao("Você não aguentou a pressão, desistiu da semana e foi para casa...");
            desistencia.setEscurecerTela(true);
            desistencia.setTocarAudio("/resources/atividades/audio/som-fim-acabou.mp3");

            return desistencia;
        }

        // Se o jogador for tentar embarbar no ônibus e tiver menos de 20 de energia
        if (ponto.onibusEstaNoPonto(jogador.getEnergia())) {

            game.setFlagSemana(true);

            ResultadoAcao sucesso = new ResultadoAcao("O ônibus estava no ponto! Você embarcou e foi para casa.");
            sucesso.setEscurecerTela(true);
            sucesso.setTocarAudio("/resources/atividades/audio/som-buzina-onibus.mp3");
            return sucesso;
        }

        // Se não estava no ponto, perde energia
        jogador.decrementarEnergia(10);

        // Automaticamente, é verificado se o jogador pode entrar no ônibus
        if (ponto.onibusEstaNoPonto(jogador.getEnergia())) {

            game.setFlagSemana(true);

            ResultadoAcao sucessoAtraso = new ResultadoAcao("O ônibus demorou, você gastou 10 de energia esperando, mas conseguiu embarcar.");
            sucessoAtraso.setEscurecerTela(true);
            sucessoAtraso.setTocarAudio("/resources/atividades/audio/som-buzina-onibus.mp3");
            return sucessoAtraso;
        }

        // Falhou em tudo
        ResultadoAcao falha = new ResultadoAcao("O ônibus não passou. Você gastou 10 de energia esperando em pé. Tente de novo.");
        falha.setTocarAudio("/resources/atividades/audio/som-fim-acabou.mp3");

        return falha;
    }

    // Repositório de Locais - - - - - - - - - - - - - - - - - - - - - - - -

    public List<Local> listarLocais() {
        return this.localRepository.listar();
    }

    public Local buscarLocal(String nome) {
        return this.localRepository.buscar(nome);
    }


}