package model.entidades;

// remoção do random e inclusão de inteligência e carisma
public class Colega extends Entidade {

    // Atributos - - - - - - - - - - - - - - - - - - - - - - - - -

    private int nivelInteligencia; // 1 a 5
    private int nivelCarisma; //0 a 5

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public Colega (String nome, int idade, int humor, String aparencia, int nivelInteligencia, int nivelCarisma){
        super(nome,idade, humor, aparencia);
        this.nivelInteligencia = nivelInteligencia;
        this.nivelCarisma = nivelCarisma;
    }

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - -

    public int calcularBonusDeEstudo() {
        return this.nivelInteligencia * 2;
    }

    // O humor do colega implica na motivação do jogador
    public int calcularImpactoConversa() {

        if (this.getHumor() < 40) {
            return -10; //diminui motivação
        }
        return this.nivelCarisma * 3; // aumenta a motivação
    }

    // Getters  - - - - - - - - - - - - - - - - - - - - - - - -

    public int getNivelInteligencia() {
        return this.nivelInteligencia;
    }

    public int getNivelCarisma() {
        return this.nivelCarisma;
    }

    // Tipo Entidade  - - - - - - - - - - - - - - - - - - - - - - - -

    public TipoEntidade getTipoEntidade() {
        return TipoEntidade.COLEGA;
    }
}
