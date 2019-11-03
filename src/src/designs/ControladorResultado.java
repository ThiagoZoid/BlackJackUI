package src.designs;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import src.jogo.Jogador;
import src.main.Main;

public class ControladorResultado {

    @FXML
    private Label nomeVencedor;

    @FXML
    private Label textoVencedor;

    public void definirResultado(Jogador jogador, boolean empate, boolean blackjack) {
    	if(empate == true || blackjack == true) {
    		if(blackjack == true) {
    			textoVencedor.setText("BLACKJACK!");
    		}else {
    			textoVencedor.setVisible(false);
    		}
    	}
    	
    	if(empate == false) {
    		nomeVencedor.setText(jogador.obterNome());
    	}else {
    		nomeVencedor.setText("EMPATE!");
    	}
    }
    
    @FXML
    void aoClicarEmJogar(MouseEvent event) throws IOException {
    	Stage janela = (Stage)nomeVencedor.getScene().getWindow();
    	janela.close();
    	Stage novaJanela = new Stage();
    	Main.iniciarJogo(novaJanela);
    }

}
