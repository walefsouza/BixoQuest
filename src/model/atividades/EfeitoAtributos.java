package model.atividades;

import model.Game;
import model.entidades.Jogador;

public class EfeitoAtributos implements Efeito {

    // Atributos - - - - - - - - - - - - - - - - - - - - - - - - -

    private int impactoEnergia;
    private int impactoConhecimento;
    private int impactoMotivacao;
    private int impactoSaude;
    private int impactoDesempenho;
    private double impactoDinheiro;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public EfeitoAtributos(int energia, int conhecimento,
                           int motivacao, int saude,
                           int desempenho, double dinheiro) {

        this.impactoEnergia = energia;
        this.impactoConhecimento = conhecimento;
        this.impactoMotivacao = motivacao;
        this.impactoSaude = saude;
        this.impactoDesempenho = desempenho;
        this.impactoDinheiro = dinheiro;
    }

    // Executor  - - - - - - - - - - - - - - - - - - - - - - - -

    @Override
    public void aplicar(Game jogoAtual, ResultadoAcao resultado) {

        Jogador jogador = jogoAtual.getJogador();

        // Energia - - - - - - - - - - - - - - - - - - - - - - - - - -

        if (impactoEnergia > 0) {
            jogador.aumentarEnergia(impactoEnergia);
        }

        else if (impactoEnergia < 0) {
            jogador.decrementarEnergia(Math.abs(impactoEnergia));
        }

        // Conhecimento - - - - - - - - - - - - - - - - - - - - - - - - - -

        if (impactoConhecimento > 0) {
            jogador.aumentarLevelConhecimento(impactoConhecimento);
        }

        else if (impactoConhecimento < 0) {
            jogador.decrementarLevelConhecimento(Math.abs(impactoConhecimento));
        }

        // Motivação - - - - - - - - - - - - - - - - - - - - - - - - - -

        if (impactoMotivacao > 0) {
            jogador.aumentarMotivacao(impactoMotivacao);
        }

        else if (impactoMotivacao < 0) {
            jogador.decrementarMotivacao(Math.abs(impactoMotivacao));
        }

        // Saúde - - - - - - - - - - - - - - - - - - - - - - - - - -

        if (impactoSaude > 0) {
            jogador.aumentarSaude(impactoSaude);
        }

        else if (impactoSaude < 0) {
            jogador.decrementarSaude(Math.abs(impactoSaude));
        }

        // Desempenho Acadêmico - - - - - - - - - - - - - - - - - - - - - - - - - -

        if (impactoDesempenho > 0) {
            jogador.aumentarDesempenhoAcademico(impactoDesempenho);
        }

        else if (impactoDesempenho < 0) {
            jogador.decrementarDesempenhoAcademico(Math.abs(impactoDesempenho));
        }

        // Dinheiro - - - - - - - - - - - - - - - - - - - - - - - - - -

        if (impactoDinheiro > 0) {
            jogador.aumentarDinheiro(impactoDinheiro);
        }

        else if (impactoDinheiro < 0) {
            jogador.decrementarDinheiro(Math.abs(impactoDinheiro));
        }
    }
}