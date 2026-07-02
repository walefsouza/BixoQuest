package application;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class BixoQuestMain extends Application {

    // Start  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // Inicializa o primeiro cenário após o jogador dar start

    @Override
    public void start(Stage stage) {

        // Carregando fontes
        Font.loadFont(getClass().getResourceAsStream("/resources/fontes/Signwood-Regular.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/resources/fontes/upheavtt.ttf"), 24);

        // Stage inicial
        SceneManager.iniciar(stage);
        SceneManager.navegar(RotasFixas.TELAJOGAR.getRotaFixa());
    }

    // Main  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public static void main(String[] args) {
        launch(args);
    }
}
