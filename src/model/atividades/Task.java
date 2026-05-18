package model.atividades;

import model.Game;
import model.entidades.Jogador;
import model.mapa.Local;
import model.mapa.TipoLocal;

import java.util.List;

public class Task extends Atividade {

    private boolean realizada;
    private CategoriaTask categoria;
    private int custoEnergia;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public Task(String nome, String descricao, TipoLocal localAtividade,
                boolean realizada, CategoriaTask categoria, String icone, List<Efeito> efeitos) {

        super(nome, descricao, localAtividade,icone,  efeitos);
        this.realizada = realizada;
        this.categoria = categoria;
        this.custoEnergia = categoria.getCustoEnergia();
    }

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - -

    // Verifica se o jogador possui energia suficiente para realizar uma Task
    public boolean podeRealizar(Jogador j) {
        return j.getEnergia() >= this.custoEnergia;
    }

    // Retorna resultados visuais da execução
    public ResultadoAcao executar(Game game) {

        Jogador j = game.getJogador();
        j.decrementarEnergia(this.custoEnergia);
        this.setRealizada(true);

        return super.executar(game);
    }

    // Getters  - - - - - - - - - - - - - - - - - - - - - - - -

    public CategoriaTask getCategoria() {
        return this.categoria;
    }

    public boolean getRealizada() {
        return this.realizada;
    }

    public int getCustoEnergia() {
        return this.custoEnergia;
    }

    // Setters  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setCategoria(CategoriaTask categoria) {
        this.categoria = categoria;
    }

    public void setRealizada(boolean realizada) {
        this.realizada = realizada;
    }

    public void setCustoEnergia(int custoEnergia) {
        this.custoEnergia = custoEnergia;
    }
}