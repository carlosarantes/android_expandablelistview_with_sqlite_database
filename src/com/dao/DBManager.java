package com.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper{

	public final static String NAME = "cinema";
	public final static int VERSION = 1;
	
	public final String TABLES[] = {"genres", "films"};
	
	
	public DBManager(Context context) {
		super(context, NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
	String sql = " CREATE TABLE "+TABLES[0]+" ( " 
				+ " idGenre             INTEGER       PRIMARY KEY AUTOINCREMENT NOT NULL, "
				+ " genrename           VARCHAR(50)   NOT NULL, "
				+ " description         VARCHAR(255)  NOT NULL, "
				+ " creationDate        VARCHAR(10)   NOT NULL);";
		db.execSQL(sql);
		
		   sql = " CREATE TABLE "+TABLES[1]+" ( "
			  + " idFilm        INTEGER       PRIMARY KEY AUTOINCREMENT NOT NULL,"
			  + " name          VARCHAR(150)  NOT NULL, "
			  + " year          INTEGER       NOT NULL, "
			  + " dtrelease     VARCHAR(10)   NOT NULL, "
			  + " directorsname VARCHAR(100)  NOT NULL, "
			  + " description   TEXT,         NOT NULL, "
			  + " duration      INTEGER       NOT NULL, "
			  + " country       VARCHAR(100)  NOT NULL, "
			  + " dtcreation    VARCHAR(10)   NOT NULL, "
			  + " fkidGenre     INTEGER       NOT NULL, " 
			  + " FOREIGN KEY(fkidGenre) REFERENCES genres(idGenre));";
		db.execSQL(sql);   	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = " DROP TABLE IF EXISTS"+TABLES[0]+";";
		db.execSQL(sql);
		       sql = " DROP TABLE IF EXISTS"+TABLES[1]+";";
		db.execSQL(sql);
		this.onCreate(db);
	}
}