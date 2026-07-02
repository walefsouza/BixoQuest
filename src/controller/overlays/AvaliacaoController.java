package controller.overlays;

import application.RotasFixas;
import application.SceneManager;
import application.SessaoSingleton;
import application.Utilitarios;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Game;
import model.academico.Disciplina;
import model.academico.Semestre;
import model.atividades.Evento;
import model.atividades.EventoAvaliacao;
import model.atividades.Pergunta;
import model.atividades.ResultadoAcao;
import repository.IRepository;
import repository.Repository;
import service.AcademicoService;
import java.util.ArrayList;
import java.util.List;
import static application.Utilitarios.configurarClique;

public class AvaliacaoController {

    // Interface - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @FXML private Label disciplinaNome;
    @FXML private Label numero;
    @FXML private Label pergunta;

    @FXML private Label opcaoa;
    @FXML private Label opcaob;
    @FXML private Label opcaoc;

    @FXML private ImageView basea;
    @FXML private ImageView baseb;
    @FXML private ImageView basec;

    @FXML private AnchorPane pane;

    // Atributos - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private Disciplina disciplina;
    private List<Pergunta> perguntas;
    private int indiceAtual = 0;
    private List<Integer> respostas;
    private EventoAvaliacao avaliacao;

    IRepository eventosRepo = new Repository("dados/eventos-bixoquest.json", new TypeToken<ArrayList<Evento>>(){}.getType());
    IRepository semestreRepo = new Repository("dados/semestres.json", new TypeToken<ArrayList<Semestre>>(){}.getType());

    // Inicialização - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @FXML
    public void initialize() {

        configurarClique(opcaoa);
        configurarClique(opcaob);
        configurarClique(opcaoc);

        configurarClique(basea);
        configurarClique(baseb);
        configurarClique(basec);


        Game game = SessaoSingleton.getInstancia().getGame();

        AcademicoService academicoService = new AcademicoService(semestreRepo, eventosRepo);

        this.disciplina = game.getSemestre().getDisciplinas().get(0);
        this.avaliacao = disciplina.getAvaliacao();

        this.respostas = new ArrayList<>();
        this.disciplinaNome.setText(disciplina.getNome());

        carregarPergunta(indiceAtual);
    }

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    // Pega avaliação, captura a pergunta com base no indice e atualiza o quiz
    private void carregarPergunta(int indice) {

        Pergunta p = avaliacao.getPerguntas().get(indice);

        pergunta.setText(p.getEnunciado());
        numero.setText((indice+1) + "/" + avaliacao.getPerguntas().size());

        opcaoa.setText(p.getAlternativas()[0]);
        opcaob.setText(p.getAlternativas()[1]);
        opcaoc.setText(p.getAlternativas()[2]);
    }

    // Botões - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @FXML
    void clicarOpcaoA(MouseEvent event) {
        Utilitarios.animarClique(basea, () -> coletarResposta(0));
    }

    @FXML
    void clicarOpcaoB(MouseEvent event) {
        Utilitarios.animarClique(baseb, () -> coletarResposta(1));
    }

    @FXML
    void clicarOpcaoC(MouseEvent event) {
        Utilitarios.animarClique(basec, () -> coletarResposta(2));
    }

    // Auxiliares - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    // Armazena a resposta em um vetor que será processado pela correção
    private void coletarResposta(int opcao) {

        respostas.add(opcao);
        indiceAtual++;

        if (indiceAtual < avaliacao.getPerguntas().size()) {
            carregarPergunta(indiceAtual);
        }

        else {
            finalizarProva();
        }
    }

    // Finaliza avaliação, mostra resultado e volta a tela da sala de aula
    private void finalizarProva() {

        AcademicoService academicoService = new AcademicoService(semestreRepo, eventosRepo);
        Game game = SessaoSingleton.getInstancia().getGame();

        ResultadoAcao resultado = academicoService.corrigirAvaliacao(game, disciplina, respostas);

        SceneManager.mostrarDialogoWarn(
                pane,
                disciplina.getNome(),
                resultado.getTextoNarrativo(),
                disciplina.getIcone()
        );

        pane.setOnMouseClicked(event -> {
            SceneManager.navegar(RotasFixas.SALADEAULA.getRotaFixa());
        });
    }
}