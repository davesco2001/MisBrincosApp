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

import java.util.ArrayList;

public class DeleteBookingsAdapter extends RecyclerView.Adapter<DeleteBookingsAdapter.ViewHolderDeleteBookings> {
    ArrayList<Booking> bookings;
    private final DeleteBookingsAdapter.ListItemClick onClickListener;

    public  DeleteBookingsAdapter(ArrayList<Booking> bookings, DeleteBookingsAdapter.ListItemClick listener){
        this.bookings = bookings;
        this.onClickListener = listener;
    }
    public interface ListItemClick{
        void buttonOnClick(View v, int clickedItem);
    }
    @NonNull
    @Override
    public ViewHolderDeleteBookings onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int item = R.layout.delete_booking_card;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean fast = false;
        View view = layoutInflater.inflate(item,parent,fast);
        ViewHolderDeleteBookings deleteBookings= new ViewHolderDeleteBookings(view);
        return deleteBookings;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolderDeleteBookings holder, int position) {
        holder.bind(position);
    }
    @Override
    public int getItemCount() {
        return bookings.size();
    }
    public class ViewHolderDeleteBookings extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        TextView sessionLessonName;
        TextView date;
        ImageButton icon;
        Button button;

        public ViewHolderDeleteBookings(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.delete_booking_card_id);
            sessionLessonName = itemView.findViewById(R.id.bookingDeleteSessionTextLessonName);
            date = itemView.findViewById(R.id.bookingDeleteSessionTextDate);
            icon = itemView.findViewById(R.id.iconBookingDeleteSessionCard);
            button=itemView.findViewById(R.id.buttonDeleteBookingSession);
        }

        @SuppressLint("ResourceAsColor")
        public void bind(int position) {
            int idSession = bookings.get(position).getSessionId();
            //Search in db the name of this id session
            String sessionName= "name";
            sessionLessonName.setText(sessionName);
            String dateFormat = ""+bookings.get(position).getBookingDate(); //TODO : FORMAT DATE
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
