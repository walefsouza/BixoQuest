package service;

import model.Game;
import model.atividades.*;
import model.entidades.Jogador;
import model.mapa.TipoLocal;
import repository.IRepository;

import java.util.*;

public class AtividadeService {

    private IRepository<Task> taskRepository;
    private IRepository<Evento> eventoRepository;
    private Random random;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public AtividadeService(IRepository<Task> taskRepository, IRepository<Evento> eventoRepository) {
        this.taskRepository = taskRepository;
        this.eventoRepository = eventoRepository;
        this.random = new Random();
    }

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - -

    public List<Task> escolherTasksDaSemana(Game game) {

        // Define um HashMap com as tasks separadas em categorias
        List<Task> repositoryTasks = taskRepository.listar();
        Map<CategoriaTask, List<Task>> mapTasks = new HashMap<>();

        // Separa as tasks não realizadas em listas por categoria
        for (Task task : repositoryTasks) {

            // Verifica se a task já foi realizada nos saves do game
            if (!game.getTaskRealizada(task.getNome())) {

                if (!mapTasks.containsKey(task.getCategoria())) {
                    mapTasks.put(task.getCategoria(), new ArrayList<>());
                }

                mapTasks.get(task.getCategoria()).add(task);

            }
        }

        // Lista para receber as tasks da semana (uma por categoria)
        List<Task> tasksDaSemana = new ArrayList<>();

        for (List<Task> lista : mapTasks.values()) {
            if (!lista.isEmpty()) {
                Collections.shuffle(lista);
                tasksDaSemana.add(lista.get(0));
            }
        }

        // Misturando para não ficarem sempre na mesma ordem na interface
        Collections.shuffle(tasksDaSemana);
        return tasksDaSemana;
    }

    // Executando task e aplicando efeitos no jogador ou interface gráfica
    public ResultadoAcao executarTask(Task task, Game game) {

        Jogador jogador = game.getJogador();

        if (jogador.getEnergia() < task.getCustoEnergia()) {

            // Se não tiver energia, sobrescreve o texto do DTO e não executa a task.
            ResultadoAcao erro;
            erro = new ResultadoAcao("Você está exausto demais para " + task.getNome() + ".");
            erro.setTocarAudio("src/resources/atividades/audio/som-sem-energia.mp3");
            return erro;
        }

        // Aplicando efeitos nos atributos do jogador e enviando DTO para interface
        ResultadoAcao resultado = task.executar(game);
        game.setTasksRealizadas(task.getNome());
        resultado.setTocarAudio("src/resources/atividades/audio/som-att-realizada.mp3");
        resultado.setSucesso(true);

        return resultado;
    }

    // Verifica se algum evento do repositório pode ser inicializado
    public ResultadoAcao processarEventosObrigatorios(Game jogoAtual) {

        List<Evento> todosEventos = eventoRepository.listar();

        // Eventos obrigatórios
        for (Evento evento : todosEventos) {

            // Se for obrigatório, o evento deve acontecer imediatamente desde que não tenha acontecido
            if (evento.getCategoria() == CategoriaEvento.OBRIGATORIO && evento.verificarCondicao(jogoAtual)) {

                if (!jogoAtual.getEventoRealizado(evento.getNome())) {

                    ResultadoAcao resultado = evento.executar(jogoAtual);
                    jogoAtual.setEventoRealizado(evento.getNome());
                    return resultado;
                }
            }
        }
        return null;
    }

    // Retorna o evento surpresa ou NULL se nada acontecer ao viajar
    public ResultadoAcao processarEventosAleatorios(Game game, TipoLocal destino) {

        // Sorteando chance do evento acontecer
        int chance = random.nextInt(100);

        if (chance > 30) {
            return null;
        }

        List<Evento> eventoSurpresa = new ArrayList<>();

        // Selecionando eventos aleatórios
        for (Evento evento : eventoRepository.listar()) {

            boolean imprevisto = evento.getCategoria() == CategoriaEvento.IMPREVISTO;
            boolean oportunidade = evento.getCategoria() == CategoriaEvento.OPORTUNIDADE;

            if ((imprevisto || oportunidade) && evento.verificarCondicao(game)) {

                if (evento.getLocalAtividade() == destino || evento.getLocalAtividade() == TipoLocal.QUALQUER_LUGAR) {
                    eventoSurpresa.add(evento);
                }
            }
        }

        // Se nenhum evento caiu na probabilidade, retorna null
        if (eventoSurpresa.isEmpty()){
            return null;
        }

        // Sorteando um evento na lista de eventos surpresa
        Evento eventoSorteado = eventoSurpresa.get(random.nextInt(eventoSurpresa.size()));

        return eventoSorteado.executar(game);
    }
}