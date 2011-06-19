package MoviesData;

import java.io.Serializable;

import Startup.DataHours;

public class HistoricData implements Serializable{


	private static final long serialVersionUID = 1L;
	private Object object;
	private String situation;
	private DataHours date;
	
	public HistoricData(Object object, String situation, DataHours date) {
		this.object = object;
		this.situation = situation;
		this.date = date;
	}
	
	public Object getObject() {
		return object;
	}
	
	public String getSituation() {
		return situation;
	}
	
	public DataHours getDate() {
		return date;
	}
}
