package caso1;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Buffer {

	private ArrayList<Mensaje> buffer;
	
	private int tamano;
	
	private int contador;
	
	private int cantidadMensajes;
	
	public Buffer(int tamano, int cantidadMensajes) {
		this.tamano=tamano;
		contador=0;
		buffer = new ArrayList<Mensaje>();
		this.cantidadMensajes=cantidadMensajes;
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
	
	public int darCantidadConsultas() {
		return cantidadMensajes;
	}
	
	public void restarCantidadConsultas() {
		cantidadMensajes--;
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
			
			
			Buffer buffer = new Buffer(capacidadBuffer,numeroClientes*numeroConsultas);
			for(int i = 0; i < numeroClientes; i++)
			{
				Cliente cliente = new Cliente(buffer, numeroConsultas);
				cliente.start();
			}
			ArrayList<Servidor> servidores= new ArrayList<Servidor>();
			for(int i = 0; i < numeroServidores; i++)
			{
				Servidor servidor = new Servidor(buffer);
				servidores.add(servidor);
				servidor.start();
			}
			while(buffer.darCantidadConsultas()>0){
				
			}
			System.exit(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
