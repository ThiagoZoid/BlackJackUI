package src.jogo;


import src.controle.Dealer;
import src.controle.Regras;
import src.enums.EnumEscolhaJogador;

public class Jogador {
	private String nome;
	private Baralho mao;
	
	public Jogador(String nome) {
		this.nome = nome;
	}
	
	public void novaMao() {
		mao = new Baralho(new Carta[Regras.obterMaxCartasPorMao()]);
	}
	
	public Baralho obterMao() {
		return mao;
	}
	
	public String obterNome() {
		return nome;
	}
	
	public int obterSoma(boolean flag) {
		int soma = 0;
		for(int i = 0; i<mao.obterTamanho(); i++) {
			soma += flag && Regras.obterValor(mao.lerCarta(i)).obterNumero_() != 0 ? Regras.obterValor(mao.lerCarta(i)).obterNumero_() :
				Regras.obterValor(mao.lerCarta(i)).obterNumero();
		}
		return soma;
	}
	
	public void receberCarta(Carta carta) {
		Valor valor = Regras.obterValor(carta);
		System.out.println(obterNome() + " recebeu: " + valor.obterNome() + " de " + Regras.obterNaipe(carta) + 
				" (" + valor.obterNumero() + (valor.obterNumero_() != 0 ? "/" + valor.obterNumero_() : "") + ")");
		mao.adicionarCarta(carta);
	}
	
	public void fazerEscolha(Mesa mesa, EnumEscolhaJogador escolha) {
		Dealer.fazerJogada(mesa, escolha);
	}
	
	public void mostrarMao() {
		System.out.println("Cartas: ");
		for(int i = 0; i<mao.obterTamanho(); i++) {
			Carta carta = mao.lerCarta(i);
			if(Regras.obterValor(carta).obterNumero_() == 0) {
				System.out.println("-> " + Regras.obterValor(carta).obterNome() + " de " + 
						Regras.obterNaipe(carta) + " (" + Regras.obterValor(carta).obterNumero() + ")");
			}else {
				System.out.println("-> " + Regras.obterValor(carta).obterNome() + " de " + 
						Regras.obterNaipe(carta) + " (" + Regras.obterValor(carta).obterNumero() + "/" + Regras.obterValor(carta).obterNumero_() + ")");
			}
		}
		if(obterSoma(false) == obterSoma(true)) {
			System.out.println("Total: " + obterSoma(false));
		} else {
			System.out.println("Total: " + obterSoma(false) + "/" + obterSoma(true));
		}
		
	}
	
}
