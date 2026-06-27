package controller.locais;

import application.SceneManager;
import application.SessaoSingleton;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class TransicaoController {

    @FXML
    public void transferirTelaRota(MouseEvent event) {

        // Recupera a rota de onde o jogador veio antes da transição
        String rotaAnterior = SessaoSingleton.getInstancia().getUltimaRota();

        if (rotaAnterior != null) {
            SceneManager.navegar(rotaAnterior);
        }
    }
}