package System;

import java.util.InputMismatchException;
import java.util.Scanner;

import MoviesData.MovieData;
import Startup.DataHours;


/**
 * PADRÃO SINGLETON
 */

 /**
 *Classe responsavel por gerenciar todas as tarefas do filme
 */
public class ManageMovie extends ManageMovies {
	private static ManageMovie instance;
	
	public ManageMovie() {}
	
	public static ManageMovie getInstance() {
		if (instance==null)	instance = new ManageMovie();
		return instance;
	}
	
	/**
	 * Exibi um menu no qual ele pode escolher se quer exibir, criar, modificar ou excluir um filme
	 */
	void optionMovie() {
		Scanner scanner = new Scanner(System.in);
		int option = 0;
		boolean repeate = true;
		boolean error = false;
		do {
			repeate = true;
			System.out.println("\t\nGerenciar Filmes...");
			try {
				do {
					System.out.println("\tExibir os Filme(1)");
					System.out.println("\tModificar os Filmes(2)");
					System.out.println("\tCriar os Filmes(3)");
					System.out.println("\tExcluir os Filmes(4)");
					System.out.println("\tVoltar ao menu anterior(5)");
					System.out.print("Digite o codigo: ");
					option = scanner.nextInt();

					if (option < 1 || option > 5) {
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
				viewMovie("view");
				break;
			case 2:
				modifyMovie();
				break;
			case 3:
				createMovie();
				break;
			case 4:
				deleteMovie();
				break;
			case 5:
				repeate = false;
				break;
			}
		} while (repeate);
	}

	/**
	 * Cria um filme
	 */
	private void createMovie() {
		System.out.print("\nCriando Filme...");
		String name = nameOfMovie();
		String gender = typeOfGender();
		Integer ageRate = chooseAgeRate();
		String dimension = chooseDimension();
		String language = chooseLanguage();
		DataHours duration = chooseDuration();
		Integer idMovie = idMovie();

		MovieData newMovieData = new MovieData(idMovie, name, gender, ageRate,
				dimension, language, duration);
		mapMovieData.put(idMovie, newMovieData);
		historic.AddHistoricModify("Criado o fime: " + newMovieData.toString());
		uploadData();
		System.out.println("Filme criado");
	}

	/**
	 * Modifica um filme
	 */
	private void modifyMovie() {
		Scanner scanner = new Scanner(System.in);
		MovieData currentMovie = chooseMovie("modify");
		if (currentMovie == null)
			return;

		String name = null;
		String gender = null;
		Integer ageRate = null;
		String dimension = null;
		String language = null;
		String duration = null;

		boolean verify = false;
		do {
			try {
				System.out.println("\nModificando Filme...");
				System.out.print("\nNome (" + currentMovie.getName() + "): ");
				name = scanner.next();
				System.out.print("\nGenero (" + currentMovie.getGender()	+ "): ");
				gender = scanner.next();
				System.out.print("\nFaixa Etaria (" + currentMovie.getAgeRate() + "): ");
				ageRate = scanner.nextInt();
				System.out.print("\nDimensao (" + currentMovie.getDimension()	+ "): ");
				dimension = scanner.next();
				System.out.print("\nIdioma (" + currentMovie.getLanguage() + "): ");
				language = scanner.next();
				System.out.print("\nDuracao (" + currentMovie.getDuration().toDuration() + "): ");
				duration = scanner.next();
				verify = true;
			} catch (InputMismatchException e) {
				System.out.println("Insira uma dimensao valida");
				scanner = new Scanner(System.in);
			} catch (NumberFormatException e) {
				System.out.println("Erro de formato, insira novamente");
				scanner = new Scanner(System.in);
			}
		} while (!verify);

		currentMovie.setName(name);
		currentMovie.setGender(gender);
		currentMovie.setAgeRate(ageRate);
		currentMovie.setDimension(dimension);
		currentMovie.setLanguage(language);
		currentMovie.setDuration(selectDuration(duration));
		
		mapMovieData.put(currentMovie.getIdMovie(), currentMovie);
		historic.AddHistoricModify("Modificado o fime: "
				+ currentMovie.toString());
		uploadData();
	}
	
	/**
	 * Deleta um filme
	 */
	private void deleteMovie() {
		MovieData currentMovie = chooseMovie("delete");
		if (currentMovie == null) {
			System.out.println("\nVoce nao selecionou nenhum Filme");
			return;
		} else {
			System.out.println("\nExcluido o filme e todas as suas sessoes");
			mapMovieData.remove(currentMovie.getIdMovie());
			for (Integer room : mapSessionData.keySet()) {
				for (String session : mapSessionData.get(room).keySet()) {
					if (mapSessionData.get(room).get(session).getCurrentMovie()
							.getIdMovie() == currentMovie.getIdMovie())
						mapSessionData.get(room).remove(session);
				}
			}

			mapSessionData.remove(currentMovie.getIdMovie());
			historic
					.AddHistoricModify("Excluido o filme e todas as suas sessoes: "
							+ currentMovie.toString());
			uploadData();
		}
	}

	/**
	 * Cria o id do filme sequencialmente, sem repeticao
	 * @return
	 */
	protected Integer idMovie() {
		if (mapMovieData == null || mapMovieData.isEmpty())
			return 1;
		else {
			int lastId = 0;
			for (Integer current : mapMovieData.keySet())
				lastId = mapMovieData.get(current).getIdMovie();
			return lastId + 1;
		}
	}
	
	/**
	 * Adiciona um nome ao filme
	 * @return
	 */
	protected String nameOfMovie() {
		Scanner scanner = new Scanner(System.in);
		String film = null;
		boolean continueLoop = false;
		do {
			try {
				System.out.print("\nDigite o nome do filme : ");
				film = scanner.next();
				continueLoop = true;
			} catch (IllegalArgumentException e) {
				System.out.println("Valores invalidos." + e);
				scanner = new Scanner(System.in);
			}
		} while (!continueLoop);
		return film;
	}

	/**
	 * Adiciona um genero ao filme
	 * @return
	 */
	protected String typeOfGender() {
		Scanner scanner = new Scanner(System.in);
		String genders = null;
		boolean continueLoop = false;
		do {
			try {
				System.out.print("Digite o genero do filme : ");
				genders = scanner.next();
				continueLoop = true;
			} catch (IllegalArgumentException e) {
				System.out.println("Valores invalidos." + e);
				scanner = new Scanner(System.in);
			}
		} while (!continueLoop);

		return genders;
	}

	/**
	 * Escolhe a faixa etaria do filme
	 * @return
	 */
	protected Integer chooseAgeRate() {
		Scanner scanner = new Scanner(System.in);
		Integer ageRate = 0;
		boolean spider = false;
		do {
			try {
				System.out.print("Digite a faixa etaria do filme : ");
				ageRate = scanner.nextInt();
				if (ageRate < 0) {
					System.out.println("Digite novamente.");
					spider = true;
				} else
					spider = false;
			} catch (InputMismatchException e) {
				System.out.println("Valor invalido" + e);
				scanner = new Scanner(System.in);
			} catch (NumberFormatException e) {
				System.out.printf("%s", e);
				System.out.println("Erro de formato, insira novamente");
				scanner = new Scanner(System.in);
			}
		} while (spider);
		return ageRate;
	}

	/**
	 * Escolhe a dimensao do filme
	 * @return
	 */
	protected String chooseDimension() {
		Scanner scanner = new Scanner(System.in);
		String dimension = "0";
		boolean continueLoop = false;
		do {
			try {
				System.out.print("Digite a dimensao do filme(2d ou 3d): ");
				dimension = scanner.next();
				continueLoop = true;
			} catch (IllegalArgumentException e) {
				System.out.println("Valores invalidos." + e);
				scanner = new Scanner(System.in);
			}
		} while (!continueLoop);

		return dimension;
	}

	/**
	 * Escolhe o idioma do filme
	 * @return
	 */
	protected String chooseLanguage() {
		Scanner scanner = new Scanner(System.in);
		String language = "0";
		boolean continueLoop = false;
		do {
			try {
				System.out.print("Digite o idioma do filme : ");
				language = scanner.next();
				continueLoop = true;
			} catch (IllegalArgumentException e) {
				System.out.println("Valores invalidos." + e);
				scanner = new Scanner(System.in);
			}
		} while (!continueLoop);

		return language;
	}
	
	/**
	 * Escolhe a duracao do filme
	 * @return
	 */
	private DataHours chooseDuration() {
		Scanner scanner = new Scanner(System.in);
		String dateString = "";
		DataHours newData = null;
		boolean continueLoop = false;
		do {
			try {
				System.out.print("Digite a duracao(hh-mm): ");
				dateString = scanner.next();
				continueLoop = true;
				
				newData = selectDuration(dateString);
			} catch (IllegalArgumentException e) {
				System.out.println("Valores invalidos." + e);
				scanner = new Scanner(System.in);
			}
		} while (!continueLoop || !checkData(newData));
		return newData;
	}
	
	private DataHours selectDuration(String newData) throws InputMismatchException{
		try{
			return new DataHours(1, 1, 1, new Integer(newData.substring(0, 2)), new Integer(newData.substring(3, 5)));
		}catch (InputMismatchException e) {
			throw e;
		}
	}
}