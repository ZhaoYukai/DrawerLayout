package com.drawerlayoutusing.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContentFragment extends Fragment {
	
	private TextView textView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_content , container , false);
		textView = (TextView) view.findViewById(R.id.id_textView);
		
		//获得单击菜单项后传入的那个参数，根据 键名"text"进行提取
		String text = getArguments().getString("text");
		textView.setText(text);
		
		return view;
	}

}
