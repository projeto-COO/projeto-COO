//OK 17:00 17/05
package startup;

import java.io.Serializable;
/**
 * Classe criada para suprir a necessidade de se utilizar data e horario
 */
@SuppressWarnings("serial")
public class DataHours implements Serializable {

	Integer dd;
	Integer MM;
	Integer yyyy;
	Integer hh;
	Integer mm;
	
	public DataHours(Integer dd, Integer MM, Integer yyyy, Integer hh, Integer mm) {
		this.dd = dd;
		this.MM = MM;
		this.yyyy = yyyy;
		this.hh = hh;
		this.mm =mm;
	}
	
	public DataHours(Integer hh, Integer mm) {
		this.hh = hh;
		this.mm =mm;
	}
	
	public Integer dd() {
		return dd;
	}
	public Integer MM() {
		return MM;
	}
	public Integer yyyy() {
		return yyyy;
	}
	public Integer hh() {
		return hh;
	}
	public Integer mm() {
		return mm;
	}
	
	public void setdd(Integer dd) {
		this.dd = dd;
	}

	public void setMM(Integer mM) {
		MM = mM;
	}

	public void setyyyy(Integer yyyy) {
		this.yyyy = yyyy;
	}

	public void sethh(Integer hh) {
		this.hh = hh;
	}

	public void setmm(Integer mm) {
		this.mm = mm;
	}

	public String toString(){
		return dd.toString()+MM.toString()+yyyy.toString()+hh.toString()+mm.toString();
	}
}