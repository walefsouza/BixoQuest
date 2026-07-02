package model.mapa;

public enum Cardapio {

    // Cardápio - - - - - - - - - - - - - - - - - - - - - - - - -

    COXINHA("Coxinha", 10),
    PASTEL("Pastel", 15),
    SUCO("Suco", 6),
    CAFE("Cafe", 4),
    BEIJU("Beiju", 12);

    // Atributos - - - - - - - - - - - - - - - - - - - - - - - - -

    private final String nome;
    private final int preco;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    Cardapio(String nome, int preco) {
        this.nome = nome;
        this.preco = preco;
    }

    // Getters  - - - - - - - - - - - - - - - - - - - - - - - - - -

    public String getNome() {
        return nome;
    }

    public int getPreco() {
        return preco;
    }
}
