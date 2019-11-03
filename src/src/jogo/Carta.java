package src.jogo;

public class Carta {
		
	public byte idNumero;
	public byte idNaipe;
	
	public Carta(byte idNumero, byte idNaipe) {
		this.idNumero = idNumero;
		this.idNaipe = idNaipe;
	}
	
	public byte getIdNumero() {
		return idNumero;
	}
	
	public byte getIdNaipe() {
		return idNaipe;
	}
}
