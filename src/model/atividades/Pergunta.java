package model.atividades;

public class Pergunta {

    // Atributos - - - - - - - - - - - - - - - - - - - - - - - - -

    private String enunciado;
    private String[] alternativas;
    private int respostaCorreta;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public Pergunta(String enunciado, String[] alternativas, int respostaCorreta) {
        this.enunciado = enunciado;
        this.alternativas = alternativas;
        this.respostaCorreta = respostaCorreta;
    }

    // Getters  - - - - - - - - - - - - - - - - - - - - - - - -

    public String getEnunciado() {
        return this.enunciado;
    }

    public String[] getAlternativas() {
        return this.alternativas;
    }

    public int getRespostaCorreta() {
        return this.respostaCorreta;
    }

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - -

    public boolean verificarResposta(int resposta) {
        return resposta == respostaCorreta;
    }

}
