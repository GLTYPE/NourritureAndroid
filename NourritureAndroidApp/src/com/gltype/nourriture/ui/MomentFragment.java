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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

public class MomentFragment extends Fragment {
	
	private ListView listView;
	private View root;
	private Context context;
	private View progresView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity(); 
		 
		root =  inflater.inflate(R.layout.fragment_moments, null);		
		listView = (ListView) root.findViewById(R.id.moments_list);
		progresView= root.findViewById(R.id.moment_progress);
		refresh();
		return root;
	}	
	public void refresh()
	{
		progresView.setVisibility(View.GONE);
		
		List<Moment> moments = new ArrayList<Moment>();
		Moment m1 = new Moment("aaaaa","asdfhkjhgfdsaf","http://p1.wmpic.me/article/2014/12/15/1418613396_lVMetWEM.jpeg");
		Moment m2 = new Moment("bbbbb","asdfhkjhgfdsaf","http://p1.wmpic.me/article/2014/12/12/1418364498_xKxQwHOQ.jpg");
		moments.add(m1);
		moments.add(m2);
		
		MomentAdapter adapter = new MomentAdapter(context, moments);
		listView.setAdapter(adapter);
		
		
	}

}
