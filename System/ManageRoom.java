package System;

import java.util.InputMismatchException;
import java.util.Scanner;

import MoviesData.RoomData;

/**
 * Padrão Singleton
 */

 /**
 *Classe responsavel por gerenciar todas as tarefas da sala
 */
public class ManageRoom extends ManageMovies {
	private static ManageRoom instance;
	
	public ManageRoom() {}
	
	public static ManageRoom getInstance() {
		if (instance==null)	instance = new ManageRoom();
		return instance;
	}
	
	/**
	 * Exibi um menu no qual ele pode escolher se quer exibir, criar, modificar ou excluir uma sala
	 */
	void optionRoom() {
		Scanner scanner = new Scanner(System.in);
		int option = 0;
		boolean repeate = true;
		boolean error = false;

		do {
			try {
				repeate = true;
				System.out.print("\t\nGerenciar Salas...");
				do {
					System.out.print("\n\tExibir as Salas(1)");
					System.out.print("\n\tModificar as Salas(2)");
					System.out.print("\n\tCriar as Salas(3)");
					System.out.print("\n\tExcluir as Salas(4)");
					System.out.print("\n\tVoltar ao menu anterior(5)");
					System.out.print("\nDigite o codigo: ");
					option = scanner.nextInt();

					if (option < 1 || option > 5) {
						error = true;
						System.out.println("Valor invalido. Digite novamente.");
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
				viewRoom("view");
				break;
			case 2:
				modifyRoom();
				break;
			case 3:
				createRoom();
				break;
			case 4:
				deleteRoom();
				break;
			case 5:
				repeate = false;
				break;
			}

		} while (repeate);
	}

	/**
	 * Cria uma sala
	 */
	private void createRoom() {
		System.out.print("\nCriando Sala...");
		Integer rows = rowsOfRoom();
		Integer columns = columnsOfRoom();
		Integer idRoom = idRoom();

		RoomData newRoomData = new RoomData(idRoom, rows, columns);
		mapRoomData.put(idRoom, newRoomData);
		historic.AddHistoric(newRoomData, "CREATED");
		uploadData();
		System.out.println("Sala criada");
	}
	
	/**
	 * Modifica uma sala
	 */
	private void modifyRoom() {
		Scanner scanner = new Scanner(System.in);
		RoomData currentRoom = chooseRoom("modify");

		if (currentRoom == null)
			return;

		Integer rows = null;
		Integer columns = null;
		boolean whatever = false;
		do {
			try {
				System.out.println("\nModificando Sala...");
				System.out.print("\nQtd. de fileiras (" + currentRoom.getRows()	+ "): ");
				rows = scanner.nextInt();
				System.out.print("\nQtd. de colunas ("+ currentRoom.getColumns() + "): ");
				columns = scanner.nextInt();
				if (rows <= 0 || columns <= 0) {
					System.out
							.println("Valores invalidos para fileira/coluna.");
					whatever = false;
				} else
					whatever = true;
			} catch (NumberFormatException e) {
				System.out.printf("%s", e);
				System.out.println("Erro de formato, insira novamente");
				scanner = new Scanner(System.in);
			} catch (InputMismatchException e) {
				System.out.printf("%s", e);
				System.out.println("Insira valores validos");
				scanner = new Scanner(System.in);
			}
		} while (!whatever);

		currentRoom.setRows(rows);
		currentRoom.setColumns(columns);
		mapRoomData.put(currentRoom.getIdRoom(), currentRoom);
		historic.AddHistoric(currentRoom, "MODIFIED");
		uploadData();
	}

	/**
	 * Deleta uma sala
	 */
	private void deleteRoom() {
		RoomData currentRoom = chooseRoom("delete");
		if (currentRoom == null) {
			System.out.print("\nVoce nao selecionou nenhuma sala");
			return;
		} else {
			System.out.println("\nExcluida a sala e todas as suas sessoes");
			mapRoomData.remove(currentRoom.getIdRoom());
			mapSessionData.remove(currentRoom.getIdRoom());
			historic.AddHistoric(currentRoom,"DELETED");
			uploadData();
		}
	}
	
	/**
	 * Determina a qtd de fileiras
	 * @return
	 */
	private Integer rowsOfRoom() {
		Scanner scanner = new Scanner(System.in);
		Integer rows = 0;
		boolean test = false;
		do {
			try {
				System.out.print("\nDigite a Qtd. de fileiras: ");
				rows = scanner.nextInt();
				test = true;
				if (rows <= 0) {
					System.out.println("Valores invalidos.");
					test = false;
				}
			} catch (InputMismatchException e) {
				System.out.println("Valores Invalidos");
				scanner = new Scanner(System.in);
			}
		} while (!test);
		return rows;
	}

	/**
	 * Determina a quantidade de colunas
	 * @return
	 * @throws InputMismatchException
	 */
	private Integer columnsOfRoom() throws InputMismatchException {
		Scanner scanner = new Scanner(System.in);
		Integer columns = 0;
		boolean test = false;
		do {
			try {
				System.out.print("Digite a Qtd. de colunas: ");
				columns = scanner.nextInt();
				test = true;
				if (columns <= 0) {
					System.out.print("\nValores invalidos.");
					test = false;
				}
			} catch (InputMismatchException e) {
				System.out.println("Valores invalidos");
			}
		} while (!test);
		return columns;
	}
	
	/**
	 * Cria o id da sala sequencialmente
	 * @return
	 */
	private Integer idRoom() {
		if (mapRoomData == null || mapRoomData.isEmpty())
			return 1;
		else {
			int lastId = 0;
			for (Integer current : mapRoomData.keySet())
				lastId = mapRoomData.get(current).getIdRoom();
			return lastId + 1;
		}
	}
}