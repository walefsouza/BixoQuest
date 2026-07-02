package controller.locais;

import application.SceneManager;
import application.SessaoSingleton;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class TransicaoController {

    // Método - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // Consiste em abrir uma tela nova e permitir voltar para anterior ao fim
    @FXML
    public void transferirTelaRota(MouseEvent event) {

        // Recupera a rota de onde o jogador veio antes da transição
        String rotaAnterior = SessaoSingleton.getInstancia().getUltimaRota();

        if (rotaAnterior != null) {
            SceneManager.navegar(rotaAnterior);
        }
    }
}