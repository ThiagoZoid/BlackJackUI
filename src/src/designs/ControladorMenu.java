package src.designs;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import src.controle.Dealer;
import src.controle.Regras;
import src.jogo.Jogador;
import src.jogo.Mesa;
import src.main.Main;
import src.textures.RegistroDeTexturas;

public class ControladorMenu {

    @FXML
    private Label numJogadores;
  
    private Jogador[] jogadores;
    private int jogadoresRegistrados = 0;
    private int qtdJogadores = 2;

    @FXML
    void aoClicarEmCreditos(MouseEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION, "Centro Universitário IESB"
    			+ "\nThiago Antunes - 1812130068"
    			+ "\nEnio Ribeiro - 1812082050", ButtonType.OK);
    	alert.showAndWait();
    }

    void prosseguirRegistro() throws IOException {
    	if(jogadoresRegistrados < qtdJogadores) {
    		Stage janelaCadastro = new Stage();
    		FXMLLoader loaderCadastro = new FXMLLoader(Main.class.getResource("/src/designs/DesignCadastro.fxml"));
        	Pane painelCadastro = (Pane)loaderCadastro.load();
        	ControladorCadastro controladorCadastro = (ControladorCadastro)loaderCadastro.getController();
        	
        	controladorCadastro.definirControladorMenu(this);
        	controladorCadastro.definirIndice(jogadoresRegistrados);
        	
        	janelaCadastro.getIcons().add(RegistroDeTexturas.naipes[1]);
        	janelaCadastro.setResizable(false);
        	janelaCadastro.setScene(new Scene(painelCadastro));
        	janelaCadastro.show();
        	
    	}else {
    		Stage janelaJogo = new Stage();
    		FXMLLoader loaderJogo = new FXMLLoader(Main.class.getResource("/src/designs/DesignJogo.fxml"));
        	Pane painelJogo = (Pane)loaderJogo.load();
        	ControladorJogo controladorJogo = (ControladorJogo)loaderJogo.getController();
        	
        	for(int i = 0; i<qtdJogadores; i++) {
    			jogadores[i].novaMao();
    		}
        	
        	Mesa mesa = new Mesa(jogadores, controladorJogo);
        	
        	Dealer.iniciarJogo(mesa);
      	
        	if(mesa.obterJogoFinalizado() == false){
        		janelaJogo.getIcons().add(RegistroDeTexturas.naipes[1]);
            	janelaJogo.setResizable(false);
            	janelaJogo.setScene(new Scene(painelJogo));
            	janelaJogo.show();
        	}        	
    	}
    }
    
    public void registrarJogador(Jogador jogador) {
    	jogadores[jogadoresRegistrados++] = jogador;
    	try {
			prosseguirRegistro();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void aoClicarEmInicio(MouseEvent event) throws IOException {

    	Stage janela = (Stage)numJogadores.getScene().getWindow();
    	janela.close();
    	
    	jogadores = new Jogador[qtdJogadores];
    	prosseguirRegistro();
    }

    @FXML
    void aoClicarEmMais(MouseEvent event) {
    	if(qtdJogadores < Regras.obterMaxJogadores()) {
    		qtdJogadores++;
    		atualizarNumJogadores();
    	}
    }

    @FXML
    void aoClicarEmMenos(MouseEvent event) {
    	if(qtdJogadores > Regras.obterMinJogadores()) {
    		qtdJogadores--;
    		atualizarNumJogadores();
    	}
    }
    
    void atualizarNumJogadores() {
    	numJogadores.setText("" + qtdJogadores);
    }
}
