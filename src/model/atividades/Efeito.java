package model.atividades;

import model.Game;

public interface Efeito {

    // Executa o efeito: modifica o estado atual do jogo (Jogador/Mapa)
    // e preenche o ResultadoAcao com os feedbacks visuais/sonoros para a tela.

    void aplicar(Game jogoAtual, ResultadoAcao resultado);

}

