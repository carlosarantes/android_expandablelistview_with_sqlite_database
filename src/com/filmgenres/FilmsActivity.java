package com.filmgenres;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import com.dao.DAOFactory;
import com.dao.FilmDAO;
import com.dao.GenreDAO;
import com.helpers.ExpandableAdapter;
import com.model.Film;
import com.model.Genre;

public class FilmsActivity extends Activity {
	
	private List<Genre> listGenres;
	private HashMap<Genre, List<Film>> listFilms;

	private Intent it_genres, it_newfilms;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_films);
		
		buildList();
		
		ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.ex_listall);
		expandableListView.setAdapter(new ExpandableAdapter(FilmsActivity.this, listGenres, listFilms));
		
		expandableListView.setOnChildClickListener(new OnChildClickListener(){
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				Toast.makeText(FilmsActivity.this, "Group: "+groupPosition+"| Item: "+childPosition, Toast.LENGTH_SHORT).show();
				return false;
			}
		});
		
		expandableListView.setOnGroupExpandListener(new OnGroupExpandListener(){
			@Override
			public void onGroupExpand(int groupPosition) {
				Toast.makeText(FilmsActivity.this, "Group (Expand): "+groupPosition, Toast.LENGTH_SHORT).show();
			}
		});
		
		expandableListView.setOnGroupCollapseListener(new OnGroupCollapseListener(){
			@Override
			public void onGroupCollapse(int groupPosition) {
				Toast.makeText(FilmsActivity.this, "Group (Collapse): "+groupPosition, Toast.LENGTH_SHORT).show();
			}
		});
		
		//expandableListView.setGroupIndicator(getResources().getDrawable(R.drawable.icon_group));
		
		it_genres = new Intent(FilmsActivity.this, GenresActivity.class); 
		it_newfilms = new Intent(FilmsActivity.this, NewFilmsActivity.class); 
	}
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------
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
		
		return super.onOptionsItemSelected(item);
	}
	//-------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------	
	public void buildList(){
		
		listGenres = new ArrayList<Genre>();
		listFilms = new HashMap<Genre, List<Film>>();
		
		DAOFactory dao = new DAOFactory(this);
		dao.openConnection();
		GenreDAO genreDAO = dao.createGenreDAO();
			
		listGenres = genreDAO.selectAllGenres();
		//----------------------------------------------------
		FilmDAO filmDAO = dao.createFilmDAO();
		for(int i = 0;i<listGenres.size();i++){		
			List<Film> auxList = filmDAO.selectFilmsByGenre(listGenres.get(i));
			listFilms.put(listGenres.get(i), auxList);
		}
		dao.closeConnection();
	}
}
