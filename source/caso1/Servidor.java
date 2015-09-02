package caso1;

public class Servidor extends Thread {
	
	private Buffer buffer;
	
	public Servidor(Buffer buffer) {
		this.buffer=buffer;
	}
	
	public void run() {
		for(int i=0; i <= 100; i++) {
			Mensaje mensaje=buffer.recibir();
			while(mensaje==null) {
				mensaje = buffer.recibir();
				yield();
			}
			mensaje.responder();
		}
	}
}
