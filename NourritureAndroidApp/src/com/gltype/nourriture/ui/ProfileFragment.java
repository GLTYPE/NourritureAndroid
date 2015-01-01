package com.gltype.nourriture.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.gltype.nurriture.R;

public class ProfileFragment extends Fragment {
	private LinearLayout imgBtn_editProfile;
	private EditText et_username,et_userlevel;
	private ListView lv_like, lv_recipe, lv_moments;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_profile, null);
		
		imgBtn_editProfile = (LinearLayout) view.findViewById(R.id.edit_profile);
		et_username = (EditText) (view.findViewById(R.id.profile_username));
		et_userlevel = (EditText) (view.findViewById(R.id.profile_userlevel));
		imgBtn_editProfile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				et_userlevel.setEnabled(true);
				et_username.setEnabled(true);
				
			}
		});
		return view;
	}
}
