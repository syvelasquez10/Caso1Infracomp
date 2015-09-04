package caso1;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Buffer {

	private ArrayList<Mensaje> buffer;
	
	private int tamano;
	
	private int contador;
	
	private int cantidadClientes;
	
	public Buffer(int tamano, int cantidadClientes) {
		this.tamano=tamano;
		this.cantidadClientes=cantidadClientes;
		contador=0;
		buffer = new ArrayList<Mensaje>();
	}
	
	public synchronized void enviar(Mensaje mensaje) {
		if(contador==tamano) {
			try {
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
	
	public synchronized int darCantidadClientes() {
		return cantidadClientes;
	}
	
	public synchronized void restarCantidadClientes() {
		cantidadClientes--;
	}
	
	public static void main(String[] args) {
		try {
			Properties propiedades = new Properties();
			FileInputStream fis = new FileInputStream(new File("data/configuracion.properties"));
			propiedades.load(fis);
			fis.close();
			int capacidadBuffer = Integer.parseInt(propiedades.getProperty("capacidad.buffer"));
			int numeroClientes = Integer.parseInt(propiedades.getProperty("numero.clientes"));
			int numeroConsultas = Integer.parseInt(propiedades.getProperty("numero.consultas"));
			int numeroServidores = Integer.parseInt(propiedades.getProperty("numero.servidores"));	
			Buffer buffer = new Buffer(capacidadBuffer,numeroClientes);
			for(int i = 0; i < numeroClientes; i++) {
				Cliente cliente = new Cliente(buffer, numeroConsultas);
				cliente.start();
			}
			ArrayList<Servidor> servidores= new ArrayList<Servidor>();
			for(int i = 0; i < numeroServidores; i++) {
				Servidor servidor = new Servidor(buffer);
				servidores.add(servidor);
				servidor.start();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
