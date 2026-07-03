package application;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class AudioManager {

    // Atributos  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private static AudioManager instancia;
    private MediaPlayer mediaPlayerFundo;
    private String caminhoMusicaAtual;
    private boolean mutado = false;

    private static final String MUSICA_PADRAO = "/resources/locais/audio/musica-geral-game.mp3";

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // No Singleton o construtor é privado e usamos o getInstancia()

    private AudioManager() {}

    public static AudioManager getInstancia() {
        if (instancia == null) {
            instancia = new AudioManager();
        }
        return instancia;
    }

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    // Muta/desmuta a sonorização do game
    public void alternarMute() {
        this.mutado = !this.mutado;

        // Se a música de fundo estiver ativa, altera o mute dela instantaneamente
        if (mediaPlayerFundo != null) {
            mediaPlayerFundo.setMute(this.mutado);
        }
    }

    // Retorna se está mutado ou não
    public boolean getMutado() {
        return this.mutado;
    }

    // Toca música de fundo em determinado cenário
    public void tocarMusicaDeFundo(String caminhoCenario) {

        // Se não tem música, usa a padrão
        String caminhoTocar = (caminhoCenario == null || caminhoCenario.trim().isEmpty()) ? MUSICA_PADRAO : caminhoCenario;

        if (caminhoTocar.equals(caminhoMusicaAtual) && mediaPlayerFundo != null) {
            return;
        }

        // Se tinha outra música tocando de outro cenário, para ela antes de começar a nova
        if (mediaPlayerFundo != null) {
            mediaPlayerFundo.stop();
            mediaPlayerFundo.dispose();
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

            mediaPlayerFundo.setMute(this.mutado);

            mediaPlayerFundo.play();
            caminhoMusicaAtual = caminhoTocar;

        }

        catch (Exception e) {
            System.out.println("Erro ao tentar tocar música de fundo: " + e.getMessage());
        }
    }

    // Toca efeitos sonoros como clique do botão, eventos, etc
    public void tocarEfeito(String caminhoEfeito) {

        // Se estiver mutado ou o caminho for inválido, ignora o efeito
        if (this.mutado || caminhoEfeito == null || caminhoEfeito.trim().isEmpty()) {
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

    // Para de tocar a música em determinado contexto
    public void pararMusica() {
        if (mediaPlayerFundo != null) {
            mediaPlayerFundo.stop();
            mediaPlayerFundo.dispose();
            mediaPlayerFundo = null;
            caminhoMusicaAtual = null;
        }
    }
}