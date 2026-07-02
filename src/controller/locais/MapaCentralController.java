package controller.locais;

import application.*;
import com.google.gson.reflect.TypeToken;
import controller.command.ICommand;
import controller.command.ViajarLocalCommand;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Game;
import model.atividades.Evento;
import model.atividades.ResultadoAcao;
import model.atividades.Task;
import model.interacao.Dialogo;
import repository.IRepository;
import repository.LocalRepository;
import repository.Repository;
import service.AtividadeService;
import service.LocalService;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MapaCentralController implements Initializable {

    // Atributos - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private Map<String, ICommand> comandosDoMapa = new HashMap<>();
    private Game game;

    // Interface - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @FXML private AnchorPane pane;
    @FXML private ImageView imagemMapa;

    // Implementação - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        IRepository tasks = new Repository("dados/tasks.json", new TypeToken<ArrayList<Task>>(){}.getType());
        IRepository eventos = new Repository("dados/eventos-bixoquest.json", new TypeToken<ArrayList<Evento>>(){}.getType());
        IRepository dialogos = new Repository("dados/dialogos.json", new TypeToken<ArrayList<Dialogo>>(){}.getType());
        IRepository locais = new LocalRepository();

        LocalService localService = new LocalService(eventos, dialogos, locais);
        AtividadeService atividadeService = new AtividadeService(tasks, eventos);

        this.game = SessaoSingleton.getInstancia().getGame();
        adicionarComandos(localService, atividadeService);

        // REcuperando imagem de fundo se ele saiu no meio do evento
        if (game.getImagemFundoAtual() != null) {
            aplicarImagemFundo(game.getImagemFundoAtual());
        }


        // Processamento de eventos obrigatórios dentro do mapa - - -  -  - -  - - - - - - - -
        ResultadoAcao resultado = atividadeService.processarEventosObrigatorios(game);

        processarEventosMapa(resultado);

        if (game.verificarFormado()){
            game.setImagemFundoAtual("/resources/locais/mapaFormatura.png");
        }

        AudioManager.getInstancia().tocarMusicaDeFundo("/resources/locais/audio/musica-tema-UEFS.mp3");
    }

    // Barra Lateral - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    // Botão mapa central
    @FXML
    public void clicarNoMapa(MouseEvent event) {

        Node objetoClicado = (Node) event.getSource();
        String idObjeto = objetoClicado.getId();
        ICommand comando = comandosDoMapa.get(idObjeto);

        if (comando != null) {
            comando.executar();
            event.consume();
        }
    }

    // Método privado para trocar a imagem de fundo caso seja um evento obrigatório
    private void aplicarImagemFundo(String caminhoImagem) {

        if (caminhoImagem == null || caminhoImagem.trim().isEmpty()) {
            return;
        }

        try {

            Image novaImagem = CacheManager.getInstancia().getImagem(caminhoImagem);

            if (!novaImagem.isError()) {
                imagemMapa.setImage(novaImagem);
            }
        }

        catch (Exception e) {
            System.out.println("Imagem de fundo vazia: " + caminhoImagem);
        }
    }

    // Método com a finalidade de notificar o acontecimento de um evento do jogo
    private void processarEventosMapa(ResultadoAcao resultado){

        if (resultado != null) {

            // Card com texto do evento
            SceneManager.mostrarCardNotificacao(
                    this.pane,
                    resultado.getTitulo(),
                    resultado.getTextoNarrativo(),
                    resultado.getIconeSobreposicao()
            );

            // Se o evento trouxe modificação de cenário, salva no Game e aplica na hora
            String caminhoImagem = resultado.getMudarImagemFundo();

            if (caminhoImagem != null && !caminhoImagem.trim().isEmpty()) {
                game.setImagemFundoAtual(caminhoImagem);
                aplicarImagemFundo(caminhoImagem);
            }

            if (resultado.getTocarAudio() != null) {
                AudioManager.getInstancia().tocarEfeito(resultado.getTocarAudio());
            }
        }
    }

    // Map de Locais - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // Tem a finalidade de guardar os fxmls de cada local do mapa para o usuário
    // ser direcionado logo após clicar em cima do local

    private void adicionarComandos(LocalService localService, AtividadeService atividadeService){

        comandosDoMapa.put("cantina", new ViajarLocalCommand(
                "/fxmls/locais/telaCantina.fxml",
                "CANTINA",
                game,
                localService,
                atividadeService,
                pane
        ));

        comandosDoMapa.put("borogodo", new ViajarLocalCommand(
                "/fxmls/locais/telaBorogodo.fxml",
                "BOROGODO",
                game,
                localService,
                atividadeService,
                pane
        ));

        comandosDoMapa.put("saladeaula", new ViajarLocalCommand(
                "/fxmls/locais/telaSalaDeAula.fxml",
                "SALA DE AULA",
                game,
                localService,
                atividadeService,
                pane
        ));

        comandosDoMapa.put("pontodeonibus", new ViajarLocalCommand(
                "/fxmls/locais/telaPontoDeOnibus.fxml",
                "PONTO DE ONIBUS",
                game,
                localService,
                atividadeService,
                pane
        ));

        comandosDoMapa.put("colegiado", new ViajarLocalCommand(
                "/fxmls/locais/telaColegiado.fxml",
                "COLEGIADO",
                game,
                localService,
                atividadeService,
                pane
        ));

        comandosDoMapa.put("laboratorio", new ViajarLocalCommand(
                "/fxmls/locais/telaLaboratorio.fxml",
                "LABORATORIO",
                game,
                localService,
                atividadeService,
                pane
        ));
    }
}