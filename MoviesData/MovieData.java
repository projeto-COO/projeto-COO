//OK 17:00 17/05
package MoviesData;

import java.io.Serializable;

import Startup.DataHours;


/**
 * Classe que possui todos os atributos de um Filme
 */
public class MovieData implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idMovie;
	private String name;
	private String gender;
	private Integer ageRate;
	private String dimension;
	private String language;
	private DataHours duration;
	private boolean sold;

	public MovieData(Integer idMovie, String name, String gender,
			Integer ageRate, String dimension, String language, DataHours duration) {
		this.name = name;
		this.idMovie = idMovie;
		this.gender = gender;
		this.ageRate = ageRate;
		this.dimension = dimension;
		this.language = language;
		this.duration = duration;
		this.setSold(false);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getAgeRate() {
		return ageRate;
	}

	public void setAgeRate(Integer ageRate) {
		this.ageRate = ageRate;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	public void setDuration(DataHours duration) {
		this.duration = duration;
	}

	public DataHours getDuration() {
		return duration;
	}

	public Integer getIdMovie() {
		return idMovie;
	}

	public String toString() {
		return "Codigo:" + idMovie + "\tNome-" + name + "\tGenero-" + gender
				+ "\tF.E.:" + ageRate + "\tIdioma-" + language + "\tDuracao: " + 
				duration.hh() + ":" + duration.mm() ;
	}

	public void toShow() {
		System.out.println("Codigo:" + idMovie + "\tNome-" + name + "\tGenero-"
				+ gender + "\tF.E.:" + ageRate + "\tIdioma-" + language + "\tDuracao: " + 
				duration.hh() + ":" + duration.mm() + "\tVenda: "
				+ (isSold() ? "True" : "False"));
	}

	public void setSold(boolean sold) {
		this.sold = sold;
	}

	public boolean isSold() {
		return sold;
	}
}