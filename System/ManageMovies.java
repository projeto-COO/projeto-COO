package System;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import MoviesData.MovieData;
import MoviesData.RoomData;
import MoviesData.SessionData;
import Startup.DataHours;
import Startup.StartupSystem;



/**
 * Padrão Singleton
 */

 /**
 * Esta classe gerencia todo o cinema, filmes, salas e sessoes.
 * @author ricardo
 *
 */
@SuppressWarnings("unused")
public class ManageMovies extends StartupSystem {
	
	private static ManageMovies instance;
	
	public ManageMovies() {}
	
	public static ManageMovies getInstance() {
		if (instance==null)	instance = new ManageMovies();
		return instance;
	}
	
	/**
	 * Exibe um menu no qual será escolhido qual area irá ser mexida 
	 */
	public void mainScreem() {
		Scanner scanner = new Scanner(System.in);
		int option = 0;
		boolean repeate = true;
		boolean error = false;
		do {
			try {
				do {
					System.out.println("\nGerenciar...");
					System.out.println("\tSessoes(1)");
					System.out.println("\tFilmes(2)");
					System.out.println("\tSalas(3)");
					System.out.println("\tVoltar ao Menu anterior(4)");
					System.out.print("Digite o codigo: ");
					option = scanner.nextInt();
					if (option < 1 || option > 4) {
						System.out.print("\nValor invalido. Digite novamente.\n");
						error = true;
					} else	error = false;
				} while (error);

			} catch (NumberFormatException e) {
				System.out.println("Erro de formato, insira novamente");
				scanner = new Scanner(System.in);
			} catch (InputMismatchException e) {
				System.out.println("Insira codigo valido");
				scanner = new Scanner(System.in);
			}

			switch (option) {
			case 1:
				ManageSession manageSession = ManageSession.getInstance();
				manageSession.optionSession();
				break;
			case 2:
				ManageMovie manageMovie = ManageMovie.getInstance();
				manageMovie.optionMovie();
				break;
			case 3:
				ManageRoom manageRoom = ManageRoom.getInstance();
				manageRoom.optionRoom();
				break;
			case 4:
				repeate = false;
				break;
			}
		} while (repeate);
	}

	/**
	 * Metodo que retorna um filme escolhido, caso tenha
	 * @return
	 */
	protected MovieData chooseMovie(String restriction) {
		Scanner scanner = new Scanner(System.in);
		Integer chooseMovie = 0;
		boolean error = false;
		if (mapMovieData.isEmpty()) {
			System.out.println("\nE necessario cadastrar filmes");
			return null;
		}else{
			viewMovie(restriction);
			do {
				try{
					System.out.print("Digite o codigo do filme: ");
					chooseMovie = scanner.nextInt();
					
					if(mapMovieData.get(chooseMovie).isSold()){
						System.out.println("Insira codigo valido");
						error = true;
					}else	error = false;
				}catch (InputMismatchException e) {
					System.out.println("Insira codigo valido");
					scanner = new Scanner(System.in);
					error = true;
				}
			} while (error);
			return mapMovieData.get(chooseMovie);
		}
	}
	
	/**
	 * Método que retorna uma sala escolhida
	 * @return
	 */
	protected RoomData chooseRoom(String restriction) {
		Scanner scanner = new Scanner(System.in);
		Integer chooseRoom = 0;
		boolean error = false;
		if (mapRoomData == null || mapRoomData.isEmpty()) {
			System.out.println("\nE necessario criar salas");
			return null;
		}
		viewRoom(restriction);
		do {
			try{
				System.out.print("Digite o codigo da sala: ");
				chooseRoom = scanner.nextInt();
				
				if(mapRoomData.get(chooseRoom).isSold()){
					System.out.println("Insira codigo valido");
					error = true;
				}else	error = false;
			}catch (InputMismatchException e) {
				System.out.println("Insira codigo valido");
				scanner = new Scanner(System.in);
				error = true;
			}
		} while (error);
		return mapRoomData.get(chooseRoom);
	}

	/**
	 * Método que retorna uma data escolhida na qual será a sessao
	 * @return
	 */
	protected DataHours chooseDate() {
		Scanner scanner = new Scanner(System.in);
		DataHours date = null;
		String calendarString;
		String dateString;
		boolean error = false;
		
		do{
			System.out.print("\nDigite a data e horario da sessao(dd/mm/yyyy-hh:mm): ");
			calendarString = scanner.next();
			try{
				date = new DataHours(new Integer(calendarString.substring(0,
						2)), new Integer(calendarString.substring(3, 5)), new Integer(
						calendarString.substring(6, 10)), new Integer(
						calendarString.substring(11, 13)), new Integer(calendarString.substring(
						14, 16)));
				
				error = false;
			}catch(StringIndexOutOfBoundsException e){
				error = true;
				scanner = new Scanner(System.in);
			}
		}while(error);
		
		return date;
	}

	/**
	 * Procura uma sessao especificada pelo id
	 * @param idSession
	 * @return
	 */
	protected SessionData searchSession(Integer idSession) {
		SessionData currentSession = null;
		for (Integer idRoom : mapSessionData.keySet()) {
			for (String current : mapSessionData.get(idRoom).keySet())
				currentSession = mapSessionData.get(idRoom).get(current);
		}
		return currentSession;
	}

	/**
	 * Exibe todas as sessoes
	 */
	protected void viewSession(String restriction) {
		if (mapSessionData.isEmpty())
			System.out.println("\nNao existem sessoes cadastradas");
		else {
			System.out.println("\nExibindo Sessoes..");
			for (Integer roomNow : mapSessionData.keySet()) {
				for (String sessionNow : mapSessionData.get(roomNow).keySet()){
					if (restriction.equals("delete")|| restriction.equals("modify")) {
						if (!mapSessionData.get(roomNow).get(sessionNow).isSold())
							mapSessionData.get(roomNow).get(sessionNow)	.toShow();
					}else	mapSessionData.get(roomNow).get(sessionNow).toShow();
				}
			}
		}
	}

	/**
	 * Exibe todos os filmes
	 */
	protected void viewMovie(String restriction) {
		Integer currentId;
		if (mapMovieData.isEmpty()) {
			System.out.println("\nNao existem filmes cadastrados");
		} else {
			System.out.println("\nExibindo Filmes...");
			for (Integer movieNow : mapMovieData.keySet()){
				if(restriction.endsWith("delete") || restriction.equals("modify")){
					if(! mapMovieData.get(movieNow).isSold())
						mapMovieData.get(movieNow).toShow();
				}else mapMovieData.get(movieNow).toShow();					
			}				
		}
	}

	/**
	 * Exibe todas as salas
	 */
	protected void viewRoom(String restriction) {
		if (mapRoomData.isEmpty()) {
			System.out.println("Nao existem salas cadastradas");
		} else {
			System.out.println("\nExibindo sala...");
			for (Integer currentId : mapRoomData.keySet()){
				if(restriction.equals("delete") ||  restriction.equals("modify")){
					if(!mapRoomData.get(currentId).isSold())
						mapRoomData.get(currentId).toShow();
				}else mapRoomData.get(currentId).toShow();
			}
		}
	}
	
	/**
	 * Verifica se o horario passado corresponde com a realidade
	 * @param nowData
	 * @return
	 */
	public boolean checkData(DataHours nowData) {
		if (nowData.hh() < 0 || nowData.hh() > 24 || nowData.mm() < 0
				|| nowData.mm() > 59 || nowData.dd() < 1 || nowData.dd() > 31
				|| nowData.MM() < 1 || nowData.MM() > 12 || nowData.yyyy() < 1) {
			System.out.println("\nData Inadequada");
			return false;
		}
		return true;
	}
}