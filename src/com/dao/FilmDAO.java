package com.dao;

import java.util.ArrayList;

import com.model.Film;
import com.model.Genre;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FilmDAO {
	
	private final String TABLENAME = "films";
	private final DBManager connectionInstance;
	private SQLiteDatabase writable;
	private SQLiteDatabase readable;
	private Cursor cursor;
	private ContentValues contentValues;
	
	public FilmDAO(DBManager db){
		connectionInstance = db;
		writable = db.getWritableDatabase();
		readable = db.getReadableDatabase();
	}
	//-----------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------
	public ArrayList<Film> selectFilmsByGenre(Genre genre){
		
		ArrayList<Film> films;
		films = new ArrayList<Film>();
		
		String sql = " SELECT * FROM "+TABLENAME+" WHERE fkidGenre = "+genre.getIdGenre();
		cursor = readable.rawQuery(sql, null);
		if(cursor.getCount() > 0){
			
			Film film;
			cursor.moveToFirst();
			
			do{
			   film = new Film();
			   film.setIdFilm(cursor.getInt(0));	
			   film.setName(cursor.getString(1));
			   film.setYear(cursor.getInt(2));
			   film.setRelease(cursor.getString(3));
			   film.setDirectorsname(cursor.getString(4));
			   film.setDescription(cursor.getString(5));
			   film.setDuration(cursor.getInt(6));
			   film.setCountry(cursor.getString(7));
			   film.setCreationDate(cursor.getString(8));
			   
			   Genre mainGenre = new Genre();
			   mainGenre.setIdGenre(cursor.getInt(9));
			   
			   film.setMainGenre(mainGenre);
			   
			   films.add(film);
			   
			  /* Log.i("teste de dados", "----------------------------------------------------");
			   Log.i("teste", ""+cursor.getInt(0)+"-"+cursor.getString(1)+"-"+cursor.getInt(2));
			   Log.i("teste", cursor.getString(4));
			   Log.i("teste", cursor.getString(5));
			   Log.i("teste", "----------------------------------------------------"); */
			   
			}while(cursor.moveToNext());	
		}
		
		return films;
	}
	
	//-----------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------		
	public boolean updateFilm(ContentValues upcontentValues){
		
		int idFilm = upcontentValues.getAsInteger("idFilm");
		
		try{
			writable.update(TABLENAME, upcontentValues, " idFilm = "+idFilm, null);
			return true;
		}catch(Exception err){
			throw new IllegalStateException("The genre was found but could not be updated");
		}
	}
	//-----------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------	
	public boolean insertFilm(Film newfilm){
		
		contentValues = new ContentValues();
		
		contentValues.put("idFilm", newfilm.getIdFilm());
		contentValues.put("name", newfilm.getName());
		contentValues.put("year", newfilm.getYear());
		contentValues.put("dtrelease", newfilm.getRelease());
		contentValues.put("directorsname", newfilm.getDirectorsname());
		contentValues.put("description", newfilm.getDescription());
		contentValues.put("duration", newfilm.getDuration());
		contentValues.put("country", newfilm.getCountry());
		contentValues.put("dtcreation", newfilm.getCreationDate());
		contentValues.put("fkidGenre", newfilm.getMainGenre().getIdGenre());
		
		String sql = "SELECT * FROM "+TABLENAME+" WHERE idFilm = "+newfilm.getIdFilm();
		cursor = readable.rawQuery(sql, null);
		if(cursor.getCount() > 0){
			this.updateFilm(contentValues);
		}else{
			try{
				writable.insert(TABLENAME, null, contentValues);
				return true;
			}catch(Exception err){
				throw new IllegalStateException("The film couldn't be created!");
			}
		}
		return false;
	}
	//-----------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------
	public int getMaxId(){
		int maxgenreid;
		
		String sql = "SELECT MAX(idFilm) FROM "+TABLENAME;
		cursor = readable.rawQuery(sql, null);
		if(cursor.getCount() == 0){
			maxgenreid = 0;
		}else{
			cursor.moveToFirst();
			maxgenreid = cursor.getInt(0);
		}
		return maxgenreid;
	}
	//-----------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------	
	public int sequncialId(){
		
		int newId;
		
		newId = this.getMaxId();
		newId = newId +1;
		
		return newId;
	}	
}