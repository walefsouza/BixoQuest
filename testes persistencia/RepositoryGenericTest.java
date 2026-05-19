import com.google.gson.reflect.TypeToken;
import model.entidades.Animal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import repository.Repository;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryGenericTest {

    @Test
    public void salvarAnimalFormaCorretaNoJSON(@TempDir Path tempDir) throws IOException {

        // Criando arquivo temporário, coletando tipo Aniaml e instância do repositório
        Path arquivoTeste = tempDir.resolve("animais_teste.json");
        Type tipoAnimal = new TypeToken<ArrayList<Animal>>(){}.getType();
        Repository<Animal> repo = new Repository<>(arquivoTeste.toString(), tipoAnimal);

        // Criando & salvando animal no repositório
        Animal cachorro = new Animal("Rex", 3, 70, "img_cachorro.png", "Cachorro", 4);
        repo.salvar(cachorro);

        // Verifica o conteúdo bruto do arquivo
        String conteudoBrutoJson = new String(Files.readAllBytes(arquivoTeste));
        assertTrue(conteudoBrutoJson.contains("\"nome\": \"Rex\""));
        assertTrue(conteudoBrutoJson.contains("\"especie\": \"Cachorro\""));
        assertTrue(conteudoBrutoJson.contains("\"nivelFofura\": 4"));

        // Recarrega do arquivo com uma nova instância do repositório
        // para garantir que não está lendo da memória e sim do disco
        Repository<Animal> repoCarregar = new Repository<>(arquivoTeste.toString(), tipoAnimal);
        Animal animalCarregado = repoCarregar.buscar("Rex");

        // Compara o Animal original(em memória) com o que foi armazenado
        assertNotNull(animalCarregado);
        assertEquals("Rex", animalCarregado.capturarNome());
        assertEquals("Cachorro", animalCarregado.getEspecie());
        assertEquals(4, animalCarregado.getNivelFofura());
    }

    @Test
    public void saveDuplicadoSubstituiAnimalAnterior(@TempDir Path tempDir) {

        // Criando arquivo temporário, coletando tipo Aniaml e instância do repositório
        Path arquivoTeste = tempDir.resolve("animais_duplicados.json");
        Type tipoAnimal = new TypeToken<ArrayList<Animal>>(){}.getType();
        Repository<Animal> repo = new Repository<>(arquivoTeste.toString(), tipoAnimal);


        // Criando e salvando Rex com fofura 4 no repositório
        Animal cachorro = new Animal("Rex", 3, 70, "img_cachorro.png", "Cachorro", 4);
        repo.salvar(cachorro);

        // Atualizando(salvando) Rex com fofura = 9 noo repositório
        Animal cachorro2 = new Animal("Rex", 3, 70, "img_cachorro.png", "Cachorro", 9);
        repo.salvar(cachorro2);

        // A lista deve ter exatamente um cachorro e a fofura deve ser 9
        assertEquals(1, repo.listar().size());
        Animal recuperado = repo.buscar("Rex");
        assertEquals(9, recuperado.getNivelFofura());
    }
}