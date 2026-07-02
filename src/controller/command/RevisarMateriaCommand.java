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

    // Atributos - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private AnchorPane pane;
    private JogadorService jogadorService;

    // Cache estático para evitar leitura de disco dupla
    private static List<Colega> cacheColegas = null;

    // Construtor - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public RevisarMateriaCommand(AnchorPane pane) {
        this.pane = pane;
        this.jogadorService = new JogadorService();
    }

    // Implementação - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    public void executar() {

        // Verificando se já existe uma instância ativa
        if (cacheColegas == null) {
            IRepository<Colega> colegaRepo = new Repository<>("dados/colegas.json", new TypeToken<ArrayList<Colega>>(){}.getType());
            cacheColegas = colegaRepo.listar();
        }

        // Sorteando colega para revisar matéria
        List<Colega> colegas = new ArrayList<>(cacheColegas);
        Collections.shuffle(colegas);
        Colega colegaSorteado = colegas.get(0);

        // Pegando dados da sessão atual e usando o service
        Game game = SessaoSingleton.getInstancia().getGame();
        Jogador jogador = game.getJogador();
        ResultadoAcao resultado = jogadorService.estudarComColega(jogador, colegaSorteado);

        // Exibindo resultado final da ação para o usuário
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