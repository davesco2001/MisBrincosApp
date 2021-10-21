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

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.ViewHolderBookings> {
    ArrayList<Booking> bookings;
    private final BookingsAdapter.ListItemClick onClickListener;

    public  BookingsAdapter(ArrayList<Booking> bookings, BookingsAdapter.ListItemClick listener){
        this.bookings = bookings;
        this.onClickListener = listener;
    }
    public interface ListItemClick{
        void onListItemClick(int clickedItem);
    }
    @NonNull
    @Override
    public ViewHolderBookings onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int item = R.layout.booking_card;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean fast = false;
        View view = layoutInflater.inflate(item,parent,fast);
        ViewHolderBookings  viewHolderBookings= new ViewHolderBookings(view);
        return viewHolderBookings;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolderBookings holder, int position) {
        holder.bind(position);
    }
    @Override
    public int getItemCount() {
        return bookings.size();
    }
    public class ViewHolderBookings extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        TextView bookingsName;
        TextView date;
        ImageButton icon;


        public ViewHolderBookings(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.session_card_id);
            bookingsName = itemView.findViewById(R.id.bookingTextLessonName);
            date = itemView.findViewById(R.id.bookingTextDate);
            icon = itemView.findViewById(R.id.iconBookingCard);
            itemView.setOnClickListener(this);
        }

        @SuppressLint("ResourceAsColor")
        public void bind(int position) {
            bookings.get(position).getSessionId();
            String name="";
            bookingsName.setText(name);
            String dateFormat = ""+bookings.get(position).getBookingDate(); //TODO : FORMAT DATE
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
