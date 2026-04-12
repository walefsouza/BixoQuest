package model.mapa;

import repository.IGeneralGetNome;

import java.util.Date;
import java.util.List;

public class UniversidadeMapa {

    private String nome;
    private List<Local> locais;

    public UniversidadeMapa(String nome, List<Local> locais) {
        this.nome = nome;;
        this.locais = locais;
    }

    // setters

    public void setNome(String nome) {
        this.nome = nome;
    }


    public void setLocais(List<Local> locais) {
        this.locais = locais;
    }

    // getters

    public String getNome() {
        return this.nome;
    }


    public List<Local> getLocais() {
        return this.locais;
    }

    // métodos

    public void adicionarLocal(Local l) {
        if (locais != null)
            locais.add(l);
    }

    public void removerLocal(Local l) {
        if (locais != null)
            locais.remove(l);
    }
}
