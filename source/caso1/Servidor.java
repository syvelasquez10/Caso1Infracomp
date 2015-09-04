package caso1;

public class Servidor extends Thread {
	
	private Buffer buffer;
	
	public Servidor(Buffer buffer) {
		this.buffer=buffer;
	}
	
	public void run() {
		while(buffer.darCantidadClientes()>0) {
			Mensaje mensaje=buffer.recibir();
			if(mensaje!=null) {
				mensaje.responder();
			}
			yield();
		}
	}
}
