package com.example.misbrincosapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.misbrincosapp.model.Session;

import java.util.ArrayList;
    public class BookingSessionsAdapter extends RecyclerView.Adapter<BookingSessionsAdapter.ViewHolderBookingSessions>{

        ArrayList<Session> sessions;
        private final BookingSessionsAdapter.ListItemClick onClickListener;


        public  BookingSessionsAdapter(ArrayList<Session> sessions, BookingSessionsAdapter.ListItemClick listener){
            this.sessions = sessions;
            this.onClickListener = listener;
        }
        public interface ListItemClick{
            void buttonOnClick(View v, int clickedItem);
        }
        @NonNull
        @Override
        public ViewHolderBookingSessions onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            int item = R.layout.booking_sessions_card;
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            boolean fast = false;
            View view = layoutInflater.inflate(item,parent,fast);
            ViewHolderBookingSessions bookingSessions= new ViewHolderBookingSessions(view);
            return bookingSessions;
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolderBookingSessions holder, int position) {
            holder.bind(position);
        }
        @Override
        public int getItemCount() {
            return sessions.size();
        }
        public class ViewHolderBookingSessions extends RecyclerView.ViewHolder implements View.OnClickListener {
            CardView cardView;
            TextView sessionLessonName;
            TextView date;
            ImageButton icon;
            Button button;

            public ViewHolderBookingSessions(@NonNull View itemView) {
                super(itemView);
                cardView = itemView.findViewById(R.id.booking_session_card_id);
                sessionLessonName = itemView.findViewById(R.id.bookingSessionTextLessonName);
                date = itemView.findViewById(R.id.bookingSessionTextDate);
                icon = itemView.findViewById(R.id.iconBookingSessionCard);
                button=itemView.findViewById(R.id.buttonBookingSession);
            }

            @SuppressLint("ResourceAsColor")
            public void bind(int position) {
                sessionLessonName.setText(sessions.get(position).getLessonName());
                String dateFormat = ""+sessions.get(position).getDate(); //TODO : FORMAT DATE
                date.setText(dateFormat);
                //int color = Color.rgb(123, 52, 164);
                icon.setEnabled(false);
                icon.setImageResource(R.drawable.ic_today_black_24dp);
                button.setOnClickListener(this);
            }
            @Override
            public void onClick(View v) {
                int clickedItem= getBindingAdapterPosition();
                onClickListener.buttonOnClick(v, clickedItem);
            }

        }
    }

