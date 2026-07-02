package controller.overlays;

import application.CacheManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class CaixaDialogoController {

    // Interface - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @FXML private AnchorPane caixa;
    @FXML private ImageView icone;
    @FXML private Label nome;
    @FXML private Label texto;

    // Método - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    // Preenche as caixas de avisos ou diálogos com as informações via parâmetro
    public void preencherDados(String titulo, String mensagem, String caminhoImagemIcone) {
        this.nome.setText(titulo);
        this.texto.setText(mensagem);

        if (caminhoImagemIcone != null && !caminhoImagemIcone.isEmpty()) {

            try {
                Image imagem = CacheManager.getInstancia().getImagem(caminhoImagemIcone);
                this.icone.setImage(imagem);
            }

            catch (Exception e) {
                System.out.println("Imagem do ícone não encontrada no caminho: " + caminhoImagemIcone);
            }
        }
    }

    // Quando a caixa é clicada, fecha e podemos voltar a usar o pane base
    @FXML
    public void fecharCaixa(MouseEvent event) {

        Pane telaPai = (Pane) caixa.getParent();

        // remove caixa do pane atual
        if (telaPai != null) {
            telaPai.getChildren().remove(caixa);
        }
    }
}