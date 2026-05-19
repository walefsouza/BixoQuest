import com.google.gson.reflect.TypeToken;
import model.Game;
import model.academico.Disciplina;
import model.academico.Semestre;
import model.atividades.CategoriaEvento;
import model.atividades.EventoAvaliacao;
import model.atividades.RequisitoEvento;
import model.entidades.Jogador;
import model.entidades.Professor;
import model.mapa.TipoLocal;
import model.mapa.UniversidadeMapa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import repository.Repository;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GamePersistenciaTest {

    @Test
    public void analisarIntegridadeDoGameArmazenado(@TempDir Path tempDir) {

        // Criando arquivo temporário, coletando tipo Game e instância do repositório
        Path arquivoSave = tempDir.resolve("save_bixoquest.json");
        Type tipoGame = new TypeToken<ArrayList<Game>>(){}.getType();
        Repository<Game> repoSalvar = new Repository<>(arquivoSave.toString(),tipoGame);

        // Salvando este jogo em um arquivo JSON
        Game jogoOriginal = forjandoGame();
        repoSalvar.salvar(jogoOriginal);

        // Recarrega do arquivo com uma nova instância do repositório
        // para garantir que não está lendo da memória e sim do disco
        Repository<Game> repoCarregar = new Repository<>(arquivoSave.toString(), tipoGame);
        Game jogoCarregado = repoCarregar.buscar("Save1");

        // Se não for null, o nome do jogador provido do repositório deve ser igual
        // ao anterior para implicar que nosso repositório está funcionando como deve
        assertNotNull(jogoCarregado);
        assertEquals("Calouro", jogoCarregado.getJogador().getNome());

        // Verificando se a nota dentro da disciplina armazenada ainda é 8.
        Disciplina disciplinaCarregada = jogoCarregado.getSemestre().getDisciplinas().get(0);
        EventoAvaliacao avaliacaoCarregada = (EventoAvaliacao) disciplinaCarregada.getAvaliacao();
        assertTrue(avaliacaoCarregada.getRealizada());
        assertEquals(8, avaliacaoCarregada.getNotaObtida());
    }

    @Test
    public void salvarVariosJogosSemPerderNenhum(@TempDir Path tempDir) {

        // Criando arquivo temporário, coletando tipo Game e instância do repositório
        Path arquivo = tempDir.resolve("save_jogos.json");
        Type tipo = new TypeToken<ArrayList<Game>>(){}.getType();
        Repository<Game> repoSalvar = new Repository<>(arquivo.toString(), tipo);

        repoSalvar.salvar(forjandoGame());

        // Criando um segundo jogo com nome diferente (e menos detalhes no semestre pois não é necessário)
        Jogador jogador2 = new Jogador("Veterano", 100, 100,
                100, 50, 50, 100.0, null, "img.png");

        Game jogo2 = new Game("Save2", jogador2, new Semestre(2),
                new UniversidadeMapa("UEFS", new ArrayList<>(), "img", "audio"));

        repoSalvar.salvar(jogo2);

        // Verificando se os dois saves continuam existindo no repositório
        Repository<Game> repoCarregar = new Repository<>(arquivo.toString(), tipo);
        assertNotNull(repoCarregar.buscar("Save1"));
        assertNotNull(repoCarregar.buscar("Save2"));
        assertEquals(2, repoCarregar.listar().size()); // eles existem
    }

    @Test
    public void removerGameDeveReduzirArrayList(@TempDir Path tempDir) {

        // Criando arquivo temporário, coletando tipo Game e instância do repositório
        Path arquivo = tempDir.resolve("save_remover.json");
        Type tipo = new TypeToken<ArrayList<Game>>(){}.getType();
        Repository<Game> repoSalvar = new Repository<>(arquivo.toString(), tipo);

        // Salvando e removendo o jogo gerado por forjandoGame()
        repoSalvar.salvar(forjandoGame());
        repoSalvar.remover("Save1");

        // Recarrega arquivo e verifica se a remoção foi bem sucedida
        Repository<Game> repoCarregar = new Repository<>(arquivo.toString(), tipo);
        assertNull(repoCarregar.buscar("Save1"));
        assertEquals(0, repoCarregar.listar().size());
    }

    @Test
    public void sobreviverAJsonVazio(@TempDir Path tempDir) throws Exception {

        // Criando um arquivo vazio para verificar se a lista vem vazia
        Path arquivo = tempDir.resolve("save_vazio.json");
        Type tipo = new TypeToken<ArrayList<Game>>(){}.getType();
        Files.createFile(arquivo);


        // O ArrayList é retornado sem nenhum elemento
        Repository<Game> repo = new Repository<>(arquivo.toString(), tipo);
        assertNotNull(repo.listar());
        assertEquals(0, repo.listar().size());
    }


    private Game forjandoGame(){

        Jogador jogador = new Jogador("Calouro", 100, 100, 100,
                50, 50, 100.0, null, "img.png");

        Semestre semestre = new Semestre(1);

        EventoAvaliacao avaliacao = new EventoAvaliacao(
                "Prova Final", "Prova de Fim de Semestre", TipoLocal.SALA_DE_AULA,
                CategoriaEvento.OBRIGATORIO, RequisitoEvento.SEMANA4,
                "icone.png", new ArrayList<>(), new ArrayList<>(), 10
        );

        avaliacao.setRealizada(true);
        avaliacao.setNotaObtida(8);

        Disciplina disciplina = new Disciplina("Estrutura de Dados",
                new Professor("Prof", 50, 50, "img.png", 50),
                "icone.png", avaliacao, TipoLocal.SALA_DE_AULA);

        semestre.getDisciplinas().add(disciplina);

        Game jogoOriginal = new Game("Save1", jogador, semestre, new UniversidadeMapa("UEFS",
                new ArrayList<>(), "img", "audio"));

        return jogoOriginal;
    }
}