package com.example.finalassignmentquiz;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class QuestionListScreen extends Fragment {

    String TAG = "QuestionListScreen";
    View view;
    QuestionViewModel viewModel;
    private ProgressDialog loadingDialog;
    TextView textView;
    Button submit;
    QuestionListscreenInterface questionListscreenInterface;
    List<Question>questionList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        view = inflater.inflate(R.layout.fragment_question_list_screen, container, false);
        ViewModelProvider viewModelProvider = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()));

        viewModel = viewModelProvider.get(QuestionViewModel.class);
        questionListscreenInterface = (QuestionListscreenInterface) getActivity();
        textView = view.findViewById(R.id.timer);
        submit = view.findViewById(R.id.submit1);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialogfragment dialog=new Dialogfragment(QuestionViewModel.questionlist);

                dialog.setTargetFragment(QuestionListScreen.this, 1);
                dialog.show(getFragmentManager(), "MyCustomDialog");


            }
        });
        List<Question>list = new ArrayList<>();
        //handleQuestionList(list);
        if(QuestionViewModel.onquestionListSecreen && QuestionViewModel.questionlist!=null){
            handleQuestionList(QuestionViewModel.questionlist);
        }
        else{

            setUpLiveData();
        }


            startTimer();



        return view;
    }

    CountDownTimer cTimer = null;
    //start timer function
    void startTimer() {
        int millisinfuture = viewModel.counter*1000;

        cTimer = new CountDownTimer(millisinfuture, 1000) {
            public void onTick(long millisUntilFinished) {

                textView.setText(String.valueOf(((viewModel.counter)/60))+":"+String.valueOf((viewModel.counter)%60));
                viewModel.counter--;
            }
            public void onFinish() {
                textView.setText("Finish");
                questionListscreenInterface.submitbyListScreen(QuestionViewModel.questionlist);
            }
        };
        cTimer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        if(cTimer!=null){
            cTimer.cancel();
        }

        QuestionViewModel.onquestionListSecreen=true;

        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        if(cTimer!=null){
            cTimer.cancel();
        }

        QuestionViewModel.onquestionListSecreen=true;
        Log.d(TAG, "onStop: ");
    }



    private void setUpLiveData() {
        viewModel.getCountryLiveData().observe((LifecycleOwner) view.getContext(), questionModel -> handleQuestionList(questionModel));
        viewModel.getRequestStatusLiveData().observe((LifecycleOwner) view.getContext(), requestStatus -> handleRequestStatus(requestStatus));
    }

    private void handleQuestionList(List<Question> questionModel) {

        if(!QuestionViewModel.onquestionListSecreen){
            Collections.shuffle(questionModel);
            QuestionViewModel.questionlist = questionModel;
        }
           // to shuffle the list
        QuestionListAdapter adapter = new QuestionListAdapter(questionModel,getActivity());
        RecyclerView questionRecyclerView = view.findViewById(R.id.container1);
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        questionRecyclerView.setAdapter(adapter);

    }


    private void handleRequestStatus(QuestionViewModel.RequestStatus requestStatus) {
        switch (requestStatus) {
            case IN_PROGRESS:
                showSpinner();
                break;
            case SUCCEEDED:
                hideSpinner();
                break;
            case FAILED:
                showError();
                break;
        }
    }

    private void showSpinner() {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(getActivity());
            loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            loadingDialog.setTitle("Fetching Questions");
            loadingDialog.setMessage("Please wait...");
            loadingDialog.setIndeterminate(true);
            loadingDialog.setCanceledOnTouchOutside(false);
        }
        loadingDialog.show();
    }

    private void hideSpinner() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    private void showError() {
        hideSpinner();
        Toast.makeText(view.getContext(),"Could not fetch",Toast.LENGTH_SHORT).show();

    }

    private AlertDialog getFailureDialog() {
        return new AlertDialog.Builder(view.getContext())
                .setTitle("Question list request failed")
                .setMessage("Question list fetching is failed, do you want to retry?")
                .setPositiveButton("Retry", (dialog, which) -> {
                    dialog.dismiss();
                    viewModel.refetchquestions();
                })
                .setNegativeButton("Close app", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create();
    }
    interface QuestionListscreenInterface{
        public void submitbyListScreen(List<Question>questionList);
    }

}