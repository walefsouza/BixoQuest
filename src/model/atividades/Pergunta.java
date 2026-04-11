package model.atividades;

public class Pergunta {

    private String enunciado;
    private String[] alternativas;
    private int respostaCorreta;

    public Pergunta(String enunciado, String[] alternativas, int respostaCorreta) {
        this.enunciado = enunciado;
        this.alternativas = alternativas;
        this.respostaCorreta = respostaCorreta;
    }

    // setters

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public void setAlternativas(String[] alternativas) {
        this.alternativas = alternativas;
    }

    public void setRespostaCorreta(int respostaCorreta) {
        this.respostaCorreta = respostaCorreta;
    }

    // getters

    public String getEnunciado() {
        return this.enunciado;
    }

    public String[] getAlternativas() {
        return this.alternativas;
    }

    public int getRespostaCorreta() {
        return this.respostaCorreta;
    }

    // métodos

    public boolean verificarResposta(int resposta) {
        return resposta == respostaCorreta;
    }

}
