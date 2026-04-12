package repository;

import java.util.ArrayList;
import java.util.List;

public class Repository<T extends IGeneralGetNome> implements IRepository<T> {


    private List<T> repository;

    // construtor

    public Repository() {
        this.repository = new ArrayList<>();
    }

    // implementações

    @Override
    public boolean salvar(T t) {

        T objetoExistente = buscar(t.capturarNome());

        if (objetoExistente != null) {
            repository.remove(objetoExistente);
        }

        this.repository.add(t);
        return true;
    }

    @Override
    public T buscar(String identificador) {

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
        // Retorna a lista íntegra para o seu Service trabalhar
        return new ArrayList<>(this.repository);
    }

    @Override
    public boolean remover(String identificador) {
        T objetoRemover = buscar(identificador);
        if (objetoRemover != null) {
            repository.remove(objetoRemover);
            return true;
        }
        return false;
    }
}