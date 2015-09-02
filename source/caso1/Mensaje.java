package caso1;

public class Mensaje {

	private String mensaje;
	
	public Mensaje(String mensaje) {
		this.mensaje=mensaje;
	}
	
	public synchronized void responder() {
		mensaje+="; Respondido.";
		System.out.println("El servidor despierta al cliente");
		notify();
	}
	
	public synchronized void dormir() {
		try {
			System.out.println("El cliente se duerme sobre el mensaje");
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return mensaje;
	}
}
