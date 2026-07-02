package application;

import javafx.scene.image.Image;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class CacheManager {

    // Atributos - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private static CacheManager instancia;
    private Map<String, SoftReference<Image>> imagens;

    // Singleton - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    private CacheManager() {
        this.imagens = new HashMap<>();
    }

    public static CacheManager getInstancia() {
        if (instancia == null) {
            instancia = new CacheManager();
        }
        return instancia;
    }

    // Métodos - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    // Acessa o map estático em memória e retorna a imagem válida
    public Image getImagem(String caminho) {

        // Vale apenas se não for null
        if (caminho == null || caminho.trim().isEmpty()) {
            return null;
        }

        Image img = null;
        SoftReference<Image> referencia = this.imagens.get(caminho);

        // Verifica se a imagem já foi instanciada
        if (referencia != null) {
            img = referencia.get();
        }

        // Se a imagem não estiver na memória cache, cria e adiciona
        if (img == null) {

            try {
                img = new Image(getClass().getResource(caminho).toExternalForm());
                this.imagens.put(caminho, new SoftReference<>(img));
            }

            catch (Exception e) {
                System.out.println("imagem não encontrada para cache: " + caminho);
                return null;
            }
        }

        return img;
    }

    // Limpa hash map que guarda as referência carregadas
    public void limparCache() {
        this.imagens.clear();
        System.gc();
    }
}