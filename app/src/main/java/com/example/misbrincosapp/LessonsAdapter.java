package com.example.misbrincosapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.ViewHolderLessons> {
    ArrayList<Lesson> lessons;
    private final LessonsAdapter.ListItemClick onClickListener;


    public  LessonsAdapter(ArrayList<Lesson> lessons, LessonsAdapter.ListItemClick listener){
        this.lessons = lessons;
        this.onClickListener = listener;
    }
    public interface ListItemClick{
        void onListItemClick(int clickedItem);
    }
    @NonNull
    @Override
    public ViewHolderLessons onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int item = R.layout.lesson_card;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean fast = false;
        View view = layoutInflater.inflate(item,parent,fast);
        ViewHolderLessons viewHolderLessons= new ViewHolderLessons(view);
        return viewHolderLessons;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolderLessons holder, int position) {
        holder.bind(position);
    }
    @Override
    public int getItemCount() {
        return lessons.size();
    }


    public class ViewHolderLessons extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        TextView lessonName;
        TextView duration;
        ImageButton icon;

        public ViewHolderLessons(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.lesson_card_id);
            lessonName = itemView.findViewById(R.id.lessonTextName);
            duration = itemView.findViewById(R.id.lessonTextDuration);
            icon = itemView.findViewById(R.id.iconLessonCard);
            itemView.setOnClickListener(this);
        }

        @SuppressLint("ResourceAsColor")
        public void bind(int position) {
            lessonName.setText(lessons.get(position).getName());
            duration.setText(""+lessons.get(position).getDuration());
            //int color = Color.rgb(123, 52, 164);
            icon.setEnabled(false);
            icon.setImageResource(R.drawable.ic_class_black_24dp);
        }
        @Override
        public void onClick(View v) {
            int clickedItem= getBindingAdapterPosition();
            onClickListener.onListItemClick(clickedItem);
        }

    }
}
