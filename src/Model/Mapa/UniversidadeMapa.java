package Model.Mapa;

import java.util.Date;
import java.util.List;

public class UniversidadeMapa {

    private String nome;
    private List<Local> locais;
    private Date fundacao;

    public UniversidadeMapa(String nome, Date fundacao, List<Local> locais) {
        this.nome = nome;
        this.fundacao = fundacao;
        this.locais = locais;
    }

    // setters

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setFundacao(Date fundacao) {
        this.fundacao = fundacao;
    }

    public void setLocais(List<Local> locais) {
        this.locais = locais;
    }

    // getters

    public String getNome() {
        return this.nome;
    }

    public Date getFundacao() {
        return this.fundacao;
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
