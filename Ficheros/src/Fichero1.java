import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

/**
 * 
 */

/**
 * @author Brad Lopez
 *
 */
public class Fichero1 {
	static String fichero="fichero1.txt";
    static File f=new File(fichero);
    static BufferedReader bfr;
	static BufferedWriter bfw;
	static FileReader fr;
	static int cod=0;
	static ArrayList<String> buscar = new ArrayList<String>();
	static String[] split;
	static String dni;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(cod());

	try {
			if(f.exists()) {	    	   
				menu();
			}else {
	    		crearFichero();
	    	}
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
	    		    }
	}
	/**
	 * Crea el fichero en el que se va a trabajar
	 */
	private static void crearFichero() {
		try{
			f.createNewFile();
			JOptionPane.showMessageDialog(null, fichero+" generado");
	    }
	    catch(IOException e) {
	      System.out.println(e.getMessage()); 
	    }
	    
	    
		menu();
	}
	/**
	 * Genera el menu donde se contemplan las opciones de trabajo en el fichero 
	 */
	private static void menu() {
		Object[] opciones = { "Anadir", "Buscar", "Modificar" };
		int operacion = JOptionPane.showOptionDialog(null, "Elige una operacion", "Ficheros", 0,
				JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
		switch (operacion) {
		case 0:// Añadir
			anadirFichero();
			break;
		case 1:// Buscar
			recuperarFichero();
			break;
		case 2://Modificar
			modifcarFichero();
		default:
			JOptionPane.showMessageDialog(null, "Operacion terminada");
		}
	}
	/**
	 * Metodo que añade datos al fichero
	 */
	private static void anadirFichero()  {
	    try {
	    	String nombre=JOptionPane.showInputDialog("Introduzca el nombre");
	    	String dni=dni();
	    	bfw=new BufferedWriter(new FileWriter(f, true));
	    	bfw.write(dni+"#"+nombre);
	    	bfw.newLine();
	    	bfw.close();
	    	menu();
	    }
	    catch(IOException e) {
			e.printStackTrace();
	    }
		
    }
	/**
	 * Metodo que lee el fichero y busca los datos que se piden en el propio metodo
	 */
	private static void recuperarFichero() {
		try  {
			fr = new FileReader(fichero);
			bfr=new BufferedReader(fr);
			String contenido=bfr.readLine();
			while ((contenido)!=null) {
				buscar.add(contenido);
				contenido=bfr.readLine();
			}
			String datos=JOptionPane.showInputDialog("Introduce el dni de la persona que quieres encontrar");
			for(int i = 0; i < cod(); i++) {
				split = buscar.get(i).split("#");
				if(split[0].equals(datos)) {
					JOptionPane.showMessageDialog(null,"DNI: "+split[0] +"\n Nombre: "+split[1]);
				}
			}
			menu();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	/**
	 * Metodo que modifica los datos que especifica el usuario dentro del fichero
	 * 	crea un fichero temporal donde se escriben los datos y luego sobreescribe el fichero inicial
	 */
	private static void modifcarFichero() {
		File tempFile;
		try {
			tempFile = File.createTempFile("tempFichero","txt");
			bfw = new BufferedWriter(new FileWriter(tempFile));
			fr = new FileReader("fichero1.txt");
			bfr=new BufferedReader(fr);
			String data=bfr.readLine();
			while ((data)!=null) {
				buscar.add(data);
				data=bfr.readLine();
			}
			String datos=JOptionPane.showInputDialog("Introduce el dni de la persona que quieres modificar");
			for(int i = 0; i < cod(); i++) {
				split = buscar.get(i).split("#");
				if(split[0].equals(datos)) {
					String newN=JOptionPane.showInputDialog("Introduce el nuevo nombre");
					bfw.write(datos+"#"+newN);
					bfw.newLine();
				}else {
					bfw.write(buscar.get(i));
					bfw.newLine();
				}
			}
			bfw.close();
	            FileInputStream salida = new FileInputStream(tempFile);
	            FileOutputStream entrada = new FileOutputStream(f);

	            int c;
	            while( (c = salida.read() ) != -1)
	                entrada.write(c);
	            	
	            salida.close();
	            entrada.close();
				tempFile.deleteOnExit();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Valida que el dni tenga el valor adecuado
	 * @return
	 */
	private static String dni() {
		try {
			String dni=JOptionPane.showInputDialog("Intorduzca el DNI");
	        Pattern patron = Pattern.compile("[0-9]{7,8}[A-Z a-z]");
	        Matcher mat = patron.matcher(dni);
	        fr = new FileReader("fichero1.txt");
			bfr=new BufferedReader(fr);
			String data=bfr.readLine();
			while ((data)!=null) {
				buscar.add(data);
				data=bfr.readLine();
			}
			for(int i = 0; i < cod(); i++) {
				split = buscar.get(i).split("#");
				if(split[0].equals(dni)) {
			           dni=JOptionPane.showInputDialog("DNI ya esxistente \n Introduzca porfavor DNI no repetido"); 
				}
			}
	        while(!mat.matches()){
	           dni=JOptionPane.showInputDialog("Error Intorduzca un DNI correcto");
	           mat = patron.matcher(dni);
	        }
	        return dni;
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Operacion Terminada");
		}
		return null;
    }
	/**
	 * Metodo que cuenta el numero de lineas existentes en el fichero
	 * @return
	 */
	private static long cod() {
		try {
			fr = new FileReader("fichero1.txt");
			bfr=new BufferedReader(fr);
			long lNumeroLineas = 0;
			while ((fichero = bfr.readLine())!=null) {
			  lNumeroLineas++;
			}
			return lNumeroLineas;
		}catch (FileNotFoundException fnfe){
			  fnfe.printStackTrace();
		} catch (IOException ioe){
		  ioe.printStackTrace();
		}
		return (Long) null;
	}
	
	
}


