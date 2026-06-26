package application;

import controller.overlays.CaixaDialogoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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

    public static void mostrarDialogoWarn(AnchorPane telaAtual, String titulo, String mensagem, String caminhoImagem) {

        // Usa o marcador adicionado para substituir a caixa anterior pela nova
        telaAtual.getChildren().removeIf(node -> "caixaDialogoInjetada".equals(node.getId()));

        try {

            // Carrega fxml e adiciona um marcador para evitar múltiplas caixas
            FXMLLoader loader = new FXMLLoader(Utilitarios.class.getResource(RotasFixas.CAIXADIALOGO.getRotaFixa()));
            AnchorPane dialogoNode = loader.load();
            dialogoNode.setId("caixaDialogoInjetada");

            // Preenche a caixa e adiciona ao pane atual
            CaixaDialogoController controller = loader.getController();
            controller.preencherDados(titulo, mensagem, caminhoImagem);
            telaAtual.getChildren().add(dialogoNode);

        }

        catch (Exception e) {
            System.out.println("Erro ao executar a sobreposição rápida.");
            e.printStackTrace();
        }
    }

    public static void mostrarCardNotificacao(AnchorPane pane, String titulo, String mensagem, String caminhoIcone) {
        try {

            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/fxmls/overlays/caixaEvento.fxml"));
            Parent avisoVisual = loader.load();

            // Preenche a caixa e adiciona ao pane atual
            CaixaDialogoController controller = loader.getController();
            controller.preencherDados(titulo, mensagem, caminhoIcone);
            pane.getChildren().add(avisoVisual);

        }

        catch (Exception e) {
            System.out.println("Erro ao executar a sobreposição rápida.");
            e.printStackTrace();
        }
    }

}
