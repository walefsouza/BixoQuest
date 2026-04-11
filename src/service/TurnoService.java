package service;

import model.academico.Semestre;
import model.entidades.Jogador;

public class TurnoService {

    private final AcademicoService academicoService;

    public TurnoService(AcademicoService academicoService) {
        this.academicoService = academicoService;
    }

    public boolean passarSemana(Semestre semestre, Jogador jogador) {

        jogador.setEnergia(100);
        if (jogador.getSaude() < 100) jogador.aumentarSaude(10);

        semestre.setSemanaAtual(semestre.getSemanaAtual() + 1);

        academicoService.verificarInicioAvaliacoes(semestre, jogador);

        return true;
    }
}