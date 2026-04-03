package Model.Mapa;

public enum Cardapio {
    COXINHA("Coxinha", 10),
    PASTEL("Pastel", 15),
    SUCO("Suco", 6),
    CAFE("Cafe", 4),
    BEIJU("Beiju", 12);

    private final String nome;
    private final int preco;

    Cardapio(String nome, int preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public int getPreco() {
        return preco;
    }
}
