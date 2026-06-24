package application;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class BixoQuestMain extends Application {

    @Override
    public void start(Stage stage) {

        Font.loadFont(getClass().getResourceAsStream("/resources/fontes/Signwood-Regular.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/resources/fontes/upheavtt.ttf"), 24);

        SceneManager.iniciar(stage);
        SceneManager.navegar(RotasFixas.TELAJOGAR.getRotaFixa());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
