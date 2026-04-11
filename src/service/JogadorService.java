package service;

import model.entidades.Jogador;
import model.entidades.Colega;

public class JogadorService {


    public boolean estudarComColega(Jogador jogador, Colega colega) {

        if (jogador.getEnergia() < 10) return false;

        int bonus = colega.calcularBonusDeEstudo();
        jogador.aumentarLevelConhecimento(10 + bonus);
        jogador.decrementarEnergia(10);

        return true;
    }

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