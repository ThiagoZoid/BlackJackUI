package src.main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import src.textures.RegistroDeTexturas;

public class Main extends Application{

	public static void main(String[] args) {
		System.out.println("Bem-vindo!");		
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		iniciarJogo(stage);
	}
	
	public static void iniciarJogo(Stage janela) throws IOException {
		FXMLLoader loaderMenu = new FXMLLoader(Main.class.getResource("/src/designs/DesignMenu.fxml"));
    	Pane painelMenu = (Pane)loaderMenu.load();
    	//ControladorMenu controladorMenu = (ControladorMenu)loaderMenu.getController();
    	
    	janela.getIcons().add(RegistroDeTexturas.naipes[1]);
    	janela.setResizable(false);
    	janela.setTitle("BlackJack!");
    	janela.setScene(new Scene(painelMenu));
    	janela.show();
	}
}
