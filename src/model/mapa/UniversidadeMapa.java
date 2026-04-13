package model.mapa;

import repository.IGeneralGetNome;

import java.util.Date;
import java.util.List;

public class UniversidadeMapa {

    private String nome;
    private List<Local> locais;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public UniversidadeMapa(String nome, List<Local> locais) {
        this.nome = nome;;
        this.locais = locais;
    }

    // Setters  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLocais(List<Local> locais) {
        this.locais = locais;
    }

    // Getters  - - - - - - - - - - - - - - - - - - - - - - - -

    public String getNome() {
        return this.nome;
    }

    public List<Local> getLocais() {
        return this.locais;
    }

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - -

    public void adicionarLocal(Local l) {
        if (locais != null)
            locais.add(l);
    }

    public void removerLocal(Local l) {
        if (locais != null)
            locais.remove(l);
    }
}
