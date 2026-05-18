package model.mapa;

import repository.IGeneralGetNome;
import java.util.List;

public class UniversidadeMapa implements IGeneralGetNome {

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

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - -

    public void adicionarLocal(Local l) {
        if (this.locais != null) {
            this.locais.add(l);
        }
    }

    public void removerLocal(Local l) {
        if (this.locais != null) {
            this.locais.remove(l);
        }
    }

    // Setters  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLocais(List<Local> locais) {
        this.locais = locais;
    }

    public void setImagemMapa(String imagemMapa) {
        this.imagemMapa = imagemMapa;
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

    public String getImagemMapa() {
        return this.imagemMapa;
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