package com.example.finalassignmentquiz;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class QuestionDetailScreen extends Fragment {

    int position;
    String TAG = "QuestionDetailScreen";
    View view;
    TextView questionname , timer;
    RadioButton A,B,C,D , clickedbutton;
    List<Question>questionlist=new ArrayList<>();
    Button next , prev , submit ,bookmark;
    Summmryscreenopen summmryscreenopen;
    RadioGroup option;
    private static SendData sendData;



    public QuestionDetailScreen(int position,List<Question>questionlist) {
        // Required empty public constructor
        this.position = position;
        this.questionlist = questionlist;
    }
    public QuestionDetailScreen(){

    }
    public static QuestionDetailScreen newInstance(SendData sendData){
        QuestionDetailScreen questionDetailScreen = new QuestionDetailScreen();
        Bundle bundle = new Bundle();
        bundle.putSerializable("sendData",sendData);
        questionDetailScreen.setArguments(bundle);
        return questionDetailScreen;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        if(getArguments() != null){
            this.sendData = (SendData) getArguments().getSerializable("sendData");
            this.questionlist = sendData.getQuestionList();
            this.position = sendData.getPosition();
            if(sendData.getQuestionList()!=null && sendData.getQuestionList().size()!=0){
                QuestionViewModel.questionlist = sendData.getQuestionList();
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view=inflater.inflate(R.layout.fragment_question_detail_screen, container, false);
         Log.d(TAG, "onCreateView: ");
         questionname = view.findViewById(R.id.questiondetail);
         A = view.findViewById(R.id.optionA);
         B = view.findViewById(R.id.optionB);
         C = view.findViewById(R.id.optionC);
         D = view.findViewById(R.id.optionD);
         option = view.findViewById(R.id.rediogroup);
         next = view.findViewById(R.id.next);
         prev = view.findViewById(R.id.prev);
         submit = view.findViewById(R.id.submit);
         bookmark = view.findViewById(R.id.book);
         timer = view.findViewById(R.id.timer1);
         setView();
         next.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 nextfunction();
             }
         });
         prev.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 prevfunction();
             }
         });
         submit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 submitfunction();
             }
         });
         bookmark.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 bookmarkfunction();
             }
         });
         summmryscreenopen = (Summmryscreenopen) getActivity();
        startTimer();

         return view;
    }
    public void setView(){
        Question question = questionlist.get(position);
        questionname.setText(question.getQuestion());
        A.setText(question.getOptions().get(0));
        B.setText(question.getOptions().get(1));
        C.setText(question.getOptions().get(2));
        D.setText(question.getOptions().get(3));


        option.clearCheck();
        int previouscheckedid = question.getPrevious_button_checkedid();
        if (previouscheckedid!=-1){
            RadioButton checkbutton = view.findViewById(previouscheckedid);
            checkbutton.setChecked(true);
        }


    }


    CountDownTimer cTimer = null;
    //start timer function
    void startTimer() {
        int milliinfuture = QuestionViewModel.counter*1000;

        cTimer = new CountDownTimer(milliinfuture, 1000) {
            public void onTick(long millisUntilFinished) {
                int second = QuestionViewModel.counter%60;
                if(second<10){
                    timer.setText(String.valueOf(((QuestionViewModel.counter)/60))+":"+"0"+String.valueOf((QuestionViewModel.counter)%60));
                }
                else{
                    timer.setText(String.valueOf(((QuestionViewModel.counter)/60))+":"+String.valueOf((QuestionViewModel.counter)%60));
                }
                QuestionViewModel.counter--;

            }
            public void onFinish() {
                timer.setText("Finish");

                summmryscreenopen.onclicksummry(questionlist);
            }
        };
        cTimer.start();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        if(cTimer!=null){
            cTimer.cancel();
        }
        Log.d(TAG, "onDestroy: ");
        if(this.questionlist!=null && questionlist.size()!=0){
            QuestionViewModel.questionlist = this.questionlist;    
        }
        QuestionViewModel.position = this.position;
    }
    @Override
    public void onStop(){
        super.onStop();
        if(cTimer!=null){
            cTimer.cancel();
        }
        Log.d(TAG, "onStop: ");
        if(this.questionlist!=null && questionlist.size()!=0){
            QuestionViewModel.questionlist = this.questionlist;
        }
        QuestionViewModel.position = this.position;
    }
 
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }

    public void set_userans_ans_selectedid(){
        int selectedId=option.getCheckedRadioButtonId();

        Question question = questionlist.get(position);
        if(selectedId!=-1){
            question.setAnsweredornot(true);
        }

        if(A.getId()==selectedId){
            question.setPrevious_button_checkedid(selectedId);
            question.setUserAns(0);
        }
        else if(B.getId()==selectedId){
            question.setPrevious_button_checkedid(selectedId);
            question.setUserAns(1);
        }
        else if(C.getId()==selectedId){
            question.setPrevious_button_checkedid(selectedId);
            question.setUserAns(2);
        }
        else if(D.getId()==selectedId){
            question.setPrevious_button_checkedid(selectedId);
            question.setUserAns(3);
        }



    }

    public void nextfunction(){

        set_userans_ans_selectedid();
        if(position==questionlist.size()-1){
            Toast.makeText(getContext(), "This is last Question", Toast.LENGTH_SHORT).show();
        }
        else{
            position++;
            setView();
        }
        set_userans_ans_selectedid();
    }
    public void prevfunction(){

        set_userans_ans_selectedid();
        if(position==0){
            Toast.makeText(getContext(), "This is first question", Toast.LENGTH_SHORT).show();
        }
        else{
            position--;
            setView();
        }

    }
    public void submitfunction(){

        set_userans_ans_selectedid(); // for last question user click before submit

        Dialogfragment dialog=new Dialogfragment(this.questionlist);

        dialog.setTargetFragment(QuestionDetailScreen.this, 1);
        dialog.show(getFragmentManager(), "MyCustomDialog");

    }
    public void bookmarkfunction(){
        Question question = questionlist.get(position);
        if(question.getBookmarkedornot()){
            question.setBookmarkedornot(false);
        }
        else{
            question.setBookmarkedornot(true);
        }
    }
    interface Summmryscreenopen{
        void onclicksummry(List<Question>questionList);
    }


}