package com.model;

public class Genre {

	private int idGenre;
	private String genre;
	private String description;
	private String creationDate;
	
	//------------------------------------------------------------------------------------
	public Genre(){
		super();
	}
	//------------------------------------------------------------------------------------
	public Genre(int idGenre, String genre, String description) {
		super();
		this.idGenre = idGenre;
		this.genre = genre;
		this.description = description;
	}
	//------------------------------------------------------------------------------------
	public int getIdGenre() {
		return idGenre;
	}

	public void setIdGenre(int idGenre) {
		this.idGenre = idGenre;
	}
	//------------------------------------------------------------------------------------
	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	//------------------------------------------------------------------------------------
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	//------------------------------------------------------------------------------------
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	//------------------------------------------------------------------------------------
	@Override
	public String toString() {
		return genre;
	}
}