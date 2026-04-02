package Model.Mapa;

public class SalaDeAula extends Local{

    private String codigoSala;

    public SalaDeAula(String nome, String descricao) {
        super(nome, descricao);
    }

    // getters

    public String getCodigoSala() {
        return this.codigoSala;
    }

    // setters

    public void setCodigoSala(String codigoSala) {
        this.codigoSala = codigoSala;
    }

    @Override
    public String descreverLocal() {
        return "Você sabia que a sala de aula pode ser mais que um espaço de aprendizado?";
    }
}
