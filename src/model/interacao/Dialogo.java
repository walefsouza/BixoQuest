package model.interacao;

import model.entidades.Entidade;
import model.entidades.TipoEntidade;
import model.mapa.TipoLocal;
import repository.IGeneralGetNome;

public class Dialogo implements IGeneralGetNome {

    private String id;
    private TipoEntidade tipo;
    private String texto;
    private CategoriaDialogo categoria;
    private TipoLocal localDialogo;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public Dialogo(String id, TipoEntidade tipo, String texto,
                   CategoriaDialogo categoria, TipoLocal localDialogo) {

        this.id = id;
        this.tipo = tipo;
        this.texto = texto;
        this.categoria = categoria;
        this.localDialogo = localDialogo;
    }

    // Getters  - - - - - - - - - - - - - - - - - - - - - - - -

    public String getTexto() {
        return this.texto;
    }

    public CategoriaDialogo getCategoria(){
        return this.categoria;
    }

    public TipoLocal getLocalDialogo() {
        return this.localDialogo;
    }

    @Override
    public String capturarNome() {
        return this.id;
    }

    // Setters  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setCategoria(CategoriaDialogo categoria) {
        this.categoria = categoria;
    }

    public void setLocalDialogo(TipoLocal localDialogo) {
        this.localDialogo = localDialogo;
    }
}