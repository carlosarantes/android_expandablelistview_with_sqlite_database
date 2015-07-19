package com.dao;

import java.util.ArrayList;

import com.model.Genre;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class GenreDAO {

	private final String TABLENAME = "genres";
	private final DBManager connectionInstance;
	private SQLiteDatabase writable;
	private SQLiteDatabase readable;
	private Cursor cursor;
	private ContentValues contentValues;
	
	//-----------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------	
	public GenreDAO(DBManager db){
		connectionInstance = db;
		writable = db.getWritableDatabase();
		readable = db.getReadableDatabase();
	}
	//-----------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------	
	public ArrayList<Genre> selectAllGenres(){
		
		ArrayList<Genre> allgenres;
		allgenres = new  ArrayList<Genre>();
		Genre genre;
		
		String sql = "SELECT *FROM "+TABLENAME+";";
		cursor = readable.rawQuery(sql, null);
		
		if(cursor.getCount() > 0){
			
			cursor.moveToFirst();
			do{
				genre = new Genre();
				
				genre.setIdGenre(cursor.getInt(0));
				genre.setGenre(cursor.getString(1));
				genre.setDescription(cursor.getString(2));
				genre.setCreationDate(cursor.getString(3));
				
				allgenres.add(genre);
			}while(cursor.moveToNext());
		}
		return allgenres;
	}
	
	//-----------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------	
	public boolean updateGenre(Genre genre){
		
		contentValues = new ContentValues();
		
		contentValues.put("genrename", genre.getGenre());
		contentValues.put("description", genre.getDescription());
		
		try{
			writable.update(TABLENAME, contentValues, "idGenre = "+genre.getIdGenre(), null);
			return true;
		}catch(Exception err){
			throw new IllegalStateException("The genre was found but could not be updated");
		}
	}
	//-----------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------
	public boolean insertGenre(Genre newgenre){
		
		contentValues = new ContentValues();
		
		contentValues.put("idGenre", newgenre.getIdGenre());
		contentValues.put("genrename", newgenre.getGenre());
		contentValues.put("description", newgenre.getDescription());
		contentValues.put("creationDate", newgenre.getCreationDate());
	
		String sql = "SELECT * FROM "+TABLENAME+" WHERE idGenre = "+newgenre.getIdGenre();
		cursor = readable.rawQuery(sql, null);
		if(cursor.getCount() > 0){
			this.updateGenre(newgenre);
		}else{
			try{
				writable.insert(TABLENAME, null, contentValues);
				return true;
			}catch(Exception err){
				throw new IllegalStateException("The genre couldn't be created!");
			}
		}
		return false;
	}
	//-----------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------	
	public boolean deleteGenre(Genre genre){
		try{
			writable.delete(TABLENAME, "idGenre="+genre.getIdGenre(), null);
			return true;
		}catch(Exception err){
			throw new IllegalStateException("The genre couldn't be deleted!");
		}
	}
	//-----------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------
	public int getMaxId(){
		int maxgenreid;
		
		String sql = "SELECT MAX(idGenre) FROM "+TABLENAME;
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
