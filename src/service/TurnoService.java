package service;

import model.academico.Semestre;
import model.entidades.Jogador;

public class TurnoService {

    private AcademicoService academicoService;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public TurnoService(AcademicoService academicoService) {
        this.academicoService = academicoService;
    }

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - -

    // Aplica o reset da energia semanal, avança a semana e verfica o começo das avaliações
    public boolean passarSemana(Semestre semestre, Jogador jogador) {

        jogador.aumentarEnergia(100);
        if (jogador.getSaude() < 100) jogador.aumentarSaude(10);

        semestre.avancarSemana();

        academicoService.verificarInicioAvaliacoes(semestre, jogador);

        return true;
    }
}