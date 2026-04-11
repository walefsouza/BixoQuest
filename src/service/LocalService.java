package service;

import model.academico.Semestre;
import model.game.Game;
import model.entidades.Jogador;
import model.academico.Disciplina;
import model.interacao.Dialogo;
import model.mapa.*;
import model.atividades.Evento;
import repository.IRepository;
import java.util.Random;

public class LocalService {

    private IRepository<Evento> eventoRepository;
    private IRepository<Dialogo> dialogoRepository;
    private Random random;

    // construtor

    public LocalService(IRepository<Evento> eventoRepository, IRepository<Dialogo> dialogoRepository) {
        this.eventoRepository = eventoRepository;
        this.dialogoRepository = dialogoRepository;
        this.random = new Random();
    }

    // Mudança de Local

    public boolean viajar(Semestre semestre, Jogador jogador, Local destino) {
        int custoViagem = 5;

        if (jogador.getEnergia() >= custoViagem) {
            jogador.decrementarEnergia(custoViagem);
            jogador.mudarLocal(destino);
            verificarEventoLocal(semestre, jogador, destino);
            return true;
        }

        else {
            if (destino instanceof PontoDeOnibus) {
                jogador.decrementarEnergia(jogador.getEnergia());
                jogador.mudarLocal(destino);
                return true;
            }

            return false;
        }
    }

    // verificar se um evento acontece no Local

    private void verificarEventoLocal(Semestre s, Jogador j, Local destino) {
        int sorteio = random.nextInt(100);

        for (Evento evento : destino.getEventos()) {
            if (evento.verificarCondicao(s, j, sorteio)) {
                evento.executar(j);
                return;
            }
        }
    }

    //  Comprar Lanche na Cantina

    public boolean comprarLanche(Semestre semestre, Jogador jogador, Local localAtual, Cardapio lanche) {

        if (!(localAtual instanceof Cantina)) {
            return false;
        }

        if (jogador.getDinheiro() < lanche.getPreco()) {
            return false;
        }

        Cantina cantina = (Cantina) localAtual;
        int fila = cantina.getTamanhoFila();

        if (fila > 0) {
            jogador.decrementarEnergia(fila);
            cantina.setTamanhoFila(fila - 1);
        }

        else {
            jogador.aumentarEnergia(5);
        }

        jogador.decrementarDinheiro(lanche.getPreco());
        jogador.aumentarSaude(10);

        // Possibilidade de passar mal se comer na cantina

        int sorteio = random.nextInt(100);
        Evento passarMal =  eventoRepository.buscar("Passar Mal");

        if (passarMal != null && passarMal.verificarCondicao(semestre, jogador, sorteio)) {
            passarMal.executar(jogador);
        }

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
        double ganho = 10.0 * lab.getMultiplicadorEstudo();
        jogador.aumentarLevelConhecimento((int) ganho);

        return true;
    }

    // colegiado

    public boolean resolverBurocracia(Jogador jogador, Local localAtual) {
        if (!(localAtual instanceof Colegiado)) return false;

        Colegiado colegiado = (Colegiado) localAtual;

        if (jogador.getEnergia() < 10) return false;

        jogador.decrementarEnergia(10);

        if (colegiado.verificarAtendimento()) {
            jogador.aumentarLevelConhecimento(5);
            return true;
        }

        return false;
    }

    // ponto de ônibus

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
}