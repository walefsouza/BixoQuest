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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Game;
import model.atividades.Evento;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.game = SessaoSingleton.getInstancia().getGame();

        IRepository tasks = new Repository("dados/tasks.json", new TypeToken<ArrayList<Task>>(){}.getType());
        IRepository eventos = new Repository("dados/eventos.json", new TypeToken<ArrayList<Evento>>(){}.getType());
        IRepository dialogos = new Repository("dados/dialogos.json", new TypeToken<ArrayList<Dialogo>>(){}.getType());
        IRepository locais = new LocalRepository();

        LocalService localService = new LocalService(eventos, dialogos, locais);
        AtividadeService atividadeService = new AtividadeService(tasks, eventos);

        comandosDoMapa.put("cantina", new ViajarLocalCommand(
                "/fxmls/locais/telaCantina.fxml",
                "CANTINA",
                game,
                localService,
                atividadeService
        ));

        comandosDoMapa.put("borogodo", new ViajarLocalCommand(
                "/fxmls/locais/telaBorogodo.fxml",
                "BOROGODÓ",
                game,
                localService,
                atividadeService
        ));

        comandosDoMapa.put("saladeaula", new ViajarLocalCommand(
                "/fxmls/locais/telaSalaDeAula.fxml",
                "SALA DE AULA",
                game,
                localService,
                atividadeService
        ));

        comandosDoMapa.put("pontodeonibus", new ViajarLocalCommand(
                "/fxmls/locais/telaPontoDeOnibus.fxml",
                "PONTO DE ÔNIBUS",
                game,
                localService,
                atividadeService
        ));

        comandosDoMapa.put("colegiado", new ViajarLocalCommand(
                "/fxmls/locais/telaColegiado.fxml",
                "COLEGIADO",
                game,
                localService,
                atividadeService
        ));

        comandosDoMapa.put("laboratorio", new ViajarLocalCommand(
                "/fxmls/locais/telaLaboratorio.fxml",
                "LABORATÓRIO",
                game,
                localService,
                atividadeService
        ));

    }

    @FXML
    public void clicarNoMapa(MouseEvent event) {

        Node objetoClicado = (Node) event.getSource();
        String idObjeto = objetoClicado.getId();
        ICommand comando = comandosDoMapa.get(idObjeto);

        if (comando != null) {
            comando.executar();

        }

        else {
            System.out.println("Área sem comando mapeado: " + idObjeto);
            //não faz sentido um print aqui, remover?
        }
    }
}

//@FXML
//private ImageView btnVoltar;

    /*public void botaoVoltarInicial() {
        Utilitarios.animarClique(btnVoltar, () ->
                SceneManager.navegar(RotasFixas.MENUINICIAL.getRotaFixa())
        );
    }*/