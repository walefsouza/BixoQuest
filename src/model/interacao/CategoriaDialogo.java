package model.interacao;

public enum CategoriaDialogo {

    // Categorias  - - - - - - - - - - - - - - - - - - - - - - - -

    SAUDACAO("src/resources/icones/interface-icon-saudacao.png"),
    // "Olá, seja bem-vindo ao Borogodó (Praça do Engenho & Arte)"

    LORE("src/resources/icones/interface-icon-lore.png"),
    // "Ouvi dizer que tem escorpiões no LEDS... CUIDADO!"

    DICA("src/resources/icones/interface-icon-dica.png"),
    // "Faça a lista de exercícios dos professores antes das avaliações!"

    BRONCA("src/resources/icones/interface-icon-bronca.png");
    // "Você não deveria estar estudando em vez de ficar no Borogodó?"

    private final String icone;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    CategoriaDialogo (String icone){
        this.icone = icone;
    }

    // Getter  - - - - - - - - - - - - - - - - - - - - - - - - - -

    public String getIcone() {
        return this.icone;
    }
}