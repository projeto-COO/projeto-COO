package System;

import java.util.InputMismatchException;
import java.util.Scanner;

import moviesData.HistoricData;
import moviesData.SessionData;
import startup.StartupSystem;

/**
 * Classe responsavel pela venda de ingressos
 * @author ricardo
 *
 */
public class TicketSale extends StartupSystem {

	HistoricData historic = new HistoricData();
	//String sendToHist = null;
	public int optSession;
	public int optChair;
	public int spots =0;
	private SessionData currentSession;

	/**
	 * Metodo principal na escolha e finalizacao da compra
	 */
	public int verifySpots(){
		Scanner scanner = new Scanner(System.in);
		
		boolean sell = true;
		
		do{
			
			try{
			System.out.println("Ingressos para quantas pessoas?");
			spots = scanner.nextInt();
			sell=false;
			}
			catch(InputMismatchException e){
				System.out.println("Entre com valores validos");
			}}while(sell);
		return spots;
	}
	public void mainScreem() {
		Scanner scanner = new Scanner(System.in);
		optSession = 0;
		optChair = 0;
		int endBuy = 0;
		ManageSession session = new ManageSession();
		boolean sessionEmpty = true;
		System.out.println("\nSISTEMA DE VENDA DE INGRESSO\n");
		System.out.println("Nova Compra");
		try {
			do {
				currentSession = session.chooseSession("buy");
				if (currentSession != null)
					sessionEmpty = false;
			} while (sessionEmpty);

			currentSession.getCurrentRoom().updateAvailability();
			System.out.println("Quantidade de Lugares Disponiveis: "
					+ currentSession.getAvailable());
			do {
				System.out.print("Confirmar Compra(Sim(1)/ Nao(2))? ");
				endBuy = scanner.nextInt();
			} while (endBuy != 1 && endBuy != 2);

			if (endBuy == 1) {
				historic.AddHistoricSeller("Venda: "+ currentSession.toString());
				System.out.println("\nCompra Finalizada Com Sucesso. Imprimindo Ticket.");
				//sendToHist = Integer.toString(Integer.parseInt(currentSession.getIdSession()));
				tagsale(currentSession);
			}
		} catch (NullPointerException e) {
			System.out.println("Valor de entrada invalido, digitar novamamente");
			scanner = new Scanner(System.in);
		}
	}
	
	
	/**
	 * Marca a sessao, o filme e a sala como vendida
	 * @param currentSession
	 */
	private void tagsale(SessionData currentSession) {
		mapSessionData.get(currentSession.getCurrentRoom().getIdRoom()).get(
				currentSession.getIdSession()).setSold(true);
		mapMovieData.get(currentSession.getCurrentMovie().getIdMovie()).setSold(true);
		mapRoomData.get(currentSession.getCurrentRoom().getIdRoom()).setSold(true);
	}
}