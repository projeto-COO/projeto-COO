package System;

import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.Scanner;

import MoviesData.SessionData;
import Startup.DataHours;
import Startup.StartupSystem;

/**
 * Padrão Singleton
 */

/**
 * Classe responsavel pela venda de ingressos
 * 
 * @author ricardo
 * 
 */
public class TicketSale extends StartupSystem {
	final static int SOLD = 3;
	private static TicketSale instance;

	public TicketSale() {
	}

	public static TicketSale getInstance() {
		if (instance == null)
			instance = new TicketSale();
		return instance;
	}

	HistoricFactory historic = new HistoricFactory();
	private SessionData currentSession;
	public int spots = 0;

	@SuppressWarnings("static-access")
	public void mainScreem() {
		Scanner scanner = new Scanner(System.in);
		int endBuy = 0;
		boolean sessionEmpty = true;
		ManageSession session = new ManageSession();

		System.out.println("\nSISTEMA DE VENDA DE INGRESSO\n");
		System.out.println("Nova Compra");

		try {
			do {
				currentSession = session.chooseSession("buy");
				if (currentSession != null)
					sessionEmpty = false;
			} while (sessionEmpty);

			currentSession.updateAvailability(verifySpots(currentSession));
			System.out.println("Quantidade de Lugares Disponiveis: "+ currentSession.getAvailability());
			do {
				System.out.print("Confirmar Compra(Sim(1)/ Nao(2))? ");
				endBuy = scanner.nextInt();
			} while (endBuy != 1 && endBuy != 2);

			if (endBuy == 1) {
				historic.AddHistoric(currentSession, "SOLD");
				System.out.println("\nCompra Finalizada Com Sucesso. Imprimindo Ticket.");
				tagsale(currentSession);
				uploadData();
			}
		} catch (NullPointerException e) {
			System.out.println("\nValor de entrada invalido, digitar novamamente");
			scanner = new Scanner(System.in);
		}
	}

	/**
	 * Metodo principal na escolha e finalizacao da compra
	 */
	public int verifySpots(SessionData currentSession) {
		Scanner scanner = new Scanner(System.in);
		boolean error = false;

		do {
			try {
				System.out.print("\nIngressos para quantas pessoas: ");
				spots = scanner.nextInt();

				if (spots <= 0 || (currentSession.getAvailability() - spots) < 0) {
					System.out.println("\nValor não corresponde com o restante de ingressos!");
					error = true;
				} else	error = false;
			} catch (InputMismatchException e) {
				System.out.println("Entre com valores validos");
				error = true;
			}
		} while (error);
		return spots;
	}

	/**
	 * Marca a sessao, o filme e a sala como vendida
	 * 
	 * @param currentSession
	 */
	private void tagsale(SessionData currentSession) {
		mapSessionData.get(currentSession.getCurrentRoom().getIdRoom()).get(currentSession.getIdSession()).setSold(true);
		mapMovieData.get(currentSession.getCurrentMovie().getIdMovie())	.setSold(true);
		mapRoomData.get(currentSession.getCurrentRoom().getIdRoom()).setSold(true);
	}
}