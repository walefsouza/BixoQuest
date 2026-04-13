package repository;

import java.util.ArrayList;
import java.util.List;

public class Repository<T extends IGeneralGetNome> implements IRepository<T> {

    private List<T> repository;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -
    // Repositório genérico para qualquer objeto

    public Repository() {
        this.repository = new ArrayList<>();
    }

    // Implementações  - - - - - - - - - - - - - - - - - - - - - - - -

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
        // Retorna a lista para o Service trabalhar
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