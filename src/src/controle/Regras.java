package src.controle;

import src.jogo.Carta;
import src.jogo.Valor;

public abstract class Regras {
	private static String[] naipes = {"Copas", "Ouros", "Paus", "Espadas"};
	private static Valor[] valores = {
			new Valor("A", 1, 11),
			new Valor("2", 2),
			new Valor("3", 3),
			new Valor("4", 4),
			new Valor("5", 5),
			new Valor("6", 6),
			new Valor("7", 7),
			new Valor("8", 8),
			new Valor("9", 9),
			new Valor("10", 10),
			new Valor("J", 10),
			new Valor("Q", 10),
			new Valor("K", 10),
			};

	private static int minJogadores = 1;
	private static int maxJogadores = 10;
	
	private static int limite = 21;
	private static int cartasIniciais = 2;
	private static int maxCartasPorMao = 10;
	
	public static Valor obterValor(Carta carta) {
		return valores[carta.idNumero];
	}
	
	public static String obterNaipe(Carta carta) {
		return naipes[carta.idNaipe];
	}
	
	public static int obterMinJogadores() {
		return minJogadores;
	}
	
	public static int obterMaxJogadores() {
		return maxJogadores;
	}
	
	public static Valor[] obterValores() {
		return valores;
	}
	
	public static String[] obterNaipes() {
		return naipes;
	}
	
	public static int obterTotalValores() {
		return valores.length;
	}
	
	public static int obterTotalNaipes() {
		return naipes.length;
	}
	
	public static int obterLimite() {
		return limite;
	}
	
	public static int obterCartasIniciais() {
		return cartasIniciais;
	}
	
	public static int obterMaxCartasPorMao() {
		return maxCartasPorMao;
	}
}
