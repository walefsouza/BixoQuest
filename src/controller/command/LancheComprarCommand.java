package controller.command;

import application.AudioManager;
import application.SceneManager;
import application.SessaoSingleton;
import javafx.scene.layout.AnchorPane;
import model.Game;
import model.atividades.ResultadoAcao;
import model.mapa.Cantina;
import model.mapa.Cardapio;
import model.mapa.TipoLocal;
import repository.IRepository;
import service.LocalService;

public class LancheComprarCommand implements ICommand {

    private String nomeLanche;
    private int preco;
    private Game game;
    private LocalService localService;
    private Cardapio item;
    private AnchorPane pane;

    public LancheComprarCommand(Cardapio item, LocalService localService, AnchorPane pane) {
        this.nomeLanche = item.getNome();
        this.preco = item.getPreco();
        this.game = SessaoSingleton.getInstancia().getGame();
        this.localService = localService;
        this.item = item;
        this.pane = pane;
    }

    @Override
    public void executar() {

        Cantina cantina = (Cantina) localService.buscarLocal("CANTINA");
        ResultadoAcao resultado = localService.comprarLanche(this.game, cantina, this.item);

        if (resultado.getSucesso()) {
            SceneManager.mostrarCardNotificacao(
                    this.pane,
                    "Lanche Comprado!",
                    resultado.getTextoNarrativo(),
                    "/resources/icones/interface-icon-sucesso.png"
            );

            if (resultado.getTocarAudio() != null) {
                AudioManager.getInstancia().tocarEfeito(resultado.getTocarAudio());
            }

        }

        else {
            SceneManager.mostrarCardNotificacao(
                    this.pane,
                    "Atenção!",
                    resultado.getTextoNarrativo(),
                    "/resources/icones/interface-icon-erro.png"
            );

            if (resultado.getTocarAudio() != null) {
                AudioManager.getInstancia().tocarEfeito(resultado.getTocarAudio());
            }
        }
    }
}