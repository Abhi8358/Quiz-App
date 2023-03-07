package com.example.finalassignmentquiz;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SummeryScreen extends Fragment {

    SendData sendData;
    View view;
    Button restart , quit;
    TextView correct , time;

    List<Question> data=new ArrayList<>();

    public static SummeryScreen newInstance(SendData sendData){
        SummeryScreen fragment = new SummeryScreen();
        Bundle bundle = new Bundle();
        bundle.putSerializable("dataobject",sendData);
        fragment.setArguments(bundle);
        return fragment;
    }
    public SummeryScreen(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments()!=null){
            this.sendData = (SendData) getArguments().getSerializable("dataobject");
            this.data = sendData.getQuestionList();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_summery_screen, container, false);


        restart = view.findViewById(R.id.restart);
        quit = view.findViewById(R.id.exit);
        correct = view.findViewById(R.id.correct);
        time= view.findViewById(R.id.totalAttempted);

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().moveTaskToBack(true);
                getActivity().finish();
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmManager am = (AlarmManager)   getActivity().getSystemService(Context.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + 500,
                        PendingIntent.getActivity(getActivity(), 0, getActivity().getIntent(), PendingIntent.FLAG_ONE_SHOT
                                | PendingIntent.FLAG_CANCEL_CURRENT));
                Intent i = getActivity().getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        calcuclation();
        return view;
    }


    void calcuclation(){

        int correct_question_click_by_user=0;
        for(int i=0;i<data.size();i++){
            Question question = data.get(i);
            if(question.getUserAns()!=-1){

                if(question.getUserAns()==question.getCorrectAns()){
                    correct_question_click_by_user++;
                }
            }
        }
        int time_taken = 600-QuestionViewModel.counter;
        time.setText(String.valueOf(time_taken/60)+":"+String.valueOf(time_taken%60)+" minut");
        correct.setText(String.valueOf(correct_question_click_by_user));
    }
    @Override
    public void onStop(){
        super.onStop();

    }

}