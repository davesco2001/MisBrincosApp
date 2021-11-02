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

public class ShoppingsAdapter extends RecyclerView.Adapter<ShoppingsAdapter.ViewHolderShoppings> {
    ArrayList<Shoppings> shoopings;
    private final ShoppingsAdapter.ListItemClick onClickListener;


public ShoppingsAdapter(ArrayList<Shoppings> shoopings, ShoppingsAdapter.ListItemClick listener) {
        this.shoopings = shoopings;
        this.onClickListener = listener;
        }

public interface ListItemClick {
    void onListItemClick(int clickedItem);
}

    @NonNull
    @Override
    public ViewHolderShoppings onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int item = R.layout.shopping_card;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean fast = false;
        View view = layoutInflater.inflate(item, parent, fast);
        ViewHolderShoppings viewHolderShoppings = new ViewHolderShoppings(view);
        return viewHolderShoppings;
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingsAdapter.ViewHolderShoppings holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return shoopings.size();
    }

public class ViewHolderShoppings extends RecyclerView.ViewHolder implements View.OnClickListener {
    CardView cardView;
    TextView idPackage;
    TextView cCStudent;
    ImageButton icon;

    public ViewHolderShoppings(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.shopping_card_id);
        idPackage = itemView.findViewById(R.id.packageTextShoppingId);
        cCStudent = itemView.findViewById(R.id.studentTextCc);
        icon = itemView.findViewById(R.id.iconShoppingCard);
        itemView.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    public void bind(int position) {
        idPackage.setText(""+shoopings.get(position).getIdPackage());
        cCStudent.setText(shoopings.get(position).getCcStudent());
        //int color = Color.rgb(123, 52, 164);
        icon.setEnabled(false);
        icon.setImageResource(R.drawable.ic_school_black_24dp);
    }

    @Override
    public void onClick(View v) {
        int clickedItem = getBindingAdapterPosition();
        onClickListener.onListItemClick(clickedItem);
    }

}
}
