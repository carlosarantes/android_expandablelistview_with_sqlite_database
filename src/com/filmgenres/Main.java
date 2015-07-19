package com.filmgenres;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Main extends Activity {

	private Intent it_genres, it_films, it_newfilms;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		it_genres = new Intent(Main.this, GenresActivity.class); 
		it_films = new Intent(Main.this, FilmsActivity.class); 
		it_newfilms = new Intent(Main.this, NewFilmsActivity.class); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		if (id == R.id.genres) {
			startActivity(it_genres);
			return true;
		}
		
		if (id == R.id.new_film) {
			startActivity(it_newfilms);
			return true;
		}
		
		if (id == R.id.all_films) {
			startActivity(it_films);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
