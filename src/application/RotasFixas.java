package application;

public enum RotasFixas {

    //TELAJOGAR("/fxmls/iniciais/telaJogar.fxml"), // tela inicial
    TELAJOGAR("/fxmls/iniciais/telaBixoquestJogar.fxml"),
    MENUINICIAL("/fxmls/iniciais/telaInicial.fxml"), // nojo jogo/salvos
    NOVOJOGO("/fxmls/iniciais/criarNovoJogo.fxml"), // criar novo jogo
    JOGOSSALVOS("/fxmls/iniciais/listarJogosSalvos.fxml"),
    MAPACENTRAL("/fxmls/iniciais/mapacentral.fxml"),

    // OVERLAYS

    MENUPAUSE("/fxmls/overlays/menuPause.fxml"),
    MENUCANTINA("/fxmls/overlays/menuCantina.fxml"),
    STATUSJOGADOR("/fxmls/overlays/playerStatus.fxml"),
    TASKSSEMANAIS("/fxmls/overlays/tasksGroup.fxml"),
    CAIXADIALOGO("/fxmls/overlays/caixaDialogo.fxml"),
    AVALIACAO("/fxmls/overlays/provaDisciplina.fxml"),

    // LOCAIS
    CANTINA("/fxmls/locais/telaCantina.fxml"),
    SALADEAULA("/fxmls/locais/telaSalaDeAula.fxml"),

    // INFORMS

    NOVOSEMESTRE("/fxmls/informs/novo-semestre.fxml"),
    PCQUEBRADO("/fxmls/informs/pc-quebrado.fxml"),
    NOVASEMANA("/fxmls/informs/nova-semana.fxml"),
    PERDEUSEMESTRE("/fxmls/informs/perdeu-semestre.fxml"),


    FIM("fimmm");


    private String caminhoFxml;

    RotasFixas(String caminhoFxml) {
        this.caminhoFxml = caminhoFxml;
    }

    public String getRotaFixa() {
        return this.caminhoFxml;
    }
}
