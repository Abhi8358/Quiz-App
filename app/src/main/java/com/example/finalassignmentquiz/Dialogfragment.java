package com.example.finalassignmentquiz;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;


public class Dialogfragment extends androidx.fragment.app.DialogFragment {

    View view;
    Button ok , cancel;
    List<Question>questionList;
    Opensummryscreeninterface opensummryscreeninterface;

    public Dialogfragment(List<Question>questionList){
        this.questionList = questionList;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_dialog, container, false);
        ok = view.findViewById(R.id.ok);
        cancel = view.findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensummryscreeninterface.clicksummeryscreem(questionList);
                getDialog().dismiss();
            }
        });
        opensummryscreeninterface = (Opensummryscreeninterface) getActivity();
        return view;
    }
    interface Opensummryscreeninterface{
        void clicksummeryscreem(List<Question> questionList);
    }
}