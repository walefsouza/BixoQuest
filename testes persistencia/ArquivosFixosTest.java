import com.google.gson.reflect.TypeToken;
import model.atividades.CategoriaTask;
import model.atividades.Task;
import model.mapa.TipoLocal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import repository.Repository;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArquivosFixosTest {

    @Test
    public void carregarDoArquivoECompararAtributos(@TempDir Path tempDir) throws IOException {

        Path arquivoTasks = tempDir.resolve("tasks_fixas.json");

        // Criando um JSON falso para por no arquivo
        String jsonPlantado = "[\n" +
                "  {\n" +
                "    \"nome\": \"Exercícios\",\n" +
                "    \"descricao\": \"Estudar para a prova\",\n" +
                "    \"localAtividade\": \"QUALQUER_LUGAR\",\n" +
                "    \"realizada\": false,\n" +
                "    \"categoria\": \"ACADEMICO\",\n" +
                "    \"icone\": \"icone_estudo.png\",\n" +
                "    \"efeitos\": []\n" +
                "  }\n" +
                "]";


        // Criando arquivo plantado, coletando tipo Task e instância do repositório
        Files.write(arquivoTasks, jsonPlantado.getBytes());
        Type tipoTask = new TypeToken<ArrayList<Task>>(){}.getType();
        Repository<Task> repo = new Repository<>(arquivoTasks.toString(), tipoTask);
        List<Task> tasksCarregadas = repo.listar();

        // Verificando se a lista não está vazia e coletando elemento 0
        assertFalse(tasksCarregadas.isEmpty());
        Task task = tasksCarregadas.get(0);

        // Comparando objeto task e dados enviados para o arquivo
        assertEquals("Exercícios", task.capturarNome());
        assertEquals("Estudar para a prova", task.getDescricao());
        assertEquals(TipoLocal.QUALQUER_LUGAR, task.getLocalAtividade());
        assertFalse(task.getRealizada());
        assertEquals(CategoriaTask.ACADEMICO, task.getCategoria());
        assertEquals("icone_estudo.png", task.getIcone());
        assertTrue(task.getEfeitos().isEmpty());
    }

    @Test
    public void carregarMultiplasTasksEVoltarComMesmaOrdem(@TempDir Path tempDir) throws IOException {

        Path arquivoTasks = tempDir.resolve("tasks_multiplas.json");

        // Taks plantadas no arquivo em ordem
        String jsonMultiplo = "[\n" +
                "  { \"nome\": \"Exercícios\", \"descricao\": \"Estudar\", " +
                "\"localAtividade\": \"QUALQUER_LUGAR\", " +
                "\"realizada\": false, \"categoria\": \"ACADEMICO\", \"icone\": \"icone1.png\", \"efeitos\": [] },\n" +
                "  { \"nome\": \"Almoço\", \"descricao\": \"Comer no RU\", " +
                "\"localAtividade\": \"CANTINA\", " +
                "\"realizada\": false, \"categoria\": \"SOCIAL\", \"icone\": \"icone2.png\", \"efeitos\": [] },\n" +
                "  { \"nome\": \"Descanso\", \"descricao\": \"Tirar uma soneca\", " +
                "\"localAtividade\": \"QUALQUER_LUGAR\", " +
                "\"realizada\": false, \"categoria\": \"BEM_ESTAR\", \"icone\": \"icone3.png\", \"efeitos\": [] }\n" +
                "]";

        Files.write(arquivoTasks, jsonMultiplo.getBytes());

        // Criando arquivo plantado, coletando tipo Task e instância do repositório
        Type tipoTask = new TypeToken<ArrayList<Task>>(){}.getType();
        Repository<Task> repo = new Repository<>(arquivoTasks.toString(), tipoTask);
        List<Task> tasksCarregadas = repo.listar();

        // Verificando quantidade de Tasks enviadas e comparando atributos
        assertEquals(3, tasksCarregadas.size());

        assertEquals("Exercícios", tasksCarregadas.get(0).capturarNome());
        assertEquals("Almoço", tasksCarregadas.get(1).capturarNome());
        assertEquals("Descanso", tasksCarregadas.get(2).capturarNome());

        assertEquals(TipoLocal.QUALQUER_LUGAR, tasksCarregadas.get(0).getLocalAtividade());
        assertEquals(CategoriaTask.SOCIAL, tasksCarregadas.get(1).getCategoria());
        assertEquals(CategoriaTask.BEM_ESTAR, tasksCarregadas.get(2).getCategoria());
    }
}