package caso1;

public class Servidor extends Thread {
	
	private Buffer buffer;
	
	public Servidor(Buffer buffer) {
		this.buffer=buffer;
	}
	
	public void run() {
		Mensaje mensaje=buffer.recibir();
		while(mensaje==null) {
			mensaje = buffer.recibir();
		}
		mensaje.notify();
		mensaje.responder();
	}
}
