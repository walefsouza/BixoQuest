package model.mapa;
import model.entidades.Jogador;

public class PontoDeOnibus extends Local{

    private int energiaPegarOnibus;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public PontoDeOnibus(String nome, String descricao) {
        super(nome, descricao);
        this.energiaPegarOnibus = 20;
    }

    // Método  - - - - - - - - - - - - - - - - - - - - - - - -

    // O ônibus, nosso checkpoint só pode estar no ponto se o jogador tiver gasto energia o suficiente
    public boolean onibusEstaNoPonto(int energiaDoJogador) {
        return energiaDoJogador <= this.energiaPegarOnibus;
    }

    // Getters  - - - - - - - - - - - - - - - - - - - - - - - -

    public int getEnergiaPegarOnibus() {
        return this.energiaPegarOnibus;
    }

    @Override
    public TipoLocal getTipo() {
        return TipoLocal.PONTO_DE_ONIBUS;
    }

    // Setter  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setEnergiaPegarOnibus(int energiaPegarOnibus) {
        this.energiaPegarOnibus = energiaPegarOnibus;
    }


}
