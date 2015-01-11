package com.gltype.nourriture.ui;

import java.util.List;

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
import com.gltype.nourriture.adapter.ProfileListLikeAdapter;
import com.gltype.nourriture.model.Product;

public class FragmentListLike extends Fragment {
	
	private List<Product> pros;
	private ListView lv_pros;
	private ProfileListLikeAdapter adapter;
	
	
	public FragmentListLike(List<Product> pros) {
		this.pros = pros;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view  = inflater.inflate(R.layout.profile_fragment_like, container, false);
		
		lv_pros = (ListView) view.findViewById(R.id.list_like);
		adapter = new ProfileListLikeAdapter(pros, getActivity());
		lv_pros.setAdapter(adapter);
		lv_pros.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> list, View view, int position,
					long arg3) {
				Product product = (Product) list.getItemAtPosition(position);
				Intent intent = new Intent(FragmentListLike.this.getActivity(), ProductDetialActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("product", product);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		return view;
	}
}
