package com.filmgenres;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dao.DAOFactory;
import com.dao.GenreDAO;
import com.helpers.ShowDialogAsyncTask;
import com.model.Genre;

public class GenresActivity extends Activity implements View.OnClickListener, OnItemClickListener, OnMenuItemClickListener, DialogInterface.OnClickListener{
	
	//-----------------------------------------------------------------------
	private EditText et_idgenre, et_genre, et_description;
	private Button bt_confirm;
	private ProgressBar pg_progress;
	private PopupMenu popupMenu;
	//-----------------------------------------------------------------------
	private ListView lv_listgenres;
	private ArrayAdapter<Genre> adaptgenres;
	//-----------------------------------------------------------------------
	private Genre selectedGenre;
	
	private Intent it_newfilms, it_films;
	//Drawable draw;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_genres);
		
		et_idgenre = (EditText) findViewById(R.id.et_idgenre);
		et_genre = (EditText) findViewById(R.id.et_genre);
		et_description = (EditText) findViewById(R.id.et_description);
		et_idgenre.setEnabled(false);
		
		pg_progress = (ProgressBar) findViewById(R.id.pg_progress);
	
	//    Drawable draw = res.getDrawable(R.drawable.customprogressbar);
		//pg_progress.setProgressDrawable(draw);
		
		pg_progress.setProgress(0);
		pg_progress.setVisibility(4);
		
		lv_listgenres = (ListView) findViewById(R.id.lv_listgenres);
		lv_listgenres.setOnItemClickListener(this);
		
		bt_confirm = (Button) findViewById(R.id.bt_confirm);
		bt_confirm.setOnClickListener(this);
		
		fillGenreList();
		setNewId();
		
		it_films = new Intent(GenresActivity.this, FilmsActivity.class); 
		it_newfilms = new Intent(GenresActivity.this, NewFilmsActivity.class); 
	}
	//--------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
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
	//--------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------
	@Override
	public void onClick(View v) {
	
		if(v.getId() == R.id.bt_confirm){

			int blanks = 0;
			int idGenre;
			String name, description, creationDate;
			
			idGenre = Integer.parseInt(et_idgenre.getText().toString());
			name = et_genre.getText().toString(); 
			description = et_description.getText().toString(); 
			creationDate = "01/01/2015";
			
			if(name.length() < 2){
				blanks++;
				Toast.makeText(GenresActivity.this, "You must especify a validy genre name. Minimum char length = 2!", Toast.LENGTH_SHORT).show();
			}
			
			if(description.length() < 10){
				blanks++;
				Toast.makeText(GenresActivity.this, "You must especify a validy genre description. Minimum char length = 10!", Toast.LENGTH_SHORT).show();
			}
			
			if(blanks == 0){
				
				updateProgressStatus();
				
				Genre newgenre;
				newgenre = new Genre();
				
				newgenre.setIdGenre(idGenre);
				newgenre.setGenre(name);
				newgenre.setDescription(description);
				newgenre.setCreationDate(creationDate);
				
				DAOFactory dao = new DAOFactory(this);
				dao.openConnection();
				GenreDAO genreDAO = dao.createGenreDAO();
				
				genreDAO.insertGenre(newgenre);
				dao.closeConnection();
				
				cleanFields();
				setNewId();
				fillGenreList();
			}else{
				blanks = 0; 
			}
		}
	}
	
	public void setNewId(){
		
		int newid;
		
		DAOFactory dao = new DAOFactory(this);
		dao.openConnection();
		GenreDAO genreDAO = dao.createGenreDAO();
		
		newid = genreDAO.sequncialId();
		et_idgenre.setText(""+newid);
		dao.closeConnection();
	}
	
	public void fillGenreList(){
		
		DAOFactory dao = new DAOFactory(this);
		dao.openConnection();
		GenreDAO genreDAO = dao.createGenreDAO();
		
		ArrayList<Genre> allgenres = genreDAO.selectAllGenres();
		adaptgenres = new ArrayAdapter<Genre>(GenresActivity.this ,R.layout.itemlist_baselayout, allgenres);
		lv_listgenres.setAdapter(adaptgenres);
		dao.closeConnection();
	}
	
	public void cleanFields(){
		et_genre.setText("");
		et_description.setText("");
	}

	@Override
	public boolean onMenuItemClick(MenuItem item){
		
		if(item.getItemId()== R.id.delete_genre){
			
			AlertDialog ad = new AlertDialog.Builder(GenresActivity.this)
			.setMessage("Are you sure you want to delete the genre '"+selectedGenre.getGenre().toUpperCase()+"'?")
			.setIcon(R.drawable.ic_launcher)
			.setTitle("Genres")
			.setPositiveButton("YES", this)
			.setNegativeButton("NO", this)
			.setNeutralButton("Cancel", this)
			.setCancelable(false)
			.create();
			ad.show();
		}
		
		if(item.getItemId()== R.id.update_genre){
			
			int idGenre;
			String genrename, genredescription;
			
			idGenre = selectedGenre.getIdGenre();
			genrename = selectedGenre.getGenre();
			genredescription = selectedGenre.getDescription();
			
			et_idgenre.setText(""+idGenre);
			et_genre.setText(genrename);
			et_description.setText(genredescription);
		}
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		selectedGenre = (Genre)lv_listgenres.getItemAtPosition(position);
		
		popupMenu = new PopupMenu(GenresActivity.this, view); 
		popupMenu.setOnMenuItemClickListener(this);
		popupMenu.inflate(R.menu.mymenu);
		popupMenu.show();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		
		
		switch(which) {
		
		   case DialogInterface.BUTTON_POSITIVE:
			
			  updateProgressStatus(); 
			   
			  DAOFactory dao = new DAOFactory(this);
			  dao.openConnection();
			  GenreDAO genreDAO = dao.createGenreDAO();
				
				 if(!genreDAO.deleteGenre(selectedGenre)){
				     Toast.makeText(GenresActivity.this, "The genre "+selectedGenre.getGenre()+" couldn't be deleted!", Toast.LENGTH_SHORT).show();
				 }else{
				 	Toast.makeText(GenresActivity.this, "The genre "+selectedGenre.getGenre()+" was successfully deleted!", Toast.LENGTH_SHORT).show();
				 }	
			  dao.closeConnection();
			  cleanFields();
			  setNewId();
			  fillGenreList();
			  break;
		}
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