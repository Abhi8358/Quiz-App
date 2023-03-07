package com.example.finalassignmentquiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class SetupScreen extends Fragment {

    final String TAG = "SetupScreen";
    View view;
    Button button;
    SetupScreen_interface setupscreen_interface;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"OnCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setupscreen_interface = (SetupScreen_interface) getContext();
        view = inflater.inflate(R.layout.fragment_setup_screen, container, false);
        Log.d(TAG,"OnCreateView");
        button = view.findViewById(R.id.start);
        button.setOnClickListener(view -> {
            setupscreen_interface.click();
        });

        return view;
    }

    interface SetupScreen_interface{
        void click();
    }

}