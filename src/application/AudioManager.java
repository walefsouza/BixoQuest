package application;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class AudioManager {

    private static AudioManager instancia;
    private MediaPlayer mediaPlayerFundo;
    private String caminhoMusicaAtual;
    private boolean isMutado = false;

    private static final String MUSICA_PADRAO = "/resources/locais/audio/musica-geral-game.mp3";


    private AudioManager() {}

    public static AudioManager getInstancia() {
        if (instancia == null) {
            instancia = new AudioManager();
        }
        return instancia;
    }

    public void alternarMute() {
        this.isMutado = !this.isMutado;

        // Se a música de fundo estiver ativa, altera o mute dela instantaneamente
        if (mediaPlayerFundo != null) {
            mediaPlayerFundo.setMute(this.isMutado);
        }
    }

    public boolean isMutado() {
        return this.isMutado;
    }

    public void tocarMusicaDeFundo(String caminhoCenario) {

        // Se não tem música, usa a padrão
        String caminhoTocar = (caminhoCenario == null || caminhoCenario.trim().isEmpty()) ? MUSICA_PADRAO : caminhoCenario;

        if (caminhoTocar.equals(caminhoMusicaAtual) && mediaPlayerFundo != null) {
            return;
        }

        // Se tinha outra música tocando de outro cenário, para ela antes de começar a nova
        if (mediaPlayerFundo != null) {
            mediaPlayerFundo.stop();
        }

        try {
            URL recurso = getClass().getResource(caminhoTocar);

            if (recurso == null) {
                System.out.println("MÚSICA NÃO ENCONTRADA: " + caminhoTocar);
                return;
            }

            Media media = new Media(recurso.toString());
            mediaPlayerFundo = new MediaPlayer(media);

            mediaPlayerFundo.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayerFundo.setVolume(0.3);

            mediaPlayerFundo.setMute(this.isMutado);

            mediaPlayerFundo.play();
            caminhoMusicaAtual = caminhoTocar;

        }

        catch (Exception e) {
            System.out.println("Erro ao tentar tocar música de fundo: " + e.getMessage());
        }
    }

    public void tocarEfeito(String caminhoEfeito) {
        // Se estiver mutado ou o caminho for inválido, ignora o efeito
        if (this.isMutado || caminhoEfeito == null || caminhoEfeito.trim().isEmpty()) {
            return;
        }

        try {
            URL recurso = getClass().getResource(caminhoEfeito);

            if (recurso != null) {
                AudioClip clip = new AudioClip(recurso.toString());
                clip.setVolume(0.8);
                clip.play();
            }

            else {
                System.out.println("EFEITO NÃO ENCONTRADO: " + caminhoEfeito);
            }

        }

        catch (Exception e) {
            System.out.println("Erro ao tentar tocar efeito: " + e.getMessage());
        }
    }

    public void pararMusica() {
        if (mediaPlayerFundo != null) {
            mediaPlayerFundo.stop();
            caminhoMusicaAtual = null;
        }
    }
}