package com.helpers;

import java.util.HashMap;
import java.util.List;

import com.filmgenres.R;
import com.model.Film;
import com.model.Genre;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableAdapter extends BaseExpandableListAdapter {
	
	private List<Genre> listGenres;
	private HashMap<Genre, List<Film>> listFilms;
	private LayoutInflater inflater;
	
	public ExpandableAdapter(Context context, List<Genre> listGenres, HashMap<Genre, List<Film>> listFilms){
		this.listGenres = listGenres;
		this.listFilms = listFilms;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getGroupCount() {
		return listGenres.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return listFilms.get(listGenres.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return listGenres.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return listFilms.get(listGenres.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		ViewHolderGroup holder;
		
		if(convertView == null){
			convertView = inflater.inflate(R.layout.header_expandable_list_view, null);
			holder = new ViewHolderGroup();
			convertView.setTag(holder);
			
			holder.tvGroup = (TextView) convertView.findViewById(R.id.tvGroup);
		}
		else{
			holder = (ViewHolderGroup) convertView.getTag();
		}

		holder.tvGroup.setText(listGenres.get(groupPosition).toString());
		
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		
		ViewHolderItem holder;
		String val = getChild(groupPosition, childPosition).toString();
		
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_expandable_list_view, null);
			holder = new ViewHolderItem();
			convertView.setTag(holder);
			
			holder.tvItem = (TextView) convertView.findViewById(R.id.tvItem);
		}
		else{
			holder = (ViewHolderItem) convertView.getTag();
		}
		holder.tvItem.setText(val);
		
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	class ViewHolderGroup {
		TextView tvGroup;
	}
	
	class ViewHolderItem {
		TextView tvItem;
	}
}
