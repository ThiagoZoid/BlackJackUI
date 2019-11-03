package src.designs;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import src.controle.Regras;
import src.jogo.Carta;
import src.jogo.Valor;
import src.textures.RegistroDeTexturas;

public class ControladorCarta {

    @FXML
    private Label nomeTopo;
    @FXML
    private Label nomeBaixo;
    @FXML
    private ImageView naipe;
    @FXML
    private Label numeroPontos;
    
    public void definirCarta(Carta carta) {
    	naipe.setImage(RegistroDeTexturas.naipes[carta.idNaipe]);
    	Valor valor = Regras.obterValor(carta);
    	nomeTopo.setText(valor.obterNome());
    	nomeBaixo.setText(valor.obterNome());
    	if(valor.obterNumero_() != 0) {
    		numeroPontos.setText(valor.obterNumero() + "/" + valor.obterNumero_());
    	}else {
    		numeroPontos.setText("" + valor.obterNumero());
    	}
    	
    	if(carta.idNaipe % 2 == 0) {
    		nomeTopo.setTextFill(Color.web("#505050"));
    		nomeBaixo.setTextFill(Color.web("#505050"));
    	}else {
    		nomeTopo.setTextFill(Color.web("#FF3232"));
    		nomeBaixo.setTextFill(Color.web("#FF3232"));
    	}
    	
    }
}
