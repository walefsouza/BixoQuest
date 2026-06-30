package controller.command;

import application.*;
import com.google.gson.reflect.TypeToken;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.academico.Semestre;
import model.atividades.ResultadoAcao;
import model.entidades.Animal;
import model.entidades.Colega;
import model.entidades.Jogador;
import model.entidades.Professor;
import model.interacao.Dialogo;
import model.mapa.Local;
import model.mapa.TipoLocal;
import repository.IRepository;
import repository.Repository;
import service.InteracaoService;

import java.util.*;

public class PosicionarNPCsCommand {

    private AnchorPane panePrincipal;
    private TipoLocal localAtual;
    private double[][] pontosDeSpawn;
    private InteracaoService interacaoService;

    private static List<Colega> cacheColegas = null;
    private static List<Animal> cacheAnimais = null;

    public PosicionarNPCsCommand(AnchorPane panePrincipal, TipoLocal localAtual,
                                 double[][] pontosDeSpawn, InteracaoService interacaoService){

        this.panePrincipal = panePrincipal;
        this.localAtual = localAtual;
        this.pontosDeSpawn = pontosDeSpawn;
        this.interacaoService = interacaoService;
    }


    public void executar() {

        Jogador jogador = SessaoSingleton.getInstancia().getGame().getJogador();
        Semestre semestreAtual = SessaoSingleton.getInstancia().getGame().getSemestre();

        int posicaoNPC = 0; // Controla qual coordenada do array de posições estamos

        // Posiciona jogador
        if (posicaoNPC < pontosDeSpawn.length) {

            String caminhoJogador = converterParaCorpoInteiro(jogador.getAparencia());

            desenharEntidade(

                    panePrincipal,
                    caminhoJogador,
                    pontosDeSpawn[posicaoNPC],

                    () -> {
                        SceneManager.mostrarDialogoWarn(
                                panePrincipal,
                                jogador.getNome(),
                                "Eu não aguento mais.",
                                jogador.getAparencia()
                        );

                        AudioManager.getInstancia().tocarEfeito("/resources/atividades/som-nao-aguento-mais.mp3");
            });

            posicaoNPC++;
        }

        // Carregar NPCS fixos (professor e malei)
        if (posicaoNPC < pontosDeSpawn.length) {

            // Se for Sala de Aula, pega o professor do Semestre atual
            if (localAtual == TipoLocal.SALA_DE_AULA && semestreAtual != null) {

                Professor professor = semestreAtual.getDisciplinas().get(0).getProfessor();
                String caminhoProf = converterParaCorpoInteiro(professor.getAparencia());

                desenharEntidade(

                        panePrincipal,
                        caminhoProf,
                        pontosDeSpawn[posicaoNPC],

                        () -> acionarDialogoHumano(
                                panePrincipal,
                                interacaoService,
                                localAtual
                        ));

                posicaoNPC++;
            }

            // Se for Colegiado, instancia Maeli diretamente na memória
            else if (localAtual == TipoLocal.COLEGIADO) {

                Professor maeli = new Professor("Maeli", 47, 4,"/resources/personagens/secretaria-maeli.png", 5);

                String caminhoMaeli = converterParaCorpoInteiro(maeli.getAparencia());

                desenharEntidade(

                        panePrincipal,
                        caminhoMaeli,
                        pontosDeSpawn[posicaoNPC],

                        () -> acionarDialogoHumano(
                                panePrincipal,
                                interacaoService,
                                localAtual
                        ));

                posicaoNPC++;
            }
        }

        // Criando instâncias em memória RAM para evitar lags no jogo (ESTÁTICAS)
        if (cacheColegas == null || cacheAnimais == null) {

            IRepository<Colega> colegaRepo = new Repository<>("dados/colegas.json", new TypeToken<ArrayList<Colega>>(){}.getType());
            IRepository<Animal> animalRepo = new Repository<>("dados/animais.json", new TypeToken<ArrayList<Animal>>(){}.getType());

            cacheColegas = colegaRepo.listar();
            cacheAnimais = animalRepo.listar();
        }

        List<Colega> colegas = new ArrayList<>(cacheColegas);
        List<Animal> animais = new ArrayList<>(cacheAnimais);

        Collections.shuffle(colegas);
        Collections.shuffle(animais);

        int posicaoAnimal = 0;
        int posicaoColega = 0;

        // Se ainda houver vagas sobrando, preenche com animais ou NPCS
        while (posicaoNPC < pontosDeSpawn.length) {

            // 70% de chance de nascer um colega, 30% de nascer animal (se ainda houver disponíveis)
            boolean priorizarColega = Math.random() > 0.3;

            if (priorizarColega && posicaoColega < colegas.size()) {

                Colega colega = colegas.get(posicaoColega);
                String caminhoColega = converterParaCorpoInteiro(colega.getAparencia());

                desenharEntidade(

                        panePrincipal,
                        caminhoColega,
                        pontosDeSpawn[posicaoNPC],

                        () -> acionarDialogoHumano(
                                panePrincipal,
                                interacaoService,
                                localAtual)
                );

                posicaoColega++;
                posicaoNPC++;
            }

            else if (posicaoAnimal < animais.size()) {

                Animal animal = animais.get(posicaoAnimal);

                // Animais NÃO passam pelo conversor, usam o getAparencia original
                String caminhoAnimal = animal.getAparencia();

                desenharEntidade(panePrincipal, caminhoAnimal, pontosDeSpawn[posicaoNPC], () -> {

                    ResultadoAcao resultado = interacaoService.interagirComAnimal(jogador, animal);

                    SceneManager.mostrarDialogoWarn(
                            panePrincipal,
                            "Interação",
                            resultado.getTextoNarrativo(),
                            "/resources/icones/interface-icon-animal.png");
                });

                posicaoAnimal++;
                posicaoNPC++;
            }

            else {
                break;
            }
        }
    }

