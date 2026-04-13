package service;

import model.entidades.Jogador;
import model.entidades.Colega;

public class JogadorService {

    // Estudar com alguém inteligente pode fazer o estudo render
    public boolean estudarComColega(Jogador jogador, Colega colega) {

        if (jogador.getEnergia() < 10) {
            return false;
        }

        int bonus = colega.calcularBonusDeEstudo();
        jogador.aumentarLevelConhecimento(10 + bonus);
        jogador.decrementarEnergia(10);

        return true;
    }

    // Se a pessoa estiver com mal humor, o impacto da conversa será ruim para a motivação
    public boolean interagirSocialmente(Jogador jogador, Colega colega) {

        int impacto = colega.calcularImpactoConversa();

        if (impacto > 0) {
            jogador.aumentarMotivacao(impacto);
            return true;
        } else {
            jogador.decrementarMotivacao(Math.abs(impacto));
            return false;
        }
    }
}