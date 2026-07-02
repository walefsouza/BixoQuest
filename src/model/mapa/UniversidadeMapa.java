package model.mapa;

import repository.IGeneralGetNome;
import java.util.List;

public class UniversidadeMapa implements IGeneralGetNome {

    // Atributos - - - - - - - - - - - - - - - - - - - - - - - - -

    private String nome;
    private List<Local> locais;
    private String imagemMapa;
    private String musicaTema;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public UniversidadeMapa(String nome, List<Local> locais, String imagemMapa, String musicaTema) {
        this.nome = nome;
        this.locais = locais;
        this.imagemMapa = imagemMapa;
        this.musicaTema = musicaTema;
    }

    // Setters  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLocais(List<Local> locais) {
        this.locais = locais;
    }

    public void setMusicaTema(String musicaTema) {
        this.musicaTema = musicaTema;
    }

    // Getters  - - - - - - - - - - - - - - - - - - - - - - - -

    public String getNome() {
        return this.nome;
    }

    public List<Local> getLocais() {
        return this.locais;
    }

    public String getMusicaTema() {
        return this.musicaTema;
    }

    // Interface - - - - - - - - - - - - - - - - - - - - - - - -

    @Override
    public String capturarNome() {
        return this.getNome();
    }
}