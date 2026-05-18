package service;

import model.Game;
import model.academico.Semestre;
import model.entidades.Jogador;
import model.mapa.*;
import repository.IRepository;

import java.util.ArrayList;
import java.util.List;

public class GameService {

    private IRepository<Game> gameRepository;
    private IRepository<Semestre> semestreRepository;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public GameService(IRepository<Game> gameRepository, IRepository<Semestre> semestreRepository) {
        this.gameRepository = gameRepository;
        this.semestreRepository = semestreRepository;
    }

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - -

    public Game iniciarNovoJogo(String nomeJogo, String nomeJogador, List<Local> locais) {

        Semestre semestreInicial = semestreRepository.buscar("1");

        // Se não houver semestre inicial, não pode começar um jogo!!!
        if (semestreInicial == null) {
            return null;
        }

        Jogador jogador = new Jogador(nomeJogador, 100, 50, 50, 50, 50, 50.0, null, "imagem.png");

        UniversidadeMapa mapa = new UniversidadeMapa("UEFS",locais, "imagem.png", "musica.png" );

        Game jogo = new Game(nomeJogo, jogador, semestreInicial, mapa);
        gameRepository.salvar(jogo);

        return jogo;
    }

    public List<Game> listarJogos() {
        return gameRepository.listar();
    }

    public Game buscarJogo(String nome) {
        return gameRepository.buscar(nome);
    }

    public boolean encerrarJogo(String nome) {
        return gameRepository.remover(nome);
    }

    public int consultarProgresso(Game jogo) {
        return jogo.calcularProgresso();
    }

}