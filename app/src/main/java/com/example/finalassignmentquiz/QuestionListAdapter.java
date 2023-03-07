package com.example.finalassignmentquiz;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.QuestionListHolder> {

    List<Question>questionContainer;
    Context context;
    Onclicklistner onclicklistner;

    public QuestionListAdapter(List<Question>questionContainer,Context context){


            this.questionContainer = questionContainer;

        this.context=context;
        onclicklistner = (QuestionListAdapter.Onclicklistner) context;
    }

    @NonNull
    @Override
    public QuestionListAdapter.QuestionListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.questionlistview,parent,false);
        return new QuestionListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionListAdapter.QuestionListHolder holder, int position) {
        Question question = questionContainer.get(position);
        holder.answerdornot.setContentDescription("Question "+String.valueOf(position+1)+" "+holder.answerdornot.getText().toString());
        holder.bookmarkornot.setContentDescription("Question "+String.valueOf(position+1)+" "+holder.bookmarkornot.getText().toString());
        holder.question_title.setText(position+1+": "+question.getQuestion());
        if(question.getansweredornot()){
            holder.answerdornot.setText("Answered");
            holder.answerdornot.setTextColor(Color.rgb(0,252,0));
        }
        if(question.bookmarkedornot){
            holder.bookmarkornot.setText("BookMarked");
            holder.bookmarkornot.setTextColor(Color.rgb(0,252,0));
        }
        //Toast.makeText(context,String.valueOf(position),Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {

        //Toast.makeText(context,String.valueOf(questionContainer.size()),Toast.LENGTH_SHORT).show();
        return questionContainer.size();
    }

    public class QuestionListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView question_title , answerdornot , bookmarkornot;


        public QuestionListHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            question_title = itemView.findViewById(R.id.question);
            question_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onclicklistner.clickonquestion(getAdapterPosition(),questionContainer);
                }
            });
            answerdornot = itemView.findViewById(R.id.answered);
            bookmarkornot = itemView.findViewById(R.id.bookmark);


            bookmarkornot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   String bookmarktext = bookmarkornot.getText().toString();
                    Question question = questionContainer.get(getAdapterPosition());

                    if(bookmarktext.equals("Want To BookMark")){

                          question.setBookmarkedornot(true);
                          bookmarkornot.setText("BookMarked");
                          bookmarkornot.setTextColor(Color.rgb(0,252,0));
                    }
                    else{
                        question.setBookmarkedornot(false);
                        bookmarkornot.setText("Want To BookMark");
                        bookmarkornot.setTextColor(Color.rgb(0,0,255));
                    }
                }
            });

        }

        @Override
        public void onClick(View view) {

        }
    }
    interface Onclicklistner{
         void clickonquestion(int pos,List<Question>list);
    }
}
