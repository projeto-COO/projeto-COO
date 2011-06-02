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

import startup.DataHours;
import startup.StartupSystem;

import moviesData.MovieData;
import moviesData.RoomData;
import moviesData.SessionData;

/**
 * Esta classe gerencia todo o cinema, filmes, salas e sessoes.
 * @author ricardo
 *
 */
@SuppressWarnings("unused")
public class ManageMovies extends StartupSystem {
	
	/**
	 * Exibe um menu no qual será escolhido qual areas irá ser mexida 
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
					} else
						error = false;
				} while (error);

			} catch (NumberFormatException e) {
				System.out.printf("%s", e);
				System.out.println("Erro de formato, insira novamente");
				scanner = new Scanner(System.in);
			} catch (InputMismatchException e) {
				System.out.printf("%s", e);
				System.out.println("Insira codigo valido");
				scanner = new Scanner(System.in);
			}

			switch (option) {
			case 1:
				ManageSession manageSession = new ManageSession();
				manageSession.optionSession();
				break;
			case 2:
				ManageMovie manageMovie = new ManageMovie();
				manageMovie.optionMovie();
				break;
			case 3:
				ManageRoom manageRoom = new ManageRoom();
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
	protected MovieData chooseMovie() {
		Scanner scanner = new Scanner(System.in);
		Integer chooseMovie = 0;

		if (mapMovieData.isEmpty()) {
			System.out.println("\nE necessario cadastrar filmes");
			return null;
		}else{
			viewMovie();
			do {
				System.out.print("Digite o codigo do filme: ");
				chooseMovie = scanner.nextInt();
			} while (false);
	
			return mapMovieData.get(chooseMovie);
		}
	}
	
	/**
	 * Método que retorna uma sala escolhida
	 * @return
	 */
	protected RoomData chooseRoom() {
		Scanner scanner = new Scanner(System.in);
		Integer chooseRoom = 0;

		if (mapRoomData == null || mapRoomData.isEmpty()) {
			System.out.println("\nE necessario criar salas");
			return null;
		}
		viewRoom();
		do {
			System.out.print("Digite o codigo da sala: ");
			chooseRoom = scanner.nextInt();
		} while (false);

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
			System.out.print("\nDigite a data da sessao(dd-mm-yyyy): ");
			calendarString = scanner.next();
	
			System.out.print("Digite o horario da sessao(hh-mm): ");
			dateString = scanner.next();
			
			try{
				date = new DataHours(new Integer(calendarString.substring(0,
						2)), new Integer(calendarString.substring(3, 5)), new Integer(
						calendarString.substring(6, 10)), new Integer(
						dateString.substring(0, 2)), new Integer(dateString.substring(
						3, 5)));
				error = false;
			}catch(StringIndexOutOfBoundsException e){
				error = true;
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
	protected void viewSession() {
		if (mapSessionData.isEmpty())
			System.out.println("\nNao existem sessoes cadastradas");
		else {
			System.out.println("\nExibindo Sessoes..");
			for (Integer roomNow : mapSessionData.keySet()) {
				for (String dates : mapSessionData.get(roomNow).keySet())
					mapSessionData.get(roomNow).get(dates).toShow();
			}
		}
	}

	/**
	 * Exibe todos os filmes
	 */
	protected void viewMovie() {
		if (mapMovieData.isEmpty()) {
			System.out.println("\nNao existem filmes cadastrados");
		} else {
			System.out.println("\nExibindo Filmes...");
			for (Integer movieNow : mapMovieData.keySet())
				mapMovieData.get(movieNow).toShow();
		}
	}

	/**
	 * Exibe todas as salas
	 */
	protected void viewRoom() {
		if (mapRoomData.isEmpty()) {
			System.out.println("Nao existem salas cadastradas");
		} else {
			System.out.println("\nExibindo sala...");
			Iterator<Integer> iterator = mapRoomData.keySet().iterator();
			while (iterator.hasNext())
				mapRoomData.get(iterator.next()).toShow();
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