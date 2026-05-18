package model.atividades;

import model.Game;

public class EfeitoMidia implements Efeito {

    private String arquivoAudio;
    private boolean tremerTela;
    private boolean escurecerTela;
    private boolean desfocarTela;
    private String mudarImagemFundo;
    private String iconeSobreposicao;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public EfeitoMidia(String audio, boolean tremer, boolean escurecer,
                       boolean desfoque, String imagemFundo, String sobreposicao) {

        this.arquivoAudio = audio;
        this.tremerTela = tremer;
        this.escurecerTela = escurecer;
        this.desfocarTela = desfoque;
        this.mudarImagemFundo = imagemFundo;
        this.iconeSobreposicao = sobreposicao;
    }

    // Executor  - - - - - - - - - - - - - - - - - - - - - - - -

    // Setando efeitos no DTO para levar a interface gráfica.
    @Override
    public void aplicar(Game jogoAtual, ResultadoAcao resultado) {

        resultado.setTocarAudio(this.arquivoAudio);
        resultado.setTremerTela(this.tremerTela);
        resultado.setEscurecerTela(this.escurecerTela);
        resultado.setEmbacarTela(this.desfocarTela);
        resultado.setMudarImagemFundo(this.mudarImagemFundo);
        resultado.setIconeSobreposicao(this.iconeSobreposicao);
    }
}