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

    public GameService(IRepository<Game> gameRepository, IRepository<Semestre> semestreRepository) {
        this.gameRepository = gameRepository;
        this.semestreRepository = semestreRepository;
    }

    public Game iniciarNovoJogo(String nomeJogo, String nomeJogador) {

        Semestre semestreInicial = semestreRepository.buscar("1");

        if (semestreInicial == null) {
            return null;
        }

        Jogador jogador = new Jogador(nomeJogador, 100, 50, 50, 50, 50, 50.0, null);

        UniversidadeMapa mapa = criarMapa();

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

    private UniversidadeMapa criarMapa() {
        List<Local> locais = new ArrayList<>();

        locais.add(new Cantina("Cantina", "Cantina universitária", 0));
        locais.add(new Laboratorio("Laboratório", "Lab de computação", 10, 1.5));
        locais.add(new Colegiado("Colegiado", "Secretaria do curso", true));
        locais.add(new SalaDeAula("Sala de Aula", "Sala principal", true));
        locais.add(new PontoDeOnibus("Ponto de Ônibus", "Ponto de Checkpoint"));
        locais.add(new Borogodo("Borogodó", "Praça de convivência", 0, false));

        return new UniversidadeMapa("UEFS", locais);
    }
}