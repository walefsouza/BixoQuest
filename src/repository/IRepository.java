package repository;

import java.util.List;

// Interface GENÉRICA para o repositório de QUALQUER objeto.

public interface IRepository<T extends IGeneralGetNome> {

    // Salvar objeto no repositório
    boolean salvar(T objeto);

    // Buscar objeto no repositório
    T buscar(String identificador);

    // Listar objeto no repositório
    List<T> listar();

    // Remover objeto no repositório
    boolean remover(String identificador);
}