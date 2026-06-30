package application;

import javafx.scene.image.Image;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class CacheManager {

    private static CacheManager instancia;
    private Map<String, SoftReference<Image>> imagens;

    private CacheManager() {
        this.imagens = new HashMap<>();
    }

    public static CacheManager getInstancia() {
        if (instancia == null) {
            instancia = new CacheManager();
        }
        return instancia;
    }

    public Image getImagem(String caminho) {
        if (caminho == null || caminho.trim().isEmpty()) {
            return null;
        }

        Image img = null;

        SoftReference<Image> referencia = this.imagens.get(caminho);

        if (referencia != null) {
            img = referencia.get();
        }

        if (img == null) {
            try {
                img = new Image(getClass().getResource(caminho).toExternalForm());
                this.imagens.put(caminho, new SoftReference<>(img));
            } catch (Exception e) {
                System.out.println("imagem não encontrada para cache: " + caminho);
                return null;
            }
        }

        return img;
    }

    public void limparCache() {
        this.imagens.clear();
        System.gc();
    }
}