package caso1;

public class Mensaje {

	private String mensaje;
	
	public Mensaje(String mensaje) {
		this.mensaje=mensaje;
	}
	
	public void responder() {
		mensaje+="; Respondido.";
	}
	
	public String toString() {
		return mensaje;
	}
}
