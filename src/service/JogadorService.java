package service;

import model.atividades.ResultadoAcao;
import model.entidades.Jogador;
import model.entidades.Colega;

public class JogadorService {

    // Estudar com alguém inteligente pode fazer o estudo render
    public ResultadoAcao estudarComColega(Jogador jogador, Colega colega) {

        // Não é possível estudar com o colega quando está cansado
        if (jogador.getEnergia() < 10) {
            ResultadoAcao resultado = new ResultadoAcao("Você tentou estudar, mas está exausto demais para acompanhar o raciocínio de " + colega.getNome() + ".");
            resultado.setTocarAudio("/resources/atividades/audio/som-sem-energia.mp3");
            resultado.setSucesso(false);
            resultado.setTitulo("VOCÊ ATÉ TENTOU, MAS O SONO NÃO DEIXOU");
            return resultado;
        }

        int bonus = colega.calcularBonusDeEstudo();
        jogador.aumentarLevelConhecimento(5 + bonus);
        jogador.decrementarEnergia(10);

        ResultadoAcao resultado = new ResultadoAcao("Você estudou com " + colega.getNome() + " e rendeu bastante! (Conhecimento +" + (5 + bonus) + ")");
        resultado.setTocarAudio("/resources/atividades/audio/som-att-realizada.mp3");
        resultado.setSucesso(true);
        resultado.setTitulo("ESTUDO COM SUCESSO");
        return resultado;
    }

    // Se a pessoa estiver com mal humor, o impacto da conversa será ruim para a motivação
    public ResultadoAcao interagirSocialmente(Jogador jogador, Colega colega) {

        int impacto = colega.calcularImpactoConversa();
        ResultadoAcao resultado;

        // Se o impacto for positivo, soma a motivação do jogador
        if (impacto >= 0) {

            jogador.aumentarMotivacao(impacto);
            resultado = new ResultadoAcao("Você bateu um papo legal com " + colega.getNome() + ". Sua motivação aumentou!");
            resultado.setTocarAudio("/resources/atividades/audio/som-ihaaaaaa.mp3");
            resultado.setSucesso(true);
            resultado.setTitulo("CONVERSA BOA SÓ COM AMIGOS");
        }

        // Se o impacto for negativo, subtrai da motivação do jogador
        else {

            jogador.decrementarMotivacao(Math.abs(impacto));
            resultado = new ResultadoAcao(colega.getNome() + " só reclamou da vida. Isso sugou sua energia vital...");
            resultado.setEscurecerTela(true); // Efeito visual de tristeza/desmotivação
            resultado.setSucesso(false);
            resultado.setTitulo("ESSE CARA SÓ FALA COISA RUIM");
        }
        return resultado;
    }
}