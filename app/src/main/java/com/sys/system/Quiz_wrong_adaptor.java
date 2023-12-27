package com.sys.system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Quiz_wrong_adaptor  extends RecyclerView.Adapter<Quiz_wrong_adaptor.ViewHolder>  {
    Context context;
    List<Quiz_wrong_constructor> constructorList ;

    public Quiz_wrong_adaptor(Context context, List<Quiz_wrong_constructor> constructorList) {
        this.context = context;
        this.constructorList = constructorList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.quiz_easy_cardview, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Quiz_wrong_constructor quizWrongConstructor = constructorList.get(position);
        holder.question.setText(quizWrongConstructor.getQuestion());
        holder.correct_answer.setText(quizWrongConstructor.getAnswer());
        holder.wrong_answer.setText(quizWrongConstructor.getCorrect_answer());
    }



    @Override
    public int getItemCount() {
        return constructorList.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView question,correct_answer,wrong_answer;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            question = view.findViewById(R.id.question);
            correct_answer = view.findViewById(R.id.correct_answer);
            wrong_answer = view.findViewById(R.id.wrong_answer);

        }
    }
}
