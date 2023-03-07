package com.example.finalassignmentquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SetupScreen.SetupScreen_interface ,QuestionListAdapter.Onclicklistner , QuestionDetailScreen.Summmryscreenopen , QuestionListScreen.QuestionListscreenInterface , Dialogfragment.Opensummryscreeninterface{

    final String TAG = "main";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate");
        if(savedInstanceState==null){
            replaceSetScreen(new SetupScreen());
        }




    }
    void replaceSetScreen(Fragment fragment){
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerMain,fragment);

        fragmentTransaction.commit();
    }

    // SetupScreen interface implementation
    @Override
    public void click() {
        replaceSetScreen(new QuestionListScreen());
        Toast.makeText(this,"click",Toast.LENGTH_SHORT).show();
    }

    // QuestionListAdapter interface implementation
    @Override
    public void clickonquestion(int position,List<Question>questiondetail) {

        SendData sendData = new SendData(questiondetail,position);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment questiondetailscreem = QuestionDetailScreen.newInstance(sendData);
        fragmentManager.beginTransaction().replace(R.id.containerMain,questiondetailscreem).addToBackStack(null).commit();
        //replaceSetScreen(new QuestionDetailScreen(position,questiondetail));
        //Toast.makeText(this,String.valueOf(position),Toast.LENGTH_SHORT).show();
    }

    // QuestionDetail interface implementation
    @Override
    public void onclicksummry(List<Question>questionList) {
        SendData sendData = new SendData(questionList,0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment summeryscreenobject = SummeryScreen.newInstance(sendData);

        fragmentManager.beginTransaction().replace(R.id.containerMain,summeryscreenobject).commit();

        //replaceSetScreen(new SummeryScreen());
    }

    //
    @Override
    public void submitbyListScreen(List<Question>questionList) {
        SendData sendData = new SendData(questionList,0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment summeryscreenobject = SummeryScreen.newInstance(sendData);

        fragmentManager.beginTransaction().replace(R.id.containerMain,summeryscreenobject).commit();
        //replaceSetScreen(new SummeryScreen());
    }

    @Override
    public void clicksummeryscreem(List<Question>questionList) {

        SendData sendData = new SendData(questionList,0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment summeryscreenobject = SummeryScreen.newInstance(sendData);

        fragmentManager.beginTransaction().replace(R.id.containerMain,summeryscreenobject).commit();
        //replaceSetScreen(new SummeryScreen());
    }

}