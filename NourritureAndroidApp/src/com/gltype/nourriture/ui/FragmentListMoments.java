package com.gltype.nourriture.ui;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.gltype.nourriture.R;
import com.gltype.nourriture.adapter.ProfileListMomentAdapter;
import com.gltype.nourriture.model.Moment;
import com.gltype.nourriture.model.Product;

public class FragmentListMoments extends Fragment {

	private List<Moment> moments;
	private ListView ls_moments;
	private ProfileListMomentAdapter adapter;
	private Context context;
	
	public FragmentListMoments(List<Moment> moments, Context context) {
		this.moments = moments;
		this.context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.profile_fragment_moments, container, false);
		
		ls_moments = (ListView) view.findViewById(R.id.list_moments);
		adapter = new ProfileListMomentAdapter(moments, getActivity());
		ls_moments.setAdapter(adapter);
		
		ls_moments.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> list, View view, int position,
					long arg3) {
				Moment moment = (Moment) list.getItemAtPosition(position);
				Intent intent = new Intent(FragmentListMoments.this.getActivity(), MomentDetialActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("moment", moment);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		return view;
	}
}
