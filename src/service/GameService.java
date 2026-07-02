package service;

import model.Game;
import model.academico.Semestre;
import model.entidades.Jogador;
import model.mapa.*;
import repository.IRepository;
import java.util.List;

public class GameService {
    // Atributos - - - - - - - - - - - - - - - - - - - - - - - - -

    private IRepository<Game> gameRepository;
    private IRepository<Semestre> semestreRepository;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public GameService(IRepository<Game> gameRepository, IRepository<Semestre> semestreRepository) {
        this.gameRepository = gameRepository;
        this.semestreRepository = semestreRepository;
    }

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - -

    public Game iniciarNovoJogo(String nomeSave, String nomeJogador, List<Local> locaisDoMapa, String aparencia) {

        Semestre semestreInicial = semestreRepository.buscar("1");

        if (semestreInicial == null) {
            throw new RuntimeException("Banco de semestres não encontrado!");
        }

        // Cria o Jogador (Status iniciais do Bixo da UEFS)
        Jogador jogador = new Jogador(
                nomeJogador,
                100, // Energia
                50,   // Conhecimento
                50, // Motivação
                50, // Saúde
                50, // Desempenho
                50.0,// Dinheiro
                null, // Local
                aparencia
        );

        // Cria o Mapa com os locais passados como parâmetro
        UniversidadeMapa mapa = new UniversidadeMapa("Campus UEFS",
                locaisDoMapa,
                "src/resources/locais/mapacentraluefs.png",
                "src/resources/locais/audio/musica-tema-UEFS.mp3");

        Game jogo = new Game(nomeSave, jogador, semestreInicial);

        gameRepository.salvar(jogo);

        return jogo;
    }

    // Salvar o jogo atual no repositório de jogos
    public boolean salvarProgresso(Game jogo) {

        if (jogo == null){
            return false;
        }

        // Sobrescreve o JSON atual com os atributos novos (dinheiro, energia, etc)
        gameRepository.salvar(jogo);
        return true;
    }

    // Procura jogo salvo no repositório de jogos
    public Game carregarJogo(String nome) {

        Game save = gameRepository.buscar(nome);

        // se o jogo não existir, retorna null
        if (save == null) {
            return null;
        }

        return save;
    }

    // Deletar um jogo do repositório de jogos
    public boolean deletarSave(String nome) {
        return gameRepository.remover(nome);
    }

    public List<Game> listarJogos() {
        return gameRepository.listar();
    }

    public int consultarProgresso(Game jogo) {
        return jogo.calcularProgresso();
    }

}