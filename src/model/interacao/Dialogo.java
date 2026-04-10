package model.interacao;

import model.entidades.Entidade;
import model.mapa.TipoLocal;

public class Dialogo {

    private Entidade npc;
    private String texto;
    private CategoriaDialogo categoria;
    private TipoLocal localDialogo;

    // construtor

    public Dialogo(Entidade npc, String texto, CategoriaDialogo categoria, TipoLocal localDialogo){
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