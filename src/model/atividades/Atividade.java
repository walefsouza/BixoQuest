package model.atividades;

import model.Game;
import model.entidades.Jogador;
import model.mapa.Local;
import model.mapa.TipoLocal;
import repository.IGeneralGetNome;
import java.util.List;

public abstract class Atividade implements IGeneralGetNome {

    private String nome;
    private String descricao;
    private TipoLocal localAtividade;
    private String icone;

    // Lista que guarda as consequências de uma ação (físicas ou visuais)
    protected List<Efeito> efeitos;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -
    public Atividade (String nome, String descricao, TipoLocal localAtividade, String icone, List<Efeito> efeitos) {

        this.nome = nome;
        this.descricao = descricao;
        this.localAtividade = localAtividade;
        this.efeitos = efeitos;
        this.icone = icone;
    }

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - -

    // O executar retorna o DTO e aplica os efeitos de atributo no Jogador
    public ResultadoAcao executar(Game game) {

        ResultadoAcao resultado = new ResultadoAcao(this.descricao);

        // Aplicando efeitos
        if (this.efeitos != null) {

            for (Efeito e : this.efeitos) {
                e.aplicar(game, resultado);
            }
        }

        return resultado;
    }

    // Setters  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setLocalAtividade(TipoLocal localAtividade) {
        this.localAtividade = localAtividade;
    }

    public void setEfeitos(List<Efeito> efeitos) {
        this.efeitos = efeitos;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    // Getters  - - - - - - - - - - - - - - - - - - - - - - - -

    public String getNome() {
        return this.nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public TipoLocal getLocalAtividade() {
        return localAtividade;
    }

    public List<Efeito> getEfeitos() {
        return this.efeitos;
    }

    public String getIcone() {
        return this.icone;
    }

    // Interface  - - - - - - - - - - - - - - - - - - - - - - - -

    @Override
    public String capturarNome() {
        return this.getNome();
    }
}