package model.atividades;

public enum CategoriaTask {

    // Categorias  - - - - - - - - - - - - - - - - - - - - - - - -

    ACADEMICO(15),  // Tasks voltadas ao conhecimento;
    BEM_ESTAR(5),   // Tasks voltadas a saúde;
    SOCIAL(8),      // Tasks voltadas a motivação;
    FINANCEIRO(12), // Tasks voltadas ao dinheiro;
    EXTRA(10);      // Tasks com outras finalidades;

    private final int custoEnergia;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    // As tasks possuem um custo fixo de energia por sua categoria.
    CategoriaTask(int custoEnergia) {
        this.custoEnergia = custoEnergia;
    }

    // Getter  - - - - - - - - - - - - - - - - - - - - - - - - - -

    public int getCustoEnergia() {
        return custoEnergia;
    }
}
