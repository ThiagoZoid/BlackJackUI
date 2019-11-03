package src.jogo;

import java.io.IOException;

import src.controle.Dealer;
import src.controle.Regras;
import src.designs.ControladorJogo;

public class Mesa {

	private Jogador[] jogadores;
	private Jogador vencedor;
	private Baralho baralho;
	private ControladorJogo controladorJogo;
	private int vez = 0;
	
	public Mesa(Jogador[] jogadores, ControladorJogo controladorJogo) {
		this.jogadores = jogadores;
		this.controladorJogo = controladorJogo;
		controladorJogo.definirMesa(this);
	}
	
	public int obterTotalJogadores() {
		int total = 0;
		for(int i = 0; i<jogadores.length; i++) {
			if(jogadores[i] != null) total++;
		}
		return total;
	}
	
	public Jogador obterJogador(int i) {
		return jogadores[i];
	}
	
	public Jogador obterJogadorDaVez() {
		if(vez >= jogadores.length) {
			vez = jogadores.length-1;
		}
		return jogadores[vez];
	}
	
	public Jogador obterJogadorVencedor() {
		return vencedor;
	}
	
	public void declararVencedor(int i, boolean empate, boolean blackjack) throws IOException {
		vencedor = obterJogador(i);
		
		controladorJogo.mostrarResultado(empate, blackjack);
		
		System.out.println("=============================");
		if(blackjack) System.out.println("Blackjack!");
		System.out.println(empate ? "Empate!" : ("Vencedor: " + obterJogador(i).obterNome() + " (" + obterJogador(i).obterSoma(false) + 
				(obterJogador(i).obterSoma(false) != obterJogador(i).obterSoma(true) ? "/" + obterJogador(i).obterSoma(true) : "") + ")"));
	}
	
	public void atualizarControlador(boolean insercao) {
		try {
			controladorJogo.atualizar(insercao);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void obterBaralhoNovo() {
		int totalNaipes = Regras.obterTotalNaipes();
		int totalValores = Regras.obterTotalValores();
		baralho = new Baralho(new Carta[totalNaipes*totalValores]);
		
		for(byte i = 0; i<totalValores; i++) {
			for(byte j = 0; j<totalNaipes; j++) {
				baralho.adicionarCarta(new Carta(i, j));
			}
		}
		baralho.embaralhar();
	}
	
	public Baralho obterBaralho() {
		return baralho;
	}
	
	public void resumoDaJogada(boolean insercao) {
		atualizarControlador(insercao);
		controladorJogo.realcarJogador(obterJogadorDaVez(), Dealer.verificarVencedor(obterJogadorDaVez()));
	}
	
	public void passarVez() {
		if(obterJogoFinalizado() == true) return;
		vez++;
		if(vez == obterTotalJogadores()) {
			Dealer.finalizarJogo(this);
		}
	}
	
	public boolean obterJogoFinalizado() {
		return vencedor != null;
	}
	
}
