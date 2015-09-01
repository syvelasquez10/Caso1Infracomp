package caso1;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Buffer {

	public static void main(String[] args) {
		try
		{
			Properties propiedades = new Properties();
			FileInputStream fis = new FileInputStream(new File("data/configuracion.properties"));
			propiedades.load(fis);
			fis.close();
			int numeroClientes = Integer.parseInt(propiedades.getProperty("numero.clientes"));
			int numeroConsultas = Integer.parseInt(propiedades.getProperty("numero.consultas"));
			int numeroServidores = Integer.parseInt(propiedades.getProperty("numero.servidores"));
			
			Buffer buffer = new Buffer();
			ArrayList<Cliente> clientes= new ArrayList<Cliente>();
			for (int i = 0; i < numeroClientes; i++)
			{
				Cliente clienteActual= new Cliente(buffer);
				clientes.add(clienteActual);
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
