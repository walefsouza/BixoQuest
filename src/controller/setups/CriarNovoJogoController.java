package controller.setups;

import application.*;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.Game;
import model.academico.Semestre;
import model.atividades.Evento;
import model.atividades.Task;
import repository.IRepository;
import repository.LocalRepository;
import repository.Repository;
import service.AtividadeService;
import service.GameService;

import java.util.ArrayList;
import java.util.List;

public class CriarNovoJogoController {

    // Interface - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @FXML private TextField campoNome;
    @FXML private TextField campoMapa;
    @FXML private ImageView btnContinuar;
    @FXML private ImageView btnVoltar;

    @FXML private ImageView avatar1;
    @FXML private ImageView avatar2;
    @FXML private ImageView avatar3;
    @FXML private ImageView avatar4;

    // Declarando interfaces genéricas dos repositórios, com especificação do tipo de dado armazenado
    IRepository saves = new Repository("dados/saves.json", new TypeToken<ArrayList<Game>>(){}.getType());
    IRepository semestres = new Repository("dados/semestres.json", new TypeToken<ArrayList<Semestre>>(){}.getType());
    IRepository tasksRepo = new Repository("dados/bancotasks.json", new TypeToken<ArrayList<Task>>(){}.getType());
    IRepository eventosRepo = new Repository("dados/eventos-bixoquest.json", new TypeToken<ArrayList<Evento>>(){}.getType());

    // Instância do GameService
    GameService gameService = new GameService(saves, semestres);
    AtividadeService atividadeService = new AtividadeService(tasksRepo, eventosRepo);


    private int personagemEscolhido = -1;

    // Inicializar Tela - - - - - - - - - - - - - - - - - - - - - - - -
    // Método padrão do JavaFX que roda automaticamente após iniciar a tela
    @FXML
    public void initialize() {

        // O jogador não pode avançar sem preencher todos os campos obrigatórios
        btnContinuar.setDisable(true);
        campoNome.setFocusTraversable(false); // desativando foco automático do campo
        campoMapa.setFocusTraversable(false);

        // Adicionando observadores em cada campo
        campoNome.textProperty().addListener((
                observador, valorAntigo, valorNovo) -> verificarEscolhas());

        campoMapa.textProperty().addListener((
                observador, valorAntigo, valorNovo) -> verificarEscolhas());

        AudioManager.getInstancia().tocarMusicaDeFundo("/resources/locais/audio/musica-geral-game.mp3");
    }

    // Seleção de Avatares - - - - - - - - - - - - - - - - - - - - - - - -

    @FXML
    public void selecionarAvatar1() {
        personagemEscolhido = 1;
        verificarEscolhas();
        atualizarDestaque(avatar1);
    }

    @FXML
    public void selecionarAvatar2() {
        personagemEscolhido = 2;
        verificarEscolhas();
        atualizarDestaque(avatar2);
    }

    @FXML
    public void selecionarAvatar3() {
        personagemEscolhido = 3;
        verificarEscolhas();
        atualizarDestaque(avatar3);
    }

    @FXML
    public void selecionarAvatar4() {
        personagemEscolhido = 4;
        verificarEscolhas();
        atualizarDestaque(avatar4);
    }

    // Tela do Novo Jogo  - - - - - - - - - - - - - - - - - - - - - - - -

    public void entrarNovoJogo() {
        String nomeJogador = campoNome.getText().trim();
        String nomeMapa = campoMapa.getText().trim();
        Game newGame;

        newGame = gameService.iniciarNovoJogo(nomeMapa, nomeJogador, new LocalRepository().listar(), AparenciaJogador());
        List<Task> tasksIniciais = atividadeService.escolherTasksDaSemana(newGame);
        newGame.getSemestre().setBancoTasks(tasksIniciais);

        SessaoSingleton.getInstancia().setGame(newGame);
        SceneManager.navegar(RotasFixas.MAPACENTRAL.getRotaFixa());
    }

    public void botaoVoltarMenuInicial() {
        Utilitarios.animarClique(btnVoltar, () ->
                SceneManager.navegar(RotasFixas.MENUINICIAL.getRotaFixa())
        );
    }

    // Analisar se os campos foram preenchidos - - - - - - - - - - - - - - - - - -
    // Método responsável por verificar se todos os campos foram preenchidos, pois o
    // jogador só pode avançar se definir o nome do jogador, mapa e escolher o avatar

    private void verificarEscolhas() {

        boolean nomeOk = !campoNome.getText().trim().isEmpty();
        boolean mapaOk = !campoMapa.getText().trim().isEmpty();
        boolean personagemOk = personagemEscolhido != -1;

        // Se tudo estiver preenchido, o botão de continuar fica verde para ele clicar
        if (nomeOk && mapaOk && personagemOk) {
            btnContinuar.setDisable(false);
            btnContinuar.setImage(CacheManager.getInstancia().getImagem("/resources/botoes/btn-inicial-continuar-verde.png"));
        }

        // Se não, continua desativado e o botão permanece cinza
        else {
            btnContinuar.setDisable(true);
            btnContinuar.setStyle("");
            btnContinuar.setImage(CacheManager.getInstancia().getImagem("/resources/botoes/btn-inicial-continuar.png"));
        }
    }

    // Adiciona destaque ao personagem selecionado - - - - - - - - - - - - - - - - - - - -
    // Método com o intuito de adicionar uma borda dourada com desfoque no avatar definido

    private void atualizarDestaque(ImageView selecionado) {
        DropShadow borda = new DropShadow();
        borda.setColor(Color.GOLD);
        borda.setRadius(20);
        borda.setSpread(0.6);

        avatar1.setEffect(null);
        avatar2.setEffect(null);
        avatar3.setEffect(null);
        avatar4.setEffect(null);

        avatar1.setOpacity(0.5);
        avatar2.setOpacity(0.5);
        avatar3.setOpacity(0.5);
        avatar4.setOpacity(0.5);

        selecionado.setEffect(borda);
        selecionado.setOpacity(1.0);
    }

    // Retorna a aparência do jogador selecionado
    private String AparenciaJogador(){

        switch (this.personagemEscolhido) {
            case 1: return "/resources/jogadores/jogador-opcao-1.png";
            case 2: return "/resources/jogadores/jogador-opcao-2.png";
            case 3: return "/resources/jogadores/jogador-opcao-3.png";
            case 4: return "/resources/jogadores/jogador-opcao-4.png";
            default: return "/resources/jogadores/jogador-opcao-1.png";
        }
    }
}
