package application;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Utilitarios {

    // Método utilitário com a finalidade de simular um clique de botão em um node qualquer
    public static void animarClique(Node botao, Runnable runnable) {
        ScaleTransition st = new ScaleTransition(Duration.millis(80), botao);
        st.setToX(0.9);
        st.setToY(0.9);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.setOnFinished(e -> runnable.run());
        st.play();

        AudioManager.getInstancia().tocarEfeito("/resources/atividades/som-clique-allgame.mp3");
    }

    // Método utilitário para lógica de zoom in e zoom out ao passar o mouse por cima de um node
    public static void configurarClique(Node node) {

        node.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(100), node);
            st.setToX(1.02);
            st.setToY(1.02);
            st.play();
        });

        node.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(100), node);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });
    }
}
