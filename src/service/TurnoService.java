package service;

import model.academico.Semestre;
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

    // Agora ele devolve a lista de provas para a Tela!
    public List<EventoAvaliacao> passarSemana(Semestre semestre, Jogador jogador) {

        // 1. Reset e recuperação do jogador
        jogador.aumentarEnergia(100);
        if (jogador.getSaude() < 100) {
            jogador.aumentarSaude(10);
        }

        // 2. Avança o calendário
        semestre.avancarSemana();

        // 3. Pega a pilha de provas do Acadêmico e devolve para a Tela (Controller)
        return academicoService.verificarInicioAvaliacoes(semestre);
    }
}