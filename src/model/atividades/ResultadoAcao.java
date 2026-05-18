package model.atividades;

public class ResultadoAcao {

    // Texto  - - - - - - - - - - - - - - - - - - - - - - - -

    private String textoNarrativo;

    // Audio  - - - - - - - - - - - - - - - - - - - - - - - -

    private String tocarAudio;

    // Animações  - - - - - - - - - - - - - - - - - - - - - - - -

    private boolean tremerTela;
    private boolean escurecerTela;
    private boolean embacarTela;

    // Imagens  - - - - - - - - - - - - - - - - - - - - - - - -

    private String mudarImagemFundo;
    private String iconeSobreposicao;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -
    // DTO que envia os efeitos de mídia ao controller/interface gráfica

    public ResultadoAcao(String textoNarrativo) {
        this.textoNarrativo = textoNarrativo;
    }

    // Getters  - - - - - - - - - - - - - - - - - - - - - - - -

    public String getTextoNarrativo() {
        return this.textoNarrativo;
    }

    public String getTocarAudio() {
        return this.tocarAudio;
    }

    public boolean getTremerTela() {
        return this.tremerTela;
    }

    public boolean getEscurecerTela() {
        return this.escurecerTela;
    }

    public boolean getEmbacarTela() {
        return this.embacarTela;
    }

    public String getMudarImagemFundo() {
        return this.mudarImagemFundo;
    }

    public String getIconeSobreposicao() {
        return this.iconeSobreposicao;
    }

    // Setters  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setTocarAudio(String tocarAudio) {
        this.tocarAudio = tocarAudio;
    }

    public void setTremerTela(boolean tremerTela) {
        this.tremerTela = tremerTela;
    }

    public void setEscurecerTela(boolean escurecerTela) {
        this.escurecerTela = escurecerTela;
    }

    public void setEmbacarTela(boolean embacarTela) {
        this.embacarTela = embacarTela;
    }

    public void setMudarImagemFundo(String mudarImagemFundo) {
        this.mudarImagemFundo = mudarImagemFundo;
    }

    public void setIconeSobreposicao(String iconeSobreposicao) {
        this.iconeSobreposicao = iconeSobreposicao;
    }
}