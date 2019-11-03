package src.designs;

import java.io.IOException;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import src.controle.Dealer;
import src.controle.Regras;
import src.enums.EnumEscolhaJogador;
import src.enums.EnumEstadoJogador;
import src.jogo.Jogador;
import src.jogo.Mesa;
import src.main.Main;
import src.textures.RegistroDeTexturas;

public class ControladorJogo {


    @FXML
    private SplitPane plano;
    @FXML
    private StackPane caixaDeCartas;
    @FXML
    private Label nome;
    @FXML
    private Label pontos;
    @FXML
    private StackPane planoRealce;
    @FXML
    private Label textoRealce;
    @FXML
    private ImageView imagemRealce;
    @FXML
    private Label pontosRealce;
    @FXML
    private Circle botaoParar;
    @FXML
    private Circle botaoPedir;
    
    private Mesa mesa;
    
    private final int maxCartas = 5;
    private final double deslocamento = 30;
    private final double deslocamentoNovaCarta = 100;
    
    public void atualizar(boolean insercao) throws IOException {
    	if(mesa == null) return;
    	atualizarJogador();
    	atualizarPontos();
    	atualizarMao(insercao);
    }
    
    public void definirMesa(Mesa mesa) {
    	this.mesa = mesa;
    }
    
    void atualizarJogador() {
    	nome.setText(mesa.obterJogadorDaVez().obterNome());
    }
    
    void atualizarPontos() {
    	String soma = mesa.obterJogadorDaVez().obterSoma(false) + 
    			(mesa.obterJogadorDaVez().obterSoma(false) == mesa.obterJogadorDaVez().obterSoma(true) ?
    			"" : "/" + mesa.obterJogadorDaVez().obterSoma(true));
    	pontos.setText(soma);
    }
    
    Pane adicionarCarta(int indice) throws IOException {
    	FXMLLoader loaderCarta = new FXMLLoader(Main.class.getResource("/src/designs/DesignCarta.fxml"));
    	Pane painelCarta = (Pane)loaderCarta.load();
    	ControladorCarta controladorCarta = (ControladorCarta)loaderCarta.getController();
    	
    	caixaDeCartas.getChildren().add(painelCarta);
    	controladorCarta.definirCarta(mesa.obterJogadorDaVez().obterMao().lerCarta(indice));
    	
    	return painelCarta;
    }
    
    void atualizarMao(boolean insercao) throws IOException {
    	int qtdCartas = mesa.obterJogadorDaVez().obterMao().obterTamanho();
    	int j = (qtdCartas > maxCartas ? maxCartas : qtdCartas) - 1;
    	caixaDeCartas.getChildren().clear();
    	
    	for(int i = qtdCartas > maxCartas ? qtdCartas - maxCartas : 0, k = 0; 
    	insercao ? i<(qtdCartas-1) : i<qtdCartas; i++, k++) {
    		Pane painelCarta = adicionarCarta(i);
    		
    		double deslocamentoFinal = deslocamento*(j-k)*-1;
    		double xFinal = caixaDeCartas.getPrefWidth()-painelCarta.getPrefWidth()/2+
        			deslocamentoFinal+(deslocamento/2*j);
    		double yFinal = painelCarta.getPrefHeight()/5;
    		
        	painelCarta.setTranslateX(xFinal);
        	painelCarta.setTranslateY(yFinal);
        	//painelCarta.setTranslateY(caixaDeCartas.getHeight()/2-painelCarta.getPrefHeight()/2);
    	}
    	if(insercao == true) {
    		if(qtdCartas > 0) {
        		Pane painelPrimeiraCarta = adicionarCarta(qtdCartas-1);
        		
        		double xFinal = caixaDeCartas.getPrefWidth()-painelPrimeiraCarta.getPrefWidth()/2
        				+(deslocamento/2*j)+deslocamentoNovaCarta;
        		double yFinal = painelPrimeiraCarta.getPrefHeight()/5;
        		
        		painelPrimeiraCarta.setTranslateX(xFinal);
        		painelPrimeiraCarta.setTranslateY(yFinal);
        		
        		TranslateTransition transicao = new TranslateTransition();
        		transicao.setDuration(Duration.seconds(0.2F));
        		transicao.setToX(xFinal - deslocamentoNovaCarta);
        		transicao.setNode(painelPrimeiraCarta);
        		transicao.play();
        	}
    	}
    }
    
    void ativarTelaDeRealce(boolean ativar) {
    	if(ativar == true) {
    		plano.setEffect(new GaussianBlur());
    	}else {
    		plano.setEffect(null);
    	}
    	planoRealce.setVisible(ativar);
    }
    
    public void mostrarResultado(boolean empate, boolean blackjack) throws IOException {
    	Stage janelaResultado = new Stage();
		FXMLLoader loaderResultado = new FXMLLoader(Main.class.getResource("/src/designs/DesignResultado.fxml"));
    	Pane painelResultado = (Pane)loaderResultado.load();
    	ControladorResultado controladorResultado = (ControladorResultado)loaderResultado.getController();
    	
    	controladorResultado.definirResultado(mesa.obterJogadorVencedor(), empate, blackjack);
    	
    	janelaResultado.getIcons().add(RegistroDeTexturas.naipes[1]);
    	janelaResultado.setResizable(false);
    	janelaResultado.setScene(new Scene(painelResultado));
    	janelaResultado.show();
    	
    	if(blackjack == false) {
    		Stage janela = (Stage)plano.getScene().getWindow();
            janela.close();
    	}  	
    }
    
    public void realcarJogador(Jogador jogador, EnumEstadoJogador estado) {
    	ativarTelaDeRealce(true);
    	String razao;
    	switch(estado) {
		case ABAIXO:
			razao = "parou";
			imagemRealce.setImage(RegistroDeTexturas.estados[0]);
			if(jogador.obterSoma(false) > jogador.obterSoma(true)) {
				if(jogador.obterSoma(false) <= Regras.obterLimite()) {
					pontosRealce.setText("" + jogador.obterSoma(false));
				}else {
					pontosRealce.setText("" + jogador.obterSoma(true));
				}
			}else {
				if(jogador.obterSoma(true) <= Regras.obterLimite()) {
					pontosRealce.setText("" + jogador.obterSoma(true));
				}else {
					pontosRealce.setText("" + jogador.obterSoma(false));
				}
			}
			break;
		case PONTUOU:
			razao = "fechou";
			imagemRealce.setImage(RegistroDeTexturas.estados[1]);
			if(jogador.obterSoma(false) == Regras.obterLimite()) {
				pontosRealce.setText("" + jogador.obterSoma(false));
			}else {
				pontosRealce.setText("" + jogador.obterSoma(true));
			}
			break;
		case ACIMA:
			razao = "estourou";
			imagemRealce.setImage(RegistroDeTexturas.estados[2]);
			pontosRealce.setText("" + jogador.obterSoma(false));
			break;
		default:
			razao = "???";
			break;
    	}
    	textoRealce.setText(jogador.obterNome() + " " + razao + "!");
    }
    
    @FXML
    void aoParar(MouseEvent event) {
    	mesa.obterJogadorDaVez().fazerEscolha(mesa, EnumEscolhaJogador.PARAR);
    	ScaleTransition transicao = new ScaleTransition();
    	transicao.setDuration(Duration.seconds(.05F));
    	transicao.setAutoReverse(true);
    	transicao.setCycleCount(2);
    	transicao.setToX(.8F);
    	transicao.setToY(.8F);
    	transicao.setToZ(.8F);
    	transicao.setNode(botaoParar);
    	transicao.playFromStart();
    }
    
    @FXML
    void aoPedir(MouseEvent event) throws IOException {    	
    	mesa.obterJogadorDaVez().fazerEscolha(mesa, EnumEscolhaJogador.PEDIR);
    	ScaleTransition transicao = new ScaleTransition();
    	transicao.setDuration(Duration.seconds(.05F));
    	transicao.setAutoReverse(true);
    	transicao.setCycleCount(2);
    	transicao.setToX(.8F);
    	transicao.setToY(.8F);
    	transicao.setToZ(.8F);
    	transicao.setNode(botaoPedir);
    	transicao.playFromStart();
    }
    
    @FXML
    void aoClicarEmContinuar(MouseEvent event) {
    	ativarTelaDeRealce(false);
    	Dealer.finalizarJogada(mesa);
    }
    
    /**
    @FXML
    void aoClicarEmSair(MouseEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION, "Deseja mesmo sair da partida?", ButtonType.YES, ButtonType.CANCEL);
    	alert.showAndWait();

    	if (alert.getResult() == ButtonType.YES) {
    	    Stage janela = (Stage) pontos.getScene().getWindow();
    		janela.close();
    	}
    	
    }
    **/

}
