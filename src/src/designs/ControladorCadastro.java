package src.designs;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import src.jogo.Jogador;

public class ControladorCadastro {

    @FXML
    private Label nomeJogador;
    @FXML
    private ImageView imagemJogador;
    @FXML
    private TextField campoNome;
    
    private String textoPadrao;
    private ControladorMenu controladorMenu;

    @FXML
    void aoClicarEmInicio(MouseEvent event) {
    	if(campoNome.getText().isEmpty()) {
    		Alert alert = new Alert(AlertType.ERROR, "Digite um nome para o jogador.", ButtonType.OK);
        	alert.showAndWait();
        	return;
    	}
    	String nomeFinal = atualizarNome();
    	
    	Stage janela = (Stage) campoNome.getScene().getWindow();
 		janela.close();
 		
    	controladorMenu.registrarJogador(new Jogador(nomeFinal));
    }
    
    @FXML
    void aoDigitar(KeyEvent event) {
    	atualizarNome();
    }
    
    String atualizarNome() {
    	if(campoNome.getText().isEmpty()) {
    		nomeJogador.setText(textoPadrao);
    		return textoPadrao;
    	}else {
    		nomeJogador.setText(campoNome.getText());
    		return campoNome.getText();
    	}
    }
    
    public void definirIndice(int id) {
    	textoPadrao = "Jogador #" + (id + 1);
    	campoNome.setPromptText(textoPadrao);
    	atualizarNome();
    }
    
    public void definirControladorMenu(ControladorMenu controladorMenu) {
    	this.controladorMenu = controladorMenu;
    }
    
}
