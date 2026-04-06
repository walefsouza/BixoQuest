package Model.Mapa;

public class Laboratorio extends Local{

    // construtor

    public Laboratorio (String nome, String descricao) {
        super(nome, descricao);
    }

    // task de montar circuito e realizar experimento

    // métodos

    public String descreverLocal(){
        return "Cuidados com os escorpiões do laboratório!";
    }
}
