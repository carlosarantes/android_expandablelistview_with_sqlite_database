package com.dao;

import android.content.Context;
import android.widget.Toast;

public class DAOFactory {

	private Context ctx;
	private DBManager connection;
 	
	//--------------------------------------------------------------------------------------------
	public DAOFactory(Context ctx){
		this.ctx = ctx;
	}
	//--------------------------------------------------------------------------------------------
	public GenreDAO createGenreDAO(){
		if(connection == null){
			Toast.makeText(ctx, "You must establish the connection first!", Toast.LENGTH_SHORT).show();
			throw new IllegalStateException("You must establish the connection first");
		}else{
			GenreDAO genreDAO = new GenreDAO(connection);
			return genreDAO;
		}
	}
	//--------------------------------------------------------------------------------------------
	public FilmDAO createFilmDAO(){
		if(connection == null){
			Toast.makeText(ctx, "You must establish the connection first!", Toast.LENGTH_SHORT).show();
			throw new IllegalStateException("You must establish the connection first");
		}else{
			FilmDAO filmDAO = new FilmDAO(connection);
			return filmDAO;
		}
	}
	//--------------------------------------------------------------------------------------------
	public void openConnection(){
		
		if(connection == null){
			try{
				connection = ConnectionFactory.getConnection(ctx);
			}catch(Exception err){
				Toast.makeText(ctx, "There was an error establishing a connection with the android database", Toast.LENGTH_SHORT).show();
			}	
		}else{
				Toast.makeText(ctx, "Connecting is already established", Toast.LENGTH_SHORT).show();
		}
		
	}
	//--------------------------------------------------------------------------------------------
	public void closeConnection(){
		if(connection == null){
			Toast.makeText(ctx, "You mustn't close a connection that was not established!", Toast.LENGTH_SHORT).show();
			throw new IllegalStateException("You can't close this connection!");
		}else{
			connection.close();
		}
	}
	//--------------------------------------------------------------------------------------------
}
