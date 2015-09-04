package caso1;

public class Mensaje {

	private String mensaje;
	
	public Mensaje(String mensaje) {
		this.mensaje=mensaje;
	}
	
	public synchronized void responder() {
		mensaje+="; Respondido.";
		notify();
	}
	
	public synchronized void dormir() {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return mensaje;
	}
}
