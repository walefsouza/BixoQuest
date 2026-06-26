package controller.locais;

import application.RotasFixas;
import application.SceneManager;
import application.SessaoSingleton;
import application.Utilitarios;
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

    private Map<String, ICommand> comandosDoMapa = new HashMap<>();
    private Game game;

    @FXML private AnchorPane pane;
    @FXML private ImageView imagemMapa;

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

        if (resultado != null) {

            // card com texto do evento
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
        }
    }

    @FXML
    public void clicarNoMapa(MouseEvent event) {

        Node objetoClicado = (Node) event.getSource();
        String idObjeto = objetoClicado.getId();
        ICommand comando = comandosDoMapa.get(idObjeto);

        if (comando != null) {
            comando.executar();
            event.consume();
        }

        else {
            System.out.println("Área sem comando mapeado: " + idObjeto);
            //não faz sentido um print aqui, remover?
        }
    }

    private void aplicarImagemFundo(String caminhoImagem) {

        if (caminhoImagem == null || caminhoImagem.trim().isEmpty()) {
            return;
        }

        try {

            Image novaImagem = new Image(getClass().getResourceAsStream(caminhoImagem));
            if (!novaImagem.isError()) {
                imagemMapa.setImage(novaImagem);
            }
        }

        catch (Exception e) {
            System.out.println("[DEBUG] Imagem de fundo vazia: " + caminhoImagem);
        }
    }

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