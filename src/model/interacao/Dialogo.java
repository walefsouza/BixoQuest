package model.interacao;

import model.entidades.Entidade;
import model.mapa.TipoLocal;
import repository.IGeneralGetNome;

public class Dialogo implements IGeneralGetNome {

    private String id;
    private Entidade npc;
    private String texto;
    private CategoriaDialogo categoria;
    private TipoLocal localDialogo;

    // construtor

    public Dialogo(String id, Entidade npc, String texto, CategoriaDialogo categoria, TipoLocal localDialogo){
        this.id = id;
        this.npc = npc;
        this.texto = texto;
        this.categoria = categoria;
        this.localDialogo = localDialogo;
    }

    // getters

    public Entidade getNpc() {
        return this.npc;
    }

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

    // setters

    public void setNpc(Entidade npc) {
        this.npc = npc;
    }

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