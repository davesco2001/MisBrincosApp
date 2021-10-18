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

import java.util.ArrayList;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.ViewHolderStudents>{

    ArrayList<Student> students;
    private final StudentsAdapter.ListItemClick onClickListener;


    public  StudentsAdapter(ArrayList<Student> students, StudentsAdapter.ListItemClick listener){
        this.students = students;
        this.onClickListener = listener;
    }
    public interface ListItemClick{
        void onListItemClick(int clickedItem);
    }
    @NonNull
    @Override
    public ViewHolderStudents onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int item = R.layout.student_card;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean fast = false;
        View view = layoutInflater.inflate(item,parent,fast);
        ViewHolderStudents viewHolderStudents= new ViewHolderStudents(view);
        return viewHolderStudents;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolderStudents holder, int position) {
        holder.bind(position);
    }
    @Override
    public int getItemCount() {
        return students.size();
    }

    public class ViewHolderStudents extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        TextView studentsName;
        TextView cC;
        ImageButton icon;

        public ViewHolderStudents(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.student_card_id);
            studentsName = itemView.findViewById(R.id.studentTextName);
            cC= itemView.findViewById(R.id.studentTextCc);
            icon = itemView.findViewById(R.id.iconStudentCard);
            itemView.setOnClickListener(this);
        }

        @SuppressLint("ResourceAsColor")
        public void bind(int position) {
            studentsName.setText(students.get(position).getName());
            cC.setText(students.get(position).getCc());
            //int color = Color.rgb(123, 52, 164);
            icon.setEnabled(false);
            icon.setImageResource(R.drawable.ic_people_black_24dp);
        }
        @Override
        public void onClick(View v) {
            int clickedItem= getBindingAdapterPosition();
            onClickListener.onListItemClick(clickedItem);
        }

    }
}
