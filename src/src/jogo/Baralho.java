package src.jogo;
import java.util.Random;

public class Baralho {
	private Carta[] cartas;
	
	public Baralho(Carta[] cartas){
		this.cartas = cartas;
	}
	
	private void trocar(Carta[] a, int i, int j) {
		Carta h = a[i];
		a[i] = a[j];
		a[j] = h;
	}
	
	public boolean estaVazio() {
		return obterTamanho() == 0;
	}
	
	public int obterTamanho() {
		int tamanho = 0;
		for(int i = 0; i<cartas.length; i++) {
			if(cartas[i] != null) tamanho++;
		}
		return tamanho;
	}
	
	public void adicionarCarta(Carta carta) {
		cartas[obterTamanho()] = carta;
	}
	
	public Carta lerCarta(int carta) {
		return cartas[carta];
	}
	
	public Carta pegarCarta() {
		if (estaVazio()) return null;
		int ultimaCarta = obterTamanho() - 1;
		Carta carta = cartas[ultimaCarta];
		cartas[ultimaCarta] = null;
		return carta;
	}
	
	public void embaralhar() {
		if (estaVazio()) return;
		int n = cartas.length;
		Random random = new Random();
		random.nextInt();
		for(int i=0; i<n; i++) {
			int a = i + random.nextInt(n - i);
			trocar(cartas, i, a);
		}
	}
	
}
