package src.controle;
import java.io.IOException;
import java.util.Random;

import src.enums.EnumEscolhaJogador;
import src.enums.EnumEstadoJogador;
import src.jogo.Baralho;
import src.jogo.Carta;
import src.jogo.Jogador;
import src.jogo.Mesa;

public abstract class Dealer {
	
	public static void iniciarJogo(Mesa mesa) {
		mesa.obterBaralhoNovo();
		int vencedor = 0;
		boolean blackjack = false;
		boolean empate = false;
		
		for(int i = 0; i<mesa.obterTotalJogadores(); i++) {
			for(int j = 0; j < Regras.obterCartasIniciais(); j++) {
				if(darCarta(mesa.obterBaralho(), mesa.obterJogador(i)) == EnumEstadoJogador.PONTUOU) {
					if(blackjack == true) {
						empate = true;
					}else {
						vencedor = i;
						blackjack = true;
					}
				}
			}
			mesa.atualizarControlador(true);
		}
		
		if(blackjack == true) {
			try {
				mesa.declararVencedor(vencedor, empate, true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void mostrarPontuacaoFinal(Jogador jogador) {
		
		
		String razao;
		switch(verificarVencedor(jogador)) {
			case ABAIXO:
				razao = "Parou";
				break;
			case PONTUOU:
				razao = "Fechou";
				break;
			case ACIMA:
				razao = "Estourou";
				break;
			default:
				razao = "???";
				break;
		}
		System.out.println("-----------------------------");	
		System.out.println("Pontuação final de " + jogador.obterNome() + ": " + 
				(jogador.obterSoma(false) == jogador.obterSoma(true) ? jogador.obterSoma(false) : 
					(jogador.obterSoma(false) + "/" + jogador.obterSoma(true))) + " (" + razao + ")");
		
	}
	
	public static void iniciarJogada(Mesa mesa) {
		Jogador jogador = mesa.obterJogadorDaVez();
		System.out.println("-----------------------------");
		System.out.println("Vez de: " + jogador.obterNome());
		mesa.atualizarControlador(true);
		jogador.mostrarMao();
}
	
	public static void fazerJogada(Mesa mesa, EnumEscolhaJogador escolha) {
		if(escolha == EnumEscolhaJogador.PEDIR) {
			if(darCarta(mesa.obterBaralho(), mesa.obterJogadorDaVez()) == EnumEstadoJogador.ABAIXO) {
				iniciarJogada(mesa);
			}else {
				mostrarPontuacaoFinal(mesa.obterJogadorDaVez());
				mesa.resumoDaJogada(true);
			}
		}else {
			mostrarPontuacaoFinal(mesa.obterJogadorDaVez());
			mesa.resumoDaJogada(false);
		}
	}
	
	public static void finalizarJogada(Mesa mesa) {
		mesa.passarVez();
		if(mesa.obterJogoFinalizado() == false) {
			iniciarJogada(mesa);
		}
		mesa.atualizarControlador(false);
	}
	
	public static EnumEstadoJogador darCarta(Baralho baralho, Jogador jogador) {
		Carta carta = baralho.pegarCarta();
		jogador.receberCarta(carta);
		return verificarVencedor(jogador);
	}
	
	public static EnumEstadoJogador verificarVencedor(Jogador jogador) {
		if(obterEstadoJogador(jogador, false) == EnumEstadoJogador.PONTUOU ||
				obterEstadoJogador(jogador, true) == EnumEstadoJogador.PONTUOU) {
			return EnumEstadoJogador.PONTUOU;
		}else if(obterEstadoJogador(jogador, false) == EnumEstadoJogador.ACIMA &&
				obterEstadoJogador(jogador, true) == EnumEstadoJogador.ACIMA) {
			return EnumEstadoJogador.ACIMA;
		}else {
			return EnumEstadoJogador.ABAIXO;
		}
	}
	
	public static void finalizarJogo(Mesa mesa) {
		int vencedor = 0;
		int pontuacao = mesa.obterJogador(0).obterSoma(false);
		boolean empate = false;
		for(int i = 0; i<mesa.obterTotalJogadores(); i++) {
			int soma = mesa.obterJogador(i).obterSoma(false);
			int soma_ = mesa.obterJogador(i).obterSoma(true);
			int somaFinal;
			boolean vencedorPassou = pontuacao > Regras.obterLimite();
			if((soma > pontuacao || vencedorPassou) && soma <= Regras.obterLimite() ||
					(soma_ > pontuacao || vencedorPassou) && soma_ <= Regras.obterLimite()) {
				if(soma <= Regras.obterLimite() || soma_ <= Regras.obterLimite()) {
					vencedor = i;
					if(soma > soma_) {
						if(soma <= Regras.obterLimite()) {
							somaFinal = soma;
						}else {
							somaFinal = soma_;
						}
					}else {
						if(soma_ <= Regras.obterLimite()) {
							somaFinal = soma_;
						}else {
							somaFinal = soma;
						}
					}
					empate = somaFinal == pontuacao;
					pontuacao = somaFinal;
				}
			}
			
		}
		
		if(pontuacao > Regras.obterLimite()) empate = true;
		try {
			mesa.declararVencedor(vencedor, empate, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Carta obterCartaAleatoria() {
		Random random = new Random();
		random.nextInt();
		return new Carta((byte)random.nextInt(Regras.obterTotalValores()), 
				(byte)random.nextInt(Regras.obterTotalNaipes()));
	}
	
	private static EnumEstadoJogador obterEstadoJogador(Jogador jogador, boolean flag) {
		int total = jogador.obterSoma(flag);
		if(total < Regras.obterLimite()) {
			return EnumEstadoJogador.ABAIXO;
		}else if(total > Regras.obterLimite()) {
			return EnumEstadoJogador.ACIMA;
		}else {
			return EnumEstadoJogador.PONTUOU;
		}
	}
}