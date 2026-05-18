package service;

import model.Game;
import model.academico.Semestre;
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

    // Mudança de Local
    public boolean viajar(Game game, Local destino) {

        Jogador jogador = game.getJogador();

        int custoViagem = 5;

        if (jogador.getEnergia() >= custoViagem) {
            jogador.decrementarEnergia(custoViagem);
            jogador.mudarLocal(destino);
            verificarEventoLocal(game, destino);
            return true;
        }

        // Se o jogador estiver sem energia, o único lugar que pode ir é o checkpoint semanal

        else {
            if (destino instanceof PontoDeOnibus) {
                jogador.decrementarEnergia(jogador.getEnergia());
                jogador.mudarLocal(destino);
                return true;
            }

            return false;
        }
    }

    // Verificar algum evento acontece no local que o jogador viajou
    private void verificarEventoLocal(Game game, Local destino) {

        Jogador j = game.getJogador();

        for (Evento evento : destino.getEventos()) {
            if (evento.verificarCondicao(game)) {
                evento.executar(game);
                return;
            }
        }
    }

    //  Comprar Lanche na Cantina

    public boolean comprarLanche(Game game, Local localAtual, Cardapio lanche) {

        Jogador jogador = game.getJogador();

        if (!(localAtual instanceof Cantina)) {
            return false;
        }

        if (jogador.getDinheiro() < lanche.getPreco()) {
            return false;
        }

        Cantina cantina = (Cantina) localAtual;
        int fila = cantina.getTamanhoFila();

        // A fila gasta energia do jogador
        if (fila > 0) {
            jogador.decrementarEnergia(fila);
            cantina.setTamanhoFila(fila - 1);
        }

        // Se não tiver fila ele recebe um bônus
        else {
            jogador.aumentarEnergia(5);
        }

        jogador.decrementarDinheiro(lanche.getPreco());
        jogador.aumentarSaude(10);

        return true;
    }

    // Estudar no laboratório faz com que você ganhe muito conhecimento e perca energia

    public boolean usarComputadorLab(Jogador jogador, Local localAtual) {

        if (!(localAtual instanceof Laboratorio)) {
            return false;
        }

        Laboratorio lab = (Laboratorio) localAtual;

        if (jogador.getEnergia() < 15) {
            return false;
        }

        if (lab.getComputadoresDisponiveis() <= 0) {
            return false;
        }

        lab.setComputadoresDisponiveis(lab.getComputadoresDisponiveis() - 1);
        jogador.decrementarEnergia(15);
        double ganho = 10.0 * lab.getMultiplicadorEstudo(); // bôns de estudo do laboratório
        jogador.aumentarLevelConhecimento((int) ganho);

        return true;
    }

    // O jogador usa o colegiado para resolver burocracias, isso cansa muito!

    public boolean resolverBurocracia(Jogador jogador, Local localAtual) {
        if (!(localAtual instanceof Colegiado)) {
            return false;
        }

        Colegiado colegiado = (Colegiado) localAtual;

        if (jogador.getEnergia() < 10) {
            return false;
        }

        jogador.decrementarEnergia(10);

        // Se o sistema estiver funcionando, ele ganha um bônus de conhecimento pela perda de energia
        if (colegiado.verificarAtendimento()) {
            jogador.aumentarLevelConhecimento(5);
            return true;
        }

        return false;
    }

    // O jogador pode escolher desistir da semana, zerando suas energias e fazendo avanças sem
    // desenvolver seus atributos. Isso será uma escolha e ele lida com as consequências sozinho

    public boolean tentarEmbarcar(Jogador jogador, Local localAtual, boolean desistiuDaSemana) {

        if (!(localAtual instanceof PontoDeOnibus)) {
            return false;
        }

        PontoDeOnibus ponto = (PontoDeOnibus) localAtual;

        if (desistiuDaSemana) {
            return true;
        }

        if (ponto.onibusEstaNoPonto(jogador.getEnergia())) {
            return true;
        }

        jogador.decrementarEnergia(10);

        return ponto.onibusEstaNoPonto(jogador.getEnergia());
    }

    // Repositório de Locais - - - - - - - - - - - - - - - - - - - - - - - -

    public List<Local> listarLocais() {
        return this.localRepository.listar();
    }

    public Local buscarLocal(String nome) {
        return this.localRepository.buscar(nome);
    }


}