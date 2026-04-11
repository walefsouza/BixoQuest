package model.mapa;
import model.entidades.Jogador;

public class PontoDeOnibus extends Local{

    private int energiaPegarOnibus;

    // construtor

    public PontoDeOnibus(String nome, String descricao) {
        super(nome, descricao);
        this.energiaPegarOnibus = 20;
    }

    // métodos

    public boolean onibusEstaNoPonto(int energiaDoJogador) {
        return energiaDoJogador <= this.energiaPegarOnibus;
    }

    // getters

    public int getEnergiaPegarOnibus() {
        return this.energiaPegarOnibus;
    }

    @Override
    public TipoLocal getTipo() {
        return TipoLocal.PONTO_DE_ONIBUS;
    }

    // setters

    public void setEnergiaPegarOnibus(int energiaPegarOnibus) {
        this.energiaPegarOnibus = energiaPegarOnibus;
    }


}
