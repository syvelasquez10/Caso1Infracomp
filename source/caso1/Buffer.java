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
//		synchronized(mensaje) {
//			try {
//				System.out.println("El cliente añade el mensaje");
//				mensaje.wait();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	public synchronized Mensaje recibir() {
		if(buffer.isEmpty()) {
			return null;
		}
		Mensaje mensaje=buffer.remove(0);
		contador--;
		if(tamano==contador-1) {
			notify();
		}
		System.out.println("Trata de retornar el mensaje");
		return mensaje;
	}
	
	public static void main(String[] args) {
		try
		{
			Buffer buffer = new Buffer(1);
			Cliente cliente = new Cliente(buffer, 1);
			Servidor servidor = new Servidor(buffer);
			cliente.start();
			servidor.start();
//			Properties propiedades = new Properties();
//			FileInputStream fis = new FileInputStream(new File("data/configuracion.properties"));
//			propiedades.load(fis);
//			fis.close();
//			int numeroClientes = Integer.parseInt(propiedades.getProperty("numero.clientes"));
//			int numeroConsultas = Integer.parseInt(propiedades.getProperty("numero.consultas"));
//			int numeroServidores = Integer.parseInt(propiedades.getProperty("numero.servidores"));
//			
//			Buffer buffer = new Buffer(0);
//			ArrayList<Cliente> clientes= new ArrayList<Cliente>();
//			for (int i = 0; i < numeroClientes; i++)
//			{
//				Cliente clienteActual= new Cliente(buffer, 0);
//				clientes.add(clienteActual);
//			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
