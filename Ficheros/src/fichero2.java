import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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

public class fichero2 {

	static String fichero="fichero2.txt";
    static File f=new File(fichero);
    static BufferedReader bfr;
	static BufferedWriter bfw;
	static FileReader fr;
	static int cod=0;
	static ArrayList<String> buscar = new ArrayList<String>();
	static ArrayList<String>campos=new ArrayList<String>();
	static String[] split;
	static String buscDat;
	
	static int cont;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			if(f.exists()) {	
				menu1();
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
	    
	    
		menu1();
	}
	private static void menu1() {
		try {
			String numCampos=JOptionPane.showInputDialog("Introduce el numero de campos que quieras");
			if(isNumeric(numCampos)){
				int num = Integer.parseInt(numCampos);
				for(int i=0;i<num;i++) {
					String campoNom=JOptionPane.showInputDialog("Nombre del campo");
					campos.add(campoNom);
				}
				menu2();
			}else if(numCampos==null) {
				JOptionPane.showMessageDialog(null, "Operacion terminada");
			}else {
				JOptionPane.showMessageDialog(null, "Introduzca un numero por favor");
				menu1();
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Operacion terminada");

		}

	}
	
	/**
	 * Genera el menu donde se contemplan las opciones de trabajo en el fichero 
	 */
	private static void menu2() {
		Object[] opciones = { "Anadir", "Buscar", "Modificar","Marcar","Borrar Marcados" };
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
		case 3://marcar registros
			marcarFichero();
			break;
		case 4://elimina marcados
			borrarFichero();
			break;
		default:
			JOptionPane.showMessageDialog(null, "Operacion terminada");
		}
	}
	/**
	 * Metodo que añade datos al fichero
	 */
	private static void anadirFichero()  {
		try {
			bfw=new BufferedWriter(new FileWriter(f,true));
			bfw.write(String.valueOf(cod()));
			bfw.close();
			for(int i=0;i<campos.size();i++) {
			    String nombre=JOptionPane.showInputDialog("Introduzca "+campos.get(i));
				bfw=new BufferedWriter(new FileWriter(f,true));
				bfw.write("#"+campos.get(i)+":"+nombre);
				bfw.close(); 
			}
			bfw=new BufferedWriter(new FileWriter(f,true));
			bfw.newLine();
			bfw.close();
			menu2();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/**
	 * Metodo que lee el fichero y busca los datos que se piden en el propio metodo
	 */
	private static void recuperarFichero() {
		try  {
			fr = new FileReader("fichero2.txt");
			bfr=new BufferedReader(fr);
			String contenido=bfr.readLine();
			while ((contenido)!=null) {
				cont++;
				buscar.add(contenido);
				contenido=bfr.readLine();
			}
			String datos=JOptionPane.showInputDialog("Introduce el codigo de la persona que quiera encontrar");
			for(int i = 0; i < cont; i++) {
				split = buscar.get(i).split("#");
				if(split[0].equals(datos)) {
					buscDat = "";
					for(int j=1;j<split.length;j++) {
						buscDat=buscDat+split[j]+"\n";
					}
					
				}
			}
			JOptionPane.showMessageDialog(null, "Codigo: "+datos+"\n"+buscDat);
			menu2();
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
			fr = new FileReader("fichero2.txt");
			bfr=new BufferedReader(fr);
			String contenido=bfr.readLine();
			while ((contenido)!=null) {
				cont++;
				buscar.add(contenido);
				contenido=bfr.readLine();
			}
			String datos=JOptionPane.showInputDialog("Introduce el codigo de la persona que quieras modificar");
			for(int i = 0; i < cod(); i++) {
				split = buscar.get(i).split("#");
				if(split[0].equals(datos)) {
					bfw.write(String.valueOf(i));
					for(int j=1;j<split.length;j++) {
						String[] split1=split[j].split(":");
						String newN=JOptionPane.showInputDialog("Introduce nuevo "+split1[0]);
						bfw.write("#"+split1[0]+":"+newN);
					}
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
			menu2();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Marca un registro a eleccion del usuario con un * al princpio
	 */
	private static void marcarFichero() {
		File tempFile;
		try {
			tempFile = File.createTempFile("tempFichero","txt");
			bfw = new BufferedWriter(new FileWriter(tempFile));
			fr = new FileReader("fichero2.txt");
			bfr=new BufferedReader(fr);
			String contenido=bfr.readLine();
			while ((contenido)!=null) {
				cont++;
				buscar.add(contenido);
				contenido=bfr.readLine();
			}
			String datos=JOptionPane.showInputDialog("Introduce el codigo de la persona que quieras borrar");
			for(int i = 0; i < cod(); i++) {
				split = buscar.get(i).split("#");
				if(split[0].equals(datos)) {
					bfw.write("*"+buscar.get(i));
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
	
	private  static void borrarFichero() {
		File tempFile;
		try {
			tempFile = File.createTempFile("tempFichero","txt");
			bfw = new BufferedWriter(new FileWriter(tempFile));
			fr = new FileReader("fichero2.txt");
			bfr=new BufferedReader(fr);
			String contenido=bfr.readLine();
			while ((contenido)!=null) {
				cont++;
				buscar.add(contenido);
				contenido=bfr.readLine();
			}
			for(int i = 0; i < cod(); i++) {
				String var="*";
				if(buscar.get(i).contains(var)) {
					bfw.write("");
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
				menu2();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Validacion campos es numero
	 * @param cadena
	 * @return
	 */
	 public static boolean isNumeric(String cadena) {

	        boolean resultado;

	        try {
	            Integer.parseInt(cadena);
	            resultado = true;
	        } catch (NumberFormatException excepcion) {
	            resultado = false;
	        }

	        return resultado;
	    }
	 /**
		 * Metodo que cuenta el numero de lineas existentes en el fichero
		 * @return
		 */
	 private static long cod() {
			try {
				fr = new FileReader("fichero2.txt");
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
			return  (Long) null;
		}
		
	
}


