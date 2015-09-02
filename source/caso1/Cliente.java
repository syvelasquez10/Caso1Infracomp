package caso1;

public class Cliente extends Thread{
	
	private int numeroConsultas;
	
	private Buffer buffer;
	
	public Cliente(Buffer buffer, int numeroConsultas){
		this.numeroConsultas=numeroConsultas;
		this.buffer=buffer;
	}
	
	public void run(){
		for(int i=0; i < numeroConsultas; i++) {
			Mensaje mensaje = new Mensaje(""+i);
			buffer.enviar(mensaje);
			mensaje.dormir();
			System.out.println("Se leyó el mensaje: "+mensaje.toString());
		}
	}
}
