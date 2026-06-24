package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SceneManager {

    private static Stage palcoPrincipal;
    private static String telaAtual;

    public static void iniciar(Stage stage) {
        palcoPrincipal = stage;
        palcoPrincipal.setResizable(false);
    }

    // Abstrai a lógica de mudança de cena por meio do padrão facade
    public static boolean navegar(String destino) {

        try {
            Parent tela = FXMLLoader.load(SceneManager.class.getResource(destino));
            SessaoSingleton.getInstancia().setUltimaRota(telaAtual);

            palcoPrincipal.setScene(new Scene(tela));
            palcoPrincipal.show();

            telaAtual = destino;
            return true;
        }

        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void abrirModal(String caminhoFxml) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(caminhoFxml));
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.initOwner(palcoPrincipal);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.TRANSPARENT);
            modalStage.setResizable(false);

            // Sincroniza posição e tamanho com a janela principal
            modalStage.setX(palcoPrincipal.getX());
            modalStage.setY(palcoPrincipal.getY());
            modalStage.setWidth(palcoPrincipal.getWidth());
            modalStage.setHeight(palcoPrincipal.getHeight());

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            modalStage.setScene(scene);
            modalStage.showAndWait();

        } catch (Exception e) {
            System.out.println("Erro ao carregar o modal: " + caminhoFxml);
            e.printStackTrace();
        }
    }
}
