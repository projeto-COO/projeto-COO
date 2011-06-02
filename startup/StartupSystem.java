package startup;

import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import moviesData.*;
import System.ManageMovies;
import System.TicketSale;

/**
 * Classe StartupSystem: Ela gerencia todo o sistema
 * 
 *
 */
@SuppressWarnings("unused")
public class StartupSystem {

	protected static Map<Integer,MovieData> mapMovieData;
	protected static Map<Integer,RoomData> mapRoomData;
	protected static Map<Integer, Map<String,SessionData>> mapSessionData;
	protected static HistoricData historic = new HistoricData();
	/**
	 * Metodo responsavel pelo escolha de direcionamento de tarefa, como gerenciar, historicos e
	 * vender ingressos
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("\t** Cine & Pipoca ***");
		downloadData();
		
		Scanner scanner = new Scanner(System.in);
		int option = 0;
		boolean test = false;
		boolean repeate = true;
		
		do{
			do {
				try {
					
					System.out.println("\nO que voce deseja fazer: ");
					System.out.println("\tGerenciar o sistema (1)");
					if(!mapSessionData.isEmpty())System.out.println("\tVender entradas para o cinema (2)");
					System.out.println("\tHistorico de Gerenciamento(3)");
					System.out.println("\tHistorico de Compra(4)");
					System.out.println("\tSair do Sistema(5)");
					System.out.print("Digite o codigo: ");
					option = scanner.nextInt();
					
					if(option<1 || option>5)test = false;
					else test = true;
				} catch (InputMismatchException e) {
					System.out.println("Digite novamente.");
					test = false;
					scanner = new Scanner(System.in);					
				} catch (IllegalArgumentException e) {
					System.out.println("Entre com valores validos.");
					scanner.reset();
					test = false;
					scanner = new Scanner(System.in);
				}	
			} while (!test);
			
			switch (option) {
				case 1:
					ManageMovies generateMovies = new ManageMovies();
					generateMovies.mainScreem();
					break;
				case 2:
					if(mapSessionData.isEmpty()) break; 
					TicketSale ticketSales = new TicketSale();
					ticketSales.mainScreem();
					break;
				case 3:
					historic.printHistoicModify();
					break;
				case 4:
					historic.printHistoicSellers();
					break;
				case 5:
					sairDoSistema();
					repeate = false;
					break;
			}			
		}while(repeate);
	}
	
	/**
	 * Inicializa cada uma das listas, caso alguma delas seja nula
	 */
	private static void initializeCollectins() {
		if(mapSessionData == null)mapSessionData = new TreeMap<Integer, Map<String,SessionData>>();
		if(mapMovieData == null)mapMovieData = new TreeMap<Integer, MovieData>();
		if(mapRoomData == null)mapRoomData = new TreeMap<Integer, RoomData>();		
	}
	
	/**
	 * Ao sair do sistema ele salva todas as mudanças feitas, como criacao, modificacao e exclucao
	 */
	private static void sairDoSistema(){
		uploadData();
	}

	public static boolean repeate(){
		Scanner scanner = new Scanner(System.in);
		String varReapeate;
		boolean repeate = false;
		
		do{
			System.out.print("\nVoce deseja repetir esta operacao(s) ou deseja sair(n): ");
			varReapeate = scanner.next();
		}while(!(varReapeate.equals("s") || varReapeate.equals("n")));
		
		return varReapeate.equals("s") ? true : false ;
	}
	
	/**
	 * Recupera os dados dos arquivos e adiciona aos atributos(Maps) desta classe
	 */
	@SuppressWarnings("unchecked")
	public static void downloadData() {
		try {
			
			FileInputStream fluxoMovieIn = new FileInputStream("fileMovie.ser");
			ObjectInputStream fMovieIn = new ObjectInputStream(fluxoMovieIn);
			mapMovieData = (TreeMap<Integer, MovieData>)fMovieIn.readObject();
			fMovieIn.close();
			fluxoMovieIn.close();
			
			FileInputStream fluxoRoomIn = new FileInputStream("fileRoom.ser");
			ObjectInputStream fRoomIn = new ObjectInputStream(fluxoRoomIn);
			mapRoomData = (TreeMap<Integer, RoomData>)fRoomIn.readObject();
			fRoomIn.close();
			fluxoRoomIn.close();

			FileInputStream fluxoSessionIn = new FileInputStream("fileSession.ser");
			ObjectInputStream fSessionIn = new ObjectInputStream(fluxoSessionIn);
			mapSessionData = (Map<Integer, Map<String,SessionData>>)fSessionIn.readObject();
			fSessionIn.close(); 
			fluxoSessionIn.close();
	
		} catch (FileNotFoundException fileNotFound) {
			uploadData();
		} catch (IOException io) {
			
		} catch (ClassNotFoundException classNotFound) {
			
		} finally {
			initializeCollectins();
		}
	}
	
	/**
	 * Salva as listas nos arquivos
	 */
	public static void uploadData(){
		try {
			
			FileOutputStream movieOut = new FileOutputStream("fileMovie.ser");
			ObjectOutputStream objMovie = new ObjectOutputStream(movieOut);
			objMovie.writeObject(mapMovieData);
			objMovie.close();
			movieOut.close();

			FileOutputStream roomOut = new FileOutputStream("fileRoom.ser");
			ObjectOutputStream objRoom = new ObjectOutputStream(roomOut);
			objRoom.writeObject(mapRoomData);
			objRoom.close();
			roomOut.close();
			
			FileOutputStream sessionOut = new FileOutputStream("fileSession.ser");
			ObjectOutputStream objSession = new ObjectOutputStream(sessionOut);
			objSession.writeObject(mapSessionData);
			objSession.close();
			sessionOut.close();
			
		} catch (FileNotFoundException fileNotFound) {
			System.out.println("9");
			fileNotFound.printStackTrace();
		} catch (IOException io) {
			System.out.println("10");
			io.printStackTrace();
		}finally {
			/*
			 * fMovieIn.close(); fRoomIn.close(); fSessionIn.close();
			 */
		}	
	}
}
