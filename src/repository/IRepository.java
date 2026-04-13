package repository;

import java.util.List;

// Interface GENÉRICA para o repositório de QUALQUER objeto.
public interface IRepository<T extends IGeneralGetNome> {
    boolean salvar(T objeto);
    T buscar(String identificador);
    List<T> listar();
    boolean remover(String identificador);
}