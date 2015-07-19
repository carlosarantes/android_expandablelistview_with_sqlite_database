package com.filmgenres;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.dao.DAOFactory;
import com.dao.FilmDAO;
import com.dao.GenreDAO;
import com.helpers.ShowDialogAsyncTask;
import com.model.Film;
import com.model.Genre;

public class NewFilmsActivity extends Activity implements OnClickListener {
	
	private ProgressBar pg_progress;
	private EditText et_idfilm, et_filmname, et_yearfilm, et_filmrealease, et_filmduration, et_countryfilm, et_filmdirector, et_filmdescription;
	private Spinner spin_genres;
	private Button bt_confirm;
	
	private ArrayList<Genre> genres;
	private ArrayAdapter<Genre> adaptgenres;
	private Film newFilm;
	
	private Genre selectedGenre;
	
	private Intent it_genres, it_films;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newfilmsactivity);
	
		et_idfilm          = (EditText) findViewById(R.id.et_idfilm);
		et_filmname        = (EditText) findViewById(R.id.et_filmname);
		et_yearfilm        = (EditText) findViewById(R.id.et_yearfilm);
		et_filmrealease    = (EditText) findViewById(R.id.et_filmrealease);
		et_filmduration    = (EditText) findViewById(R.id.et_filmduration);
		et_countryfilm 	   = (EditText) findViewById(R.id.et_countryfilm);
		et_filmdirector    = (EditText) findViewById(R.id.et_filmdirector);
		et_filmdescription = (EditText) findViewById(R.id.et_filmdescription);
		
		et_idfilm.setEnabled(false);
		spin_genres        = (Spinner) findViewById(R.id.spin_genres);
		
		pg_progress        = (ProgressBar) findViewById(R.id.pg_progress);
		pg_progress.setVisibility(4);
		bt_confirm         = (Button) findViewById(R.id.bt_confirm);
		bt_confirm.setOnClickListener(this);
		
		fillgenreSpinner();
		setNewId();
		
		spin_genres.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				selectedGenre = (Genre) spin_genres.getSelectedItem();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		
		it_genres = new Intent(NewFilmsActivity.this, GenresActivity.class); 
		it_films = new Intent(NewFilmsActivity.this, FilmsActivity.class); 		
	}
	//-----------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------	
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
		
		if (id == R.id.all_films) {
			startActivity(it_films);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	//-----------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------		
	@Override
	public void onClick(View v) {
		
		if(v.getId() == R.id.bt_confirm){
			
			int blanks = 0;
			int id, year, duration;
			String name, release, country, director, description;
			
			id = Integer.parseInt(et_idfilm.getText().toString());
			year = Integer.parseInt(et_yearfilm.getText().toString());
			duration = Integer.parseInt(et_filmduration.getText().toString());
			
			name = et_filmname.getText().toString();
			release = et_filmrealease.getText().toString();
			country  = et_countryfilm.getText().toString(); 
			director  = et_filmdirector.getText().toString();
			description  = et_filmdescription.getText().toString();
		
			if(blanks == 0){
				
				updateProgressStatus();
				
				newFilm = new Film();
				
				newFilm.setIdFilm(id);
				newFilm.setName(name);
				newFilm.setYear(year);
				newFilm.setRelease(release);
				newFilm.setDuration(duration);
				newFilm.setCountry(country);
				newFilm.setDirectorsname(director);
				newFilm.setMainGenre(selectedGenre);
				newFilm.setDescription(description);
				newFilm.setCreationDate("2015-01-01");
				
				DAOFactory dao = new DAOFactory(this);
				dao.openConnection();
				FilmDAO filmDAO = dao.createFilmDAO();
				
				if(filmDAO.insertFilm(newFilm)){
					Toast.makeText(NewFilmsActivity.this, "Film was successfully created", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(NewFilmsActivity.this, "There was an error creating the film", Toast.LENGTH_SHORT).show();
				}
				dao.closeConnection();
				
				cleanFields();
				setNewId();
			}else{
				blanks = 0;
			}
		}
	}
	//-----------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------	
	public void fillgenreSpinner(){
		
		DAOFactory dao = new DAOFactory(this);
		dao.openConnection();
		GenreDAO genreDAO = dao.createGenreDAO();
		genres = genreDAO.selectAllGenres();
		
		adaptgenres = new ArrayAdapter<Genre>(NewFilmsActivity.this, R.layout.itemlist_baselayout, genres);
		spin_genres.setAdapter(adaptgenres);
		dao.closeConnection();
	}
	//-----------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------	
	public void setNewId(){
		int newid;
		
		DAOFactory dao = new DAOFactory(this);
		dao.openConnection();
		FilmDAO filmDAO = dao.createFilmDAO();
		
		newid = filmDAO.sequncialId();
		et_idfilm.setText(""+newid);
		dao.closeConnection();
	}
	//-----------------------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------------------	
	public void cleanFields(){
		et_idfilm.setText("");	
		et_filmname.setText("");
		et_yearfilm.setText("");
		et_filmrealease.setText("");
		et_filmduration.setText("");
		et_countryfilm.setText("");
		et_filmdirector.setText("");
		et_filmdescription.setText("");
		
		spin_genres.setSelection(0);
	}
	//-------------------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------------------
	public void updateProgressStatus(){
		ShowDialogAsyncTask task = new ShowDialogAsyncTask();
        task.setProgressBar(pg_progress);
        task.execute();
	}
}
