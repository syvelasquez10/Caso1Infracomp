package caso1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Test {

	public static void main( String[] args ) 
	{

		try 
		{
			Test test = new Test( "./data/text.txt");
		} 
		catch (Exception e) {

			e.printStackTrace();
		}

	}

	public Test( String rutaArchivoEntrada) throws Exception
	{
		File archivoE = new File( rutaArchivoEntrada );

		if( archivoE.exists( ) )
		{
			try
			{

				BufferedReader lector = new BufferedReader( new FileReader( archivoE ) );
				String linea = lector.readLine( );
				int cantidad=Integer.parseInt(linea);
				int[] clientes = new int[cantidad];
				for (int j = 0; j < cantidad; j++) {
					clientes[j]=0;
				}
				linea=lector.readLine( );
				while( linea != null )
				{
					String[] l= linea.split("-");
					
					for (int j = 0; j < cantidad; j++) {
						if(l[3].equals(j+""))
						{
							clientes[j]=clientes[j]+1;
						}
					}
					linea = lector.readLine( );
				}
				lector.close( );
				for (int j = 0; j < cantidad; j++) {
					System.out.println(clientes[j]);
				}
			}
			catch( FileNotFoundException e )
			{
				e.printStackTrace();
			}
			catch( IOException e )
			{
				e.printStackTrace();
			}
		}
	}

}
