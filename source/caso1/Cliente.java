package caso1;

public class Cliente extends Thread{

	private int numeroConsultas;

	private Buffer buffer;

	private int id;

	public Cliente(Buffer buffer, int numeroConsultas, int id) {
		this.numeroConsultas=numeroConsultas;
		this.buffer=buffer;
		this.id=id;
	}

	public void run() {
		for(int i=0; i < numeroConsultas; i++) {
			Mensaje mensaje = new Mensaje(""+i);
			buffer.enviar(mensaje);
			System.out.println("Se leyó el mensaje de-"+id+"-: -"+mensaje.toString());
			buffer.restarCantidadMensajes();
		}
	}
}
