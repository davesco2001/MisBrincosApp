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

public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.ViewHolderSessions>{

    ArrayList<Session> sessions;
    private final SessionsAdapter.ListItemClick onClickListener;


    public  SessionsAdapter(ArrayList<Session> sessions, SessionsAdapter.ListItemClick listener){
        this.sessions = sessions;
        this.onClickListener = listener;
    }
    public interface ListItemClick{
        void onListItemClick(int clickedItem);
    }
    @NonNull
    @Override
    public ViewHolderSessions onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int item = R.layout.session_card;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean fast = false;
        View view = layoutInflater.inflate(item,parent,fast);
        ViewHolderSessions viewHolderSessions= new ViewHolderSessions(view);
        return viewHolderSessions;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolderSessions holder, int position) {
        holder.bind(position);
    }
    @Override
    public int getItemCount() {
        return sessions.size();
    }
    public class ViewHolderSessions extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        TextView sessionLessonName;
        TextView date;
        ImageButton icon;

        public ViewHolderSessions(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.session_card_id);
            sessionLessonName = itemView.findViewById(R.id.sessionLessonNameTextCard);
            date = itemView.findViewById(R.id.sessionTextDate);
            icon = itemView.findViewById(R.id.iconSessionCard);
            itemView.setOnClickListener(this);
        }

        @SuppressLint("ResourceAsColor")
        public void bind(int position) {
            sessionLessonName.setText(sessions.get(position).getLessonName());
            String dateFormat = ""+sessions.get(position).getDate(); //TODO : FORMAT DATE
            date.setText(dateFormat);
            //int color = Color.rgb(123, 52, 164);
            icon.setEnabled(false);
            icon.setImageResource(R.drawable.ic_today_black_24dp);
        }
        @Override
        public void onClick(View v) {
            int clickedItem= getBindingAdapterPosition();
            onClickListener.onListItemClick(clickedItem);
        }

    }
}
