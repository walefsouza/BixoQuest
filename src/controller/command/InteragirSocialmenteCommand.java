package controller.command;

import application.AudioManager;
import application.SceneManager;
import application.SessaoSingleton;
import com.google.gson.reflect.TypeToken;
import javafx.scene.layout.AnchorPane;
import model.Game;
import model.atividades.ResultadoAcao;
import model.entidades.Colega;
import model.entidades.Jogador;
import repository.IRepository;
import repository.Repository;
import service.JogadorService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InteragirSocialmenteCommand implements ICommand {

    // Atributos - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private AnchorPane pane;
    private JogadorService jogadorService;
    private static List<Colega> cacheColegas = null;

    // Construtor - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public InteragirSocialmenteCommand(AnchorPane pane) {
        this.pane = pane;
        this.jogadorService = new JogadorService();
    }

    // Implementação - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // Consiste em uma função que usa o método interagir socialmente do jogador
    // service. Esse método usa coleegas e instancia eles na memória cache

    @Override
    public void executar() {

        if (cacheColegas == null) {
            IRepository<Colega> colegaRepo = new Repository<>("dados/colegas.json", new TypeToken<ArrayList<Colega>>(){}.getType());
            cacheColegas = colegaRepo.listar();
        }

        // Lista colegas do repositório, embaralha e pega o primeiro do arraylist.
        List<Colega> colegas = new ArrayList<>(cacheColegas);
        Collections.shuffle(colegas);
        Colega colegaSorteado = colegas.get(0);

        Game game = SessaoSingleton.getInstancia().getGame();
        Jogador jogador = game.getJogador();

        ResultadoAcao resultado = jogadorService.interagirSocialmente(jogador, colegaSorteado);

        // Exibe mensagem com resultado da interação
        SceneManager.mostrarDialogoWarn(
                pane,
                resultado.getTitulo(),
                resultado.getTextoNarrativo(),
                colegaSorteado.getAparencia()
        );

        if (resultado.getTocarAudio() != null) {
            AudioManager.getInstancia().tocarEfeito(resultado.getTocarAudio());
        }
    }
}