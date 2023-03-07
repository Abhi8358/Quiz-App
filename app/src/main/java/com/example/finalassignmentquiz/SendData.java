package com.example.finalassignmentquiz;

import java.io.Serializable;
import java.util.*;

public class SendData implements Serializable{
    List<Question>questionList;
    int position;

    public SendData(List<Question>questionList , int position){
        this.questionList = questionList;
        this.position = position;
    }

    public void setQuestionList(List<Question>questionList){
        this.questionList = questionList;
    }
    public void setPosition(int position){
        this.position = position;
    }

    public int getPosition(){
        return position;
    }
    public List<Question> getQuestionList()
    {
        return questionList;
    }

}
