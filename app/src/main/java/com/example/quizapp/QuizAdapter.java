package com.example.quizapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {

    private List<Quiz> quizzes;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Quiz quiz);
    }

    public QuizAdapter(List<Quiz> quizzes, OnItemClickListener onItemClickListener) {
        this.quizzes = quizzes;
        this.onItemClickListener = onItemClickListener;

    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_item, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {
        Quiz quiz = quizzes.get(position);
        holder.bind(quiz, onItemClickListener);
    }


    @Override
    public int getItemCount() {

        return quizzes.size();
    }

    public class QuizViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView categoryTextView;


        public QuizViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);


        }



        public void bind(Quiz quiz, OnItemClickListener onItemClickListener) {

            titleTextView.setText(quiz.getTitle());
            if(quiz.getCategory().equals("Any Category")) {
                categoryTextView.setText("Multiple Categories");
            } else {
                categoryTextView.setText(quiz.getCategory());
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(quiz);
                }
            });
        }
    }
}

