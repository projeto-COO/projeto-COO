package System;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.TreeMap;

import startup.DataHours;
import moviesData.MovieData;
import moviesData.RoomData;
import moviesData.SessionData;

/**
 *Classe responsavel por gerenciar todas as tarefas da sessao
 */
public class ManageSession extends ManageMovies {
	SessionData chooseSession;

	/**
	 * Exibi um menu no qual ele pode escolher se quer exibir, criar, modificar
	 * ou excluir uma sessao
	 */
	void optionSession() {
		Scanner scanner = new Scanner(System.in);
		int option = 0;
		boolean repeate = true, error = false;
		do {
			try {
				System.out.println("\nGerenciando Sessoes...");
				do {
					System.out.println("\tExibir as Sessoes(1)");
					System.out.println("\tModificar as Sessoes(2)");
					System.out.println("\tCriar as Sessoes(3)");
					System.out.println("\tExcluir as Sessoes(4)");
					System.out.println("\tVoltar ao menu anterior(5)");
					System.out.print("Digite o Codigo: ");
					option = scanner.nextInt();

					if (option < 1 || option > 5)
						error = true;
					else
						error = false;
				} while (error);
			} catch (NumberFormatException e) {
				System.out.println("\nErro de formato, insira novamente");
				scanner = new Scanner(System.in);
			} catch (InputMismatchException e) {
				System.out.println("\nErro de formato, insira novamente");
				scanner = new Scanner(System.in);
			}
			switch (option) {
			case 1:
				viewSession("view");
				break;
			case 2:
				modifySession();
				break;
			case 3:
				createSession();
				break;
			case 4:
				deleteSession();
				break;
			case 5:
				repeate = false;
				break;
			}
		} while (repeate);
	}

	/**
	 * Cria um sessao
	 */
	private void createSession() {
		SessionData newSession = null;
		Integer chooseMovie;
		Integer chooseRoom;
		boolean newData = false;
		
		System.out.print("\nCriando sessao...");
		MovieData currentMovieData = chooseMovie("create");

		if (currentMovieData == null) {
			System.out.println("E necessario cadastrar filmes ou codigo invalido");
			return;
		} else
			chooseMovie = currentMovieData.getIdMovie();

		RoomData currentRoomData = chooseRoom("create");

		if (currentRoomData == null) {
			System.out.println("E necessario cadastrar salas");
			return;
		} else
			chooseRoom = currentRoomData.getIdRoom();

		do {
			DataHours date = chooseDate();
			newSession = new SessionData(mapMovieData.get(chooseMovie), date,
					mapRoomData.get(chooseRoom));

			if (!checkData(newSession.getDate())) {
				newData = true;
			} else {
				if (!checkSession(newSession)) {
					System.out.println("\nJa existe sessao alocada nesta sala neste horario");
					newData = true;
				} else {
					newData = false;
				}
			}
		} while (newData);

		if (mapSessionData.get(newSession.getCurrentRoom().getIdRoom()) == null) {
			mapSessionData.put(newSession.getCurrentRoom().getIdRoom(),new TreeMap<String, SessionData>());
		}
		
		mapSessionData.get(newSession.getCurrentRoom().getIdRoom()).put(newSession.getIdSession(), newSession);		
		
		historic.AddHistoricModify("Criada a Sessao: " + newSession.toString());
		System.out.println("Sessao criada");
		uploadData();
		return;
	}

	/**
	 * Modifica uma sessao
	 */
	private void modifySession() {
		Scanner scanner = new Scanner(System.in);
		if (mapSessionData.isEmpty()) {
			System.out.println("\nNao existem sessoes cadastradas");
		} else {
			this.chooseSession = chooseSession("modify");
			
			Integer option = 0;
			boolean error = true;
			boolean repeate = true;
			if (this.chooseSession == null) {
				System.out.println("Valor Invalido");
				return;
			}
			
			mapSessionData.get(chooseSession.getCurrentRoom().getIdRoom()).
			remove(chooseSession.getIdSession());
								
			do {
				do {
					System.out.print("\nModificando sessao... Id:"
							+ this.chooseSession.getIdSession());
					System.out.println("\nO que voce deseja modificar: ");
					System.out.println("\tMudar filme ("
							+ this.chooseSession.getCurrentMovie().getName()
							+ ") (1)");
					System.out.println("\tMudar sala ("
							+ this.chooseSession.getCurrentRoom().getIdRoom()
							+ ") (2)");
					System.out.println("\tMudar horario ("
							+ this.chooseSession.viewDate() + ") (3)");
					System.out.println("\tVoltar ao menu anterior (4)");
					System.out.print("Digite o codigo: ");
					option = scanner.nextInt();

					if (option < 1 || option > 4)
						error = true;
					else
						error = false;
				} while (error);

				switch (option) {
				case 1:
					changeMovie();
					break;
				case 2:
					changeRoom();
					break;
				case 3:
					changeDate();
					break;
				case 4:
					repeate = false;
					break;
				}
			} while (repeate);
			
			if(mapSessionData.get(this.chooseSession.getCurrentRoom().getIdRoom()) == null){
				mapSessionData.put(this.chooseSession.getCurrentRoom().getIdRoom(), 
						new TreeMap<String,SessionData>());
			}
			
			mapSessionData.get(this.chooseSession.getCurrentRoom().getIdRoom())
			.put(this.chooseSession.getIdSession(), this.chooseSession);				
						
			System.out.println("Sessao modificada");
			uploadData();
		}
	}

	/**
	 * Deleta um sessao
	 */
	private void deleteSession() {
		if (mapSessionData.isEmpty()) {
			System.out.println("\nNao existem sessoes cadastradas");
		} else {
			this.chooseSession = chooseSession("delete");
			if (this.chooseSession == null) {
				System.out.print("\nVoce nao selecionou nenhuma Sessao");
				return;
			} else {
				System.out.println("\nExcluida a sessao");
				mapSessionData.get(this.chooseSession.getCurrentRoom().getIdRoom())
						.remove(this.chooseSession.getIdSession());
				historic.AddHistoricModify("Excluida a sessao: "
						+ this.chooseSession.toString());
				uploadData();
			}
		}
	}

	/**
	 * Verifica se o horario da sessao nao entra em conflito com outra sessao da
	 * mesma sala
	 * 
	 * @param current
	 * @return
	 */
	public boolean checkSession(SessionData current) {
		SessionData example;
		if (mapSessionData.get(current.getCurrentRoom().getIdRoom()) != null) {
			for (String currentSession : mapSessionData.get(
					current.getCurrentRoom().getIdRoom()).keySet()) {
				example = mapSessionData.get(
						current.getCurrentRoom().getIdRoom()).get(
						currentSession);

				if (current.getDate().toString().equals(
						example.getDate().toString())) {
					return false;
				}
				if (current.getDate().yyyy().equals(example.getDate().yyyy())
						&& current.getDate().MM()
								.equals(example.getDate().MM())
						&& current.getDate().dd()
								.equals(example.getDate().dd())) {
					if (current.getDate().toString().equals(
							example.getDate().toString()))
						return false;
					if (current.getDate().toString().compareTo(
							example.getDate().toString()) < 0
							&& current.getEnd().toString().compareTo(
									example.getDate().toString()) > 0)
						return false;
					if (current.getDate().toString().compareTo(
							example.getDate().toString()) > 0
							&& current.getDate().toString().compareTo(
									example.getEnd().toString()) < 0)
						return false;
				}
			}
		}
		return true;
	}

	/**
	 * Modifica o filme da sessao
	 */
	private void changeMovie() {
		MovieData movieData = chooseMovie("modify");
		if (movieData != null)
			this.chooseSession.setCurrentMovie(movieData);
	}

	/**
	 * Modifica a sala da sessao
	 */
	private void changeRoom() {
		RoomData roomData = chooseRoom("modify");
		if (roomData != null)
			this.chooseSession.setCurrentRoom(roomData);
	}

	/**
	 * Modifica o horario e a data da sessao
	 */
	private void changeDate() {
		DataHours date = chooseDate();
		chooseSession.setDate(date);
	}

	/**
	 * Ira aparecer as sessoes e será pedido para escolher uma delas A escolha
	 * será feita 1º pela sala e depois pelo codigo da sessao, para que a busca
	 * seja mais rápida
	 * 
	 * @param restriction
	 * @return
	 */
	protected SessionData chooseSession(String restriction) {
		Scanner scanner = new Scanner(System.in);
		String idSession = null;
		boolean repeate = false;

		System.out.println("\nEscolha a sessao: ");
		do {
			
			viewSession(restriction);
			System.out.print("Digite o codigo da Sessao: ");
			idSession = scanner.next();
			try{
				if (mapSessionData.get((idSession.substring(0, 2).charAt(0) == '0') ? 
						new Integer(idSession.substring(1, 2)) : new Integer(idSession.substring(0, 2))) == null ||
						mapSessionData.get(new Integer(idSession.substring(0, 2))).get(idSession) == null)
					return null;
				if(restriction.equals("buy")){
					if(mapSessionData.get(new Integer(idSession.substring(0, 2))).get(idSession).getAvailability() <= 0){
						System.out.println("\n**ESGOTADA** Escolha outra sessao!\n");
						repeate = true;
					}else repeate = false;
				}else{
					if (mapSessionData.get(new Integer(idSession.substring(0, 2))).get(idSession).isSold()) {
						System.out.println("\nDigite apenas valores validos!\n");
						repeate = true;
					} else	repeate = false;
				}
			
			}catch(StringIndexOutOfBoundsException e){
				System.out.println("\nDigite apenas valores validos\n");
				scanner = new Scanner(System.in);
				repeate = true;
			}
		} while (repeate);

		return mapSessionData.get(new Integer(idSession.substring(0, 2))).get(idSession);
	}
}