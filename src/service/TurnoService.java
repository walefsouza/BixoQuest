package service;

import model.academico.Semestre;
import model.atividades.ResultadoAcao;
import model.entidades.Jogador;
import model.atividades.EventoAvaliacao;
import java.util.List;

public class TurnoService {

    private AcademicoService academicoService;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -
    public TurnoService(AcademicoService academicoService) {
        this.academicoService = academicoService;
    }

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - -

    // Passagem de turnos do jogo (semana)
    public ResultadoAcao passarSemana(Semestre semestre, Jogador jogador) {

        ResultadoAcao transicao;

        // Reset e recuperação do jogador
        jogador.aumentarEnergia(100);
        if (jogador.getSaude() < 100) {
            jogador.aumentarSaude(15);
        }

        // Avança o calendário semestral
        semestre.avancarSemana();

        transicao = new ResultadoAcao("A semana acabou. Você descansou e está pronto para a próxima!");
        transicao.setTocarAudio("src/resources/atividades/audio/som-semana-win.mp3");
        transicao.setEscurecerTela(true);

        return transicao;
    }
}