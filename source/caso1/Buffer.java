package caso1;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Buffer {

	private ArrayList<Mensaje> buffer;

	private int tamano;

	private int cantidad;

	public Buffer(int tamano, int cantidad) {
		this.tamano=tamano;
		this.cantidad=cantidad;
		buffer = new ArrayList<Mensaje>();
	}

	public void enviar(Mensaje mensaje) {
		synchronized (this) {
			if(buffer.size()==tamano) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		synchronized (mensaje) {
			synchronized (this) {
				buffer.add(mensaje);
			}
			try {
				mensaje.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized Mensaje recibir() {
		if(this.buffer.isEmpty()) {
			return null;
		}
		Mensaje mensaje= this.buffer.remove(0);
		notify();
		return mensaje;
	}

	public synchronized int darCantidadMensajes() {
		return cantidad;
	}

	public synchronized void restarCantidadMensajes() {
		cantidad--;
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
			Buffer buffer = new Buffer(capacidadBuffer,numeroClientes*numeroConsultas);
			for(int i = 0; i < numeroClientes; i++) {
				new Cliente(buffer, numeroConsultas, i).start();
			}
			for(int i = 0; i < numeroServidores; i++) {
				new Servidor(buffer).start();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
