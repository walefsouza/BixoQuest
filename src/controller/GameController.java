package controller;

import model.Game;
import model.mapa.Local;
import service.GameService;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private final GameService gameService;

    // Construtor - - - - - - - - - - - - - - - - - - - - - - - -

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // Métodos - - - - - - - - - - - - - - - - - - - - - - - - -

    // Recebe o nome do mapa, do jogador e os locais do mapa para criar um novo jogo.
    public Game criarNovoJogo(String nomeSave, String nomeJogador, List<Local> locaisDoMapa) {

        if (nomeSave == null || nomeSave.trim().isEmpty() || nomeJogador == null || nomeJogador.trim().isEmpty()) {
            return null;
        }
        return gameService.iniciarNovoJogo(nomeSave, nomeJogador, locaisDoMapa, "/jogadores/jogador-opcao-1.png");
    }

    // Recebe um jogo e salva no repositório
    public boolean salvar(Game jogo) {
        if (jogo == null) {
            return false;
        }
        return gameService.salvarProgresso(jogo);
    }

    // Verifica se o jogo está no repositório
    public Game carregar(String nomeSave) {
        if (nomeSave == null || nomeSave.trim().isEmpty()) {
            return null;
        }
        return gameService.carregarJogo(nomeSave);
    }

    // Remove um jogo do repositório
    public boolean deletar(String nomeSave) {
        if (nomeSave == null || nomeSave.trim().isEmpty()) {
            return false;
        }
        return gameService.deletarSave(nomeSave);
    }

    // Retorna todos os saves do repositório
    public List<Game> listarTodosSaves() {
        List<Game> lista = gameService.listarJogos();
        if (lista == null) {
            return new ArrayList<>();
        }
        return lista;
    }
}