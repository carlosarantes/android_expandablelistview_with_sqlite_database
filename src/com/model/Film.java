package com.model;

import java.util.ArrayList;

public class Film {

 	private int idFilm;
 	private String name;
 	private int year;
 	private String release;
 	private String directorsname;
 	private String description;
 	private int duration;//MINUTES
 	private String country;
 	private Genre mainGenre;
 	private String creationDate;
 	
 	//-----------------------------------------------------------------------------------
 	public Film() {
		super();
	}
 	//-----------------------------------------------------------------------------------
 	public Film(int idFilm, String name, int year, String release,
			String directorsname, ArrayList<String> mainactors,
			String description, int duration, String country,
			ArrayList<Genre> othersGenres, Genre mainGenre, String creationDate) {
		super();
		this.idFilm = idFilm;
		this.name = name;
		this.year = year;
		this.release = release;
		this.directorsname = directorsname;
		this.description = description;
		this.duration = duration;
		this.country = country;
		this.mainGenre = mainGenre;
		this.creationDate = creationDate;
	}
	//-----------------------------------------------------------------------------------
	public int getIdFilm() {
		return idFilm;
	}
	
	public void setIdFilm(int idFilm) {
		this.idFilm = idFilm;
	}
	//-----------------------------------------------------------------------------------
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	//-----------------------------------------------------------------------------------
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	//-----------------------------------------------------------------------------------
	public String getRelease() {
		return release;
	}
	public void setRelease(String release) {
		this.release = release;
	}
	//-----------------------------------------------------------------------------------
	public String getDirectorsname() {
		return directorsname;
	}
	public void setDirectorsname(String directorsname) {
		this.directorsname = directorsname;
	}
	//-----------------------------------------------------------------------------------
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	//-----------------------------------------------------------------------------------
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	//-----------------------------------------------------------------------------------
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	//-----------------------------------------------------------------------------------
	public Genre getMainGenre() {
		return mainGenre;
	}
	public void setMainGenre(Genre mainGenre) {
		this.mainGenre = mainGenre;
	}
	//-----------------------------------------------------------------------------------
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
	@Override
	public String toString() {
		return ".	"+idFilm + " - "+name+", "+year;
	}
}