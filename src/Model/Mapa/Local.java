package Model.Mapa;

public abstract class Local {

    private String nome;
    //private ArrayList<Atividade> atividades;
    //private ArrayList<Personagem> npcs;
    private String descricao;

    public Local (String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    // setters

    public void setNome(String nome) {
        this.nome = nome;
    }

    // getters

    public String getNome(){
        return this.nome;
    }

    // métodos

    public abstract String descreverLocal();
}
