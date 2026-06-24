package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import model.atividades.Efeito;
import repository.adapters.EfeitoTypeAdapter;

public class Repository<T extends IGeneralGetNome> implements IRepository<T> {

    private List<T> repository;
    private final String CAMINHOREPO;
    private Gson gson;
    private final Type tipoDadoLista;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -
    // Repositório genérico para qualquer objeto, recebe o tipo da lista
    // pois os generics não é capaz de lembrar do tipo T usado.

    public Repository(String CAMINHOREPO, Type tipoDadoLista) {

        //this.gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create(); // gson
        this.gson = new com.google.gson.GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Efeito.class, new EfeitoTypeAdapter())
            .create();

        this.CAMINHOREPO = CAMINHOREPO; // endereço do repositório. ex: src/dados/semestres.json
        this.tipoDadoLista = tipoDadoLista; // codificação para evento, semestre, locais, etc
        this.repository = carregarArquivo(); // carregar listas do repositório
    }

    // Implementações  - - - - - - - - - - - - - - - - - - - - - - - -

    @Override
    public boolean salvar(T t) {

        T objetoExistente = buscar(t.capturarNome());

        // se o objeto existir, remove e adiciona o novo
        if (objetoExistente != null) {
            repository.remove(objetoExistente);
        }

        this.repository.add(t);
        return salvarArquivo(); // retorna true ou false
    }

    @Override
    public T buscar(String identificador) {

        // se o identificador existir, retorna o objeto
        if (identificador != null) {
            for (T objeto : repository) {
                if (objeto.capturarNome().equals(identificador)) {
                    return objeto;
                }
            }
        }

        return null;
    }

    @Override
    public List<T> listar() {

        // Retorna a copia da lista para o Service trabalhar
        //return new ArrayList<>(repository);
        return new ArrayList<>(carregarArquivo());
    }

    @Override
    public boolean remover(String identificador) {

        T objetoRemover = buscar(identificador);

        // Remove objeto do repositório pelo identificador

        if (objetoRemover != null) {
            repository.remove(objetoRemover);
            return salvarArquivo(); // retorna true ou false
        }
        return false;
    }

    // Manipular JSON  - - - - - - - - - - - - - - - - - - - - - - - -

    private boolean salvarArquivo() {

        // Escreve objetos no repositório pelo caminho definido no construtor

        try (FileWriter writer = new FileWriter(CAMINHOREPO)) {
            gson.toJson(repository, writer);
            return true;
        }

        catch (IOException e) {
            return false;
        }
    }

    private List<T> carregarArquivo() {

        try (FileReader arquivo = new FileReader(CAMINHOREPO)) {

            // Na hora de capturar o tipo no construtor usamos isso:
            // Type tipo = new TypeToken<ArrayList<T>>(){}.getType();
            // O que permite reconstruir os objetos com o tipo certo.

            List<T> listaCarregada = gson.fromJson(arquivo, this.tipoDadoLista);

            if (listaCarregada != null) {
                return listaCarregada;
            }

            // Se não houver nenhum dado no arquivo, retorna lista vazia
            return new ArrayList<>();
        }

        catch (IOException e) {
            return new ArrayList<>();
        }
    }
}