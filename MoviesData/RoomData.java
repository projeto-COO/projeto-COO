//OK 17:00 17/05
package MoviesData;

import java.io.Serializable;

/**
 * Classe que possui todos os atributos de uma Sala
 */
public class RoomData implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idRoom; // NOME DE IDENTIFICAO DA SALA
	private boolean[][] room;
	private Integer rows;
	private Integer columns;
	private Integer capacity;
	private boolean sold;

	// CONSTRUTOR DA CLASSE Room.
	public RoomData(Integer idRoom, Integer rows, Integer columns) {
		this.idRoom = idRoom;
		this.rows = rows;
		this.columns = columns;
		this.capacity = rows * columns;
		this.setSold(false);
		room = new boolean[rows][columns];
	}

	void recalculate() {
		this.capacity = rows * columns;
	}	
	
	public Integer getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(Integer idRoom) {
		this.idRoom = idRoom;
	}

	public boolean[][] getRoom() {
		return room;
	}

	public void setRoom(boolean[][] room) {
		this.room = room;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
		recalculate();
	}

	public Integer getColumns() {
		return columns;
	}

	public void setColumns(Integer columns) {
		this.columns = columns;
		recalculate();
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public String toString() {
		return "Codigo:" + idRoom + "\tQtd.Lugares-" + capacity	+ "\tVenda: " + (isSold() ? "True" : "False");
	}

	public void toShow() {
		System.out.println("Codigo:" + idRoom + "\tQtd.Lugares-" + capacity + "\tVenda: " + (isSold() ? "True" : "False"));
	}

	public void setSold(boolean sold) {
		this.sold = sold;
	}

	public boolean isSold() {
		return sold;
	}
}
