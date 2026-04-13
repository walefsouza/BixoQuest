package model.mapa;

public enum Cardapio {
    COXINHA("Coxinha", 10),
    PASTEL("Pastel", 15),
    SUCO("Suco", 6),
    CAFE("Cafe", 4),
    BEIJU("Beiju", 12);

    private final String nome;
    private final int preco;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    Cardapio(String nome, int preco) {
        this.nome = nome;
        this.preco = preco;
    }

    // Getter  - - - - - - - - - - - - - - - - - - - - - - - -

    public String getNome() {
        return nome;
    }

    // Setter  - - - - - - - - - - - - - - - - - - - - - - - -

    public int getPreco() {
        return preco;
    }
}
