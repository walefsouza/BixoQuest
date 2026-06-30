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

public class RevisarMateriaCommand implements ICommand {

    private AnchorPane pane;
    private JogadorService jogadorService;

    // Cache estático para evitar leitura de disco dupla
    private static List<Colega> cacheColegas = null;

    public RevisarMateriaCommand(AnchorPane pane) {
        this.pane = pane;
        this.jogadorService = new JogadorService();
    }

    @Override
    public void executar() {

        if (cacheColegas == null) {
            IRepository<Colega> colegaRepo = new Repository<>("dados/colegas.json", new TypeToken<ArrayList<Colega>>(){}.getType());
            cacheColegas = colegaRepo.listar();
        }

        List<Colega> colegas = new ArrayList<>(cacheColegas);
        Collections.shuffle(colegas);
        Colega colegaSorteado = colegas.get(0);

        Game game = SessaoSingleton.getInstancia().getGame();
        Jogador jogador = game.getJogador();

        ResultadoAcao resultado = jogadorService.estudarComColega(jogador, colegaSorteado);

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