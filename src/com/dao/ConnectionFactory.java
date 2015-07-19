package com.dao;

import android.content.Context;
import android.widget.Toast;

public class ConnectionFactory {

	private static DBManager database;
	
	public static DBManager getConnection(Context ctx){
		try{
			database = new DBManager(ctx);
		}catch(Exception err){
			Toast.makeText(ctx, "Connection couldn't be established!", Toast.LENGTH_SHORT).show();
			throw new IllegalStateException("Error: "+err.getMessage());
		}
		return database;
	}
}
