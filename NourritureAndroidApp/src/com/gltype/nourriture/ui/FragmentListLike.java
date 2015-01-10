package com.gltype.nourriture.ui;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gltype.nourriture.R;
import com.gltype.nourriture.model.Product;

public class FragmentListLike extends Fragment {
	
	private List<Product> pros;
	public FragmentListLike(List<Product> pros) {
		this.pros = pros;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.profile_fragment_like, container, false);
	}
}
