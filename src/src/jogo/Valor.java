package src.jogo;

public class Valor{
	private String nome;
	private int numero;
	private int numero_;
	
	public Valor(String nome, int numero) {
		this.nome = nome;
		this.numero = numero;
	}

	public Valor(String nome, int numero, int numero_) {
		this.nome = nome;
		this.numero = numero;
		this.numero_ = numero_;
	}
	
	public String obterNome() {
		return nome;
	}
	
	public int obterNumero() {
		return numero;
	}
	
	public int obterNumero_() {
		return numero_;
	}
}
