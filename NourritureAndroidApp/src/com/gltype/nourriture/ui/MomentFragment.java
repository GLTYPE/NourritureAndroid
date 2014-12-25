package com.gltype.nourriture.ui;

import java.util.ArrayList;
import java.util.List;

import com.gltype.nourriture.adapter.MomentAdapter;
import com.gltype.nourriture.model.Moment;
import com.gltype.nurriture.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MomentFragment extends Fragment {
	
	private ListView listView;
	private View root;
	private Context context;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity(); 
		 
		root =  inflater.inflate(R.layout.fragment_moments, null);		
		listView = (ListView) root.findViewById(R.id.moments_list);
		refresh();
		return root;
	}	
	public void refresh()
	{
		
		
		List<Moment> moments = new ArrayList<Moment>();
		Moment m1 = new Moment("aaaaa","asdfhkjhgfdsaf");
		Moment m2 = new Moment("bbbbb","asdfhkjhgfdsaf");
		moments.add(m1);
		moments.add(m2);
		
		MomentAdapter adapter = new MomentAdapter(context, moments);
		listView.setAdapter(adapter);
		
		
	}

}
