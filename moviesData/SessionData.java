//OK 17:00 17/5 
package moviesData;

import java.io.Serializable;
import startup.DataHours;

/**
 * Classe que possui todos os atributos de uma Sessao
 */
public class SessionData implements Serializable {

	private static final long serialVersionUID = 1L;
	private MovieData currentMovie;
	private DataHours date;
	private DataHours end;
	private RoomData currentRoom;
	private String idSession;
	private boolean sold; // Verifica se a sessao ja foi vendida

	public SessionData(MovieData currentMovie, DataHours date,
			RoomData currentRoom) {
		this.currentMovie = currentMovie;
		this.date = date;
		this.currentRoom = currentRoom;
		this.setSold(false);
		createID();
		modifyEnd();
	}

	public MovieData getCurrentMovie() {
		return currentMovie;
	}

	public void setCurrentMovie(MovieData currentMovie) {
		this.currentMovie = currentMovie;
		createID();
	}

	public DataHours getDate() {
		return date;
	}

	public void setDate(DataHours date) {
		this.date = date;
		createID();
		modifyEnd();
	}

	public RoomData getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(RoomData currentRoom) {
		this.currentRoom = currentRoom;
		createID();
	}

	public String getIdSession() {
		return idSession;
	}
	
	public DataHours getEnd() {
		return end;
	}
	
	/**
	 * Caso a soma da duracao com o inicio do filme quebre alguma regra de horario, isto sera redefinido
	 */
	private void modifyEnd() {
		this.end = new DataHours(date.dd(), date.MM(), date.yyyy(), date.hh() + currentMovie.getDuration().hh(), 
					date.mm() + currentMovie.getDuration().mm());
		if(end.mm() > 59){
			end.sethh(end.hh()+1);
			end.setmm(end.mm()-60);
		}
		if(end.hh() > 23){
			end.setdd(end.dd()+1);
			end.sethh(end.hh()-24);
		}
	}

	/**
	 * O ID da sessao é criado da seguinte forma:
	 * idSala + idFilme + Data e Horario da sessao(exceto o ano)
	 */
	public void createID() {
		this.idSession = (currentRoom.getIdRoom() < 10 ? "0" : "")
				+ currentRoom.getIdRoom()
				+ (currentMovie.getIdMovie() < 10 ? "0" : "")
				+ currentMovie.getIdMovie() + (date.dd() < 10 ? "0" : "")
				+ date.dd() + (date.MM() < 10 ? "0" : "") + date.MM() + ""
				+ (date.hh() < 10 ? "0" : "") + date.hh()
				+ (date.mm() < 10 ? "0" : "") + date.mm();
	}

	public void toShow() {
		System.out.println("Codigo:" + idSession + "\tFilme-"
				+ currentMovie.getName() + "\tSala-" + currentRoom.getIdRoom()
				+ "\tQtd.Lug-" + currentRoom.getCapacity() + "\tHorario "
				+ viewDate() + "\tVenda: " + (isSold() ? "True" : "False"));
	}

	public String toString() {
		return "Codigo:" + idSession + "\tFilme-" + currentMovie.getName()
				+ "\tSala-" + currentRoom.getIdRoom() + "\tQtd.Lug-"
				+ currentRoom.getCapacity() + "\tHorario " + viewDate();
	}

	public String viewDate() {
		return date.dd() + "/" + date.MM() + "/" + date.yyyy() + " "
				+ date.hh() + ":" + date.mm();
	}

	public int getAvailable() {
		return currentRoom.availability();
	}

	public void setSold(boolean sold) {
		this.sold = sold;
	}

	public boolean isSold() {
		return sold;
	}
}