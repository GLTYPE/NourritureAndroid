package com.gltype.nourriture.ui;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gltype.nourriture.R;
import com.gltype.nourriture.model.Moment;

public class FragmentListMoments extends Fragment {

	private List<Moment> moments;
	private ListView ls_moments;
	private ArrayAdapter<Moment> adapter;
	
	public FragmentListMoments(List<Moment> moments) {
		this.moments = moments;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.profile_fragment_moments, null);
		
		ls_moments = (ListView) view.findViewById(R.id.list_moments);
//		adapter = new ArrayAdapter<Moment>(getActivity(), R.layout.profile_list_item_template);
		
		return view;
	}
}
