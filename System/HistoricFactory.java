package System;

import java.io.*;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.Scanner;

import MoviesData.HistoricData;
import MoviesData.MovieData;
import MoviesData.RoomData;
import MoviesData.SessionData;
import Startup.DataHours;
import Startup.StartupSystem;

/**
 * ESTA CLASSE RECEBE STRINGS DO PROGRAMA NUM TODO.
 * PARA ADICIONAR HISTORICO DE VENDA, MANDAR UM STRING, COM OS DADOS QUE QUERIA COLOCAR CONCATENADO
 * O MESMO PARA HISTORICO DE MODIFICAÇOES.
 * @author THIAGO :(
 */

public class HistoricFactory extends StartupSystem{
	
	private static HistoricFactory instance;
	public HistoricFactory() {}
	
	public static HistoricFactory getInstance() {
		if (instance==null)	instance = new HistoricFactory();
		return instance;
	}
	
	
	public void mainScreem() {
		Scanner scanner = new Scanner(System.in);
		int option = 0;
		boolean repeate = true;
		boolean error = false;
		do {
			repeate = true;
			System.out.println("\t\nHistorico...");
			try {
				do {
					System.out.println("\tVenda(1)");
					System.out.println("\tGerenciamento(2)");
					System.out.println("\tVoltar ao menu anterior(3)");
					System.out.print("Digite o codigo: ");
					option = scanner.nextInt();

					if (option < 1 || option > 3) {
						System.out.println("Valor invalido. Digite novamente.");
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
				chooseSell();
				break;
			case 2:
				chooseManage();
				break;
			case 3:
				repeate = false;
				break;
			}
		} while (repeate);
	}
	
	private void chooseSell() {
		System.out.println("\t\nHistorico de Vendas");
		for(HistoricData historicData : listHistoricData){
			if(historicData.getSituation().equals("SOLD")){
				SessionData session = (SessionData) historicData.getObject();
				System.out.println("Data da Venda: "+historicData.getDate().toString()+"\t"+session.toString());
			}			
		}
	}

	private void chooseManage() {
		System.out.println("\t\nHistorico de Gerenciamento");
		for(HistoricData historicData : listHistoricData){
			if(!historicData.getSituation().equals("SOLD")){
				if(historicData.getObject().getClass() == MovieData.class){
					MovieData historic = (MovieData) historicData.getObject();
					System.out.println("Data da "+historicData.getSituation()+" do Filme:\t"+historicData.getDate().toString()+"\t"+
							historic.toString());
				}else if(historicData.getObject().getClass() == RoomData.class){
					RoomData historic = (RoomData) historicData.getObject();
					System.out.println("Data da "+historicData.getSituation()+" da Sala:\t"+historicData.getDate().toString()+"\t"+
							historic.toString());
				}else{
					SessionData historic = (SessionData) historicData.getObject();
					System.out.println("Data da "+historicData.getSituation()+" da Sessao:\t"+historicData.getDate().toString()+"\t"+
							historic.toString());
				}
				
				
			}			
		}
	}
	
	public void AddHistoric(Object object, String situation) {
		GregorianCalendar date = new GregorianCalendar();
		HistoricData historicData = new HistoricData(object, situation, new DataHours(
				date.get(GregorianCalendar.DAY_OF_MONTH), date.get(GregorianCalendar.MONTH), 
				date.get(GregorianCalendar.YEAR),date.get(GregorianCalendar.HOUR_OF_DAY), 
				date.get(GregorianCalendar.MINUTE)));
		listHistoricData.add(historicData);
	}
	
	/**
	 * Exibe o historico de compras
	 */
	public void printHistoicSellers() {
		try{
			InputStream is = new FileInputStream("fileSellerHistoric.ser");
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			System.out.println("\nIMPRIMINDO HISTORICO DE VENDAS: \n");
			String s = br.readLine();
			while (s != null) {
				System.out.println(s);
				s = br.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("\tAinda nao existe historico de vendas.\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Exibe o historico de gerenciamento
	 */
	public void printHistoicModify() {
		try{
			System.out.println("\nIMPRIMINDO HISTORICO DE  GERENCIAMENTO: \n");
			InputStream is = new FileInputStream("fileModifyDataHistoric.ser");
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
	
			String s = br.readLine();
			while (s != null) {
				System.out.println(s);
				s = br.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("\tAinda nao existe historico de vendas.\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Limpa todos os historico
	 */
	public void clearHistoric() {
		try{
			FileOutputStream a = new FileOutputStream("fileSellerHistoric.ser",false);
			OutputStreamWriter b = new OutputStreamWriter(a);
			BufferedWriter c = new BufferedWriter(b);
			c.write("");
			c.close();

			a = new FileOutputStream("fileModifyDataHistoric.ser", false);
			b = new OutputStreamWriter(a);
			c = new BufferedWriter(b);
			c.write("");
			c.close();
			System.out.println("\n !!!TODO O HISTORICO APAGADO!!!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}