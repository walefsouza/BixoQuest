package Model.Mapa;

public class Borogodo extends Local{

    public Borogodo (String nome, String descricao) {
        super(nome, descricao);
    }

    @Override
    public String descreverLocal() {
        return "A famosa Praça do Engenho e Arte";
    }
}
