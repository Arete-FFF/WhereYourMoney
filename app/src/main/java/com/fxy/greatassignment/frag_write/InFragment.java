package com.fxy.greatassignment.frag_write;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fxy.greatassignment.R;

/**
 * 添加新账单的fragment
 * 收入账单
 */
public class InFragment extends Fragment {


    public  InFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_in, container, false);
    }
}