package caso1;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Buffer {

	private ArrayList<Mensaje> buffer;
	
	private int tamano;
	
	private int contador;
	
	public Buffer(int tamano) {
		this.tamano=tamano;
		contador=0;
		buffer = new ArrayList<Mensaje>();
	}
	
	public synchronized void enviar(Mensaje mensaje) {
		if(contador==tamano) {
			try {
				System.out.println("El cliente se duerme sobre el buffer");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		buffer.add(mensaje);
		contador++;
	}
	
	public synchronized Mensaje recibir() {
		if(buffer.isEmpty()) {
			return null;
		}
		Mensaje mensaje=buffer.remove(0);
		contador--;
		notify();
		return mensaje;
	}
	
	public static void main(String[] args) {
		try
		{
			Properties propiedades = new Properties();
			FileInputStream fis = new FileInputStream(new File("data/configuracion.properties"));
			propiedades.load(fis);
			fis.close();
			int capacidadBuffer = Integer.parseInt(propiedades.getProperty("capacidad.buffer"));
			int numeroClientes = Integer.parseInt(propiedades.getProperty("numero.clientes"));
			int numeroConsultas = Integer.parseInt(propiedades.getProperty("numero.consultas"));
			int numeroServidores = Integer.parseInt(propiedades.getProperty("numero.servidores"));	
			
			Buffer buffer = new Buffer(capacidadBuffer);
			for(int i = 0; i <= numeroClientes; i++)
			{
				Cliente cliente = new Cliente(buffer, numeroConsultas);
				cliente.start();
			}
			for(int i = 0; i <= numeroServidores; i++)
			{
				Servidor servidor = new Servidor(buffer);
				servidor.start();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