    // Converte o sprite do personagem de quadrado para ele em pé no cenário
    private String converterParaCorpoInteiro(String caminhoOriginal) {

        if (caminhoOriginal != null && caminhoOriginal.endsWith(".png")) {
            return caminhoOriginal.replace(".png", "-pe.png");
        }

        return caminhoOriginal;
    }


    // Cria, posiciona, anima e configura o clique de qualquer Entidade do jogo
    private void desenharEntidade(AnchorPane pane, String caminhoImg, double[] coordenada, Runnable acaoClique) {

        // Busca a imagem diretamente no cache global
        Image img = CacheManager.getInstancia().getImagem(caminhoImg);

        // Registra sprite como image view
        ImageView npcVisual = new ImageView(img);

        // Seta npc na coordenada definindo um teto de altura
        double posY;

        if (caminhoImg.contains("animal")) {
            npcVisual.setFitHeight(120);
            posY = coordenada[1] + 300;
        }

        else {
            npcVisual.setFitHeight(450);
            posY = coordenada[1];
        }

        npcVisual.setPreserveRatio(true);
        npcVisual.setLayoutX(coordenada[0]);
        npcVisual.setLayoutY(posY);

        // Animação de respiração
        TranslateTransition respiracao = new TranslateTransition(Duration.seconds(2), npcVisual);
        respiracao.setByY(-2);
        respiracao.setCycleCount(TranslateTransition.INDEFINITE);
        respiracao.setAutoReverse(true);
        respiracao.play();

        // Configura a animação de clique
        Utilitarios.configurarClique(npcVisual);
        npcVisual.setOnMouseClicked(evento -> {
            Utilitarios.animarClique(npcVisual, acaoClique);
        });

        pane.getChildren().add(npcVisual);
    }

    // Usa o método do service para pegar um diálogo no local
    private void acionarDialogoHumano(AnchorPane pane, InteracaoService interacaoService, TipoLocal local) {

        // Lista de diálogos naquele cenário
        List<Dialogo> falas = interacaoService.buscarFalasDoLocal(local);

        if (!falas.isEmpty()) {

            Collections.shuffle(falas);
            Dialogo falaSorteada = falas.get(0);

            SceneManager.mostrarDialogoWarn(
                    pane,
                    falaSorteada.capturarNome(),
                    falaSorteada.getTexto(),
                    "/resources/icones/interface-icon-saudacao.png");
        }
    }
}