package MoviesData;

import java.io.*;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * ESTA CLASSE RECEBE STRINGS DO PROGRAMA NUM TODO.
 * PARA ADICIONAR HISTORICO DE VENDA, MANDAR UM STRING, COM OS DADOS QUE QUERIA COLOCAR CONCATENADO
 * O MESMO PARA HISTORICO DE MODIFICAÇOES.
 * @author THIAGO :(
 */

public class HistoricData {

	/**
	 * Adiciona uma string ao historico de venda
	 * @param add
	 */
	public void AddHistoricSeller(String add) {
		
		try {
			OutputStream os = new FileOutputStream("fileSellerHistoric.ser", true);
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			
			bw.write("-)");
			bw.write(add);
			bw.newLine();
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	/**
	 * Adiciona uma string ao historico de gerenciamento
	 * @param add
	 */
	public void AddHistoricModify(String add) {
		try{
			OutputStream os = new FileOutputStream("fileModifyDataHistoric.ser",true);
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
	
			bw.write("-");
			bw.write(add);
			bw.newLine();
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	/*public void gerenciador() {
		int opcao = 0;
		boolean laco = true;
		do {
			try {
				System.out.println("\t--GERENCIADOR DE HISTORICO--");
				System.out.println("Imprimir Historico de Compra....(1)");
				System.out.println("Imprimir Historico de Operacao..(2)");
				System.out.println("Apagar Todo o Historico.........(3)");
				Scanner scanner = new Scanner(System.in);
				opcao = scanner.nextInt();
				switch (opcao) {
				case 1:
					printHistoicSellers();
					laco = false;
					break;
				case 2:
					printHistoicModify();
					laco = false;
					break;
				case 3:
					clearHistoric();
					laco = false;
					break;
				default:
					System.out.println("Opcao errada!!! tente novamente");
					break;
				}
			} catch (Exception e) {
				System.out.println("Valor do indice invalido");
			}
		} while (laco);
	}*/
}
