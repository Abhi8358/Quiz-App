package com.example.finalassignmentquiz;

import java.util.ArrayList;

public class Question {
    int id;
    String question;
    ArrayList<String> options;
    int correctAns;
    int previous_button_checkedid=-1;
    int userAns = -1;
    boolean bookmarkedornot =false;
    boolean answeredornot = false;

    public Question(int id,String question , ArrayList<String>options,int correctAns){
        this.id=id;
        this.question=question;
        this.options =options;
        this.correctAns = correctAns;
    }

    public int getId(){
        return id;
    }

    public String getQuestion(){
        return question;
    }

    public  ArrayList<String> getOptions(){
        return options;
    }

    public int getCorrectAns(){
        return correctAns;
    }

    public int getPrevious_button_checkedid(){
        return previous_button_checkedid;
    }

    public void setPrevious_button_checkedid(int previous_button_checkedid){
        this.previous_button_checkedid = previous_button_checkedid;

    }

    public void setUserAns(int userAns){
        this.userAns = userAns;
    }

    public int getUserAns(){
        return userAns;
    }

    public void setBookmarkedornot(boolean bookmarkedornot){
        this.bookmarkedornot = bookmarkedornot;
    }
    public void setAnsweredornot(boolean answeredornot){
        this.answeredornot = answeredornot;
    }

    public boolean getBookmarkedornot(){
        return bookmarkedornot;
    }
    public boolean getansweredornot(){
        return answeredornot;
    }
}
