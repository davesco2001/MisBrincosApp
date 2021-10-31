package com.example.misbrincosapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.misbrincosapp.model.Student;
import com.example.misbrincosapp.model.Teacher;

import java.util.ArrayList;

public class TeachersAdapter extends RecyclerView.Adapter<TeachersAdapter.ViewHolderTeachers> {

    ArrayList<Teacher> teachers;
    private final TeachersAdapter.ListItemClick onClickListener;


    public TeachersAdapter(ArrayList<Teacher> teachers, TeachersAdapter.ListItemClick listener) {
        this.teachers = teachers;
        this.onClickListener = listener;
    }

    public interface ListItemClick {
        void onListItemClick(int clickedItem);
    }

    @NonNull
    @Override
    public ViewHolderTeachers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int item = R.layout.student_card;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean fast = false;
        View view = layoutInflater.inflate(item, parent, fast);
        ViewHolderTeachers viewHolderTeachers = new ViewHolderTeachers(view);
        return viewHolderTeachers;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTeachers holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    public class ViewHolderTeachers extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        TextView teachersName;
        TextView cC;
        ImageButton icon;

        public ViewHolderTeachers(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.teacher_card_id);
            teachersName = itemView.findViewById(R.id.teacherTextName);
            cC = itemView.findViewById(R.id.teacherTextCc);
            icon = itemView.findViewById(R.id.iconTeacherCard);
            itemView.setOnClickListener(this);
        }

        @SuppressLint("ResourceAsColor")
        public void bind(int position) {
            teachersName.setText(teachers.get(position).getName());
            cC.setText(teachers.get(position).getcC());
            //int color = Color.rgb(123, 52, 164);
            icon.setEnabled(false);
            icon.setImageResource(R.drawable.ic_people_black_24dp);
        }

        @Override
        public void onClick(View v) {
            int clickedItem = getBindingAdapterPosition();
            onClickListener.onListItemClick(clickedItem);
        }

    }

    {
    }
}
