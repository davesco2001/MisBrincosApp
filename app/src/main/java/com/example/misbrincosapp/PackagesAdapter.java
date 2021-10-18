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

public class PackagesAdapter extends RecyclerView.Adapter<PackagesAdapter.ViewHolderPackages> {
    ArrayList<Package> packages;
    private final PackagesAdapter.ListItemClick onClickListener;


    public  PackagesAdapter(ArrayList<Package> packages, PackagesAdapter.ListItemClick listener){
        this.packages = packages;
        this.onClickListener = listener;
    }
    public interface ListItemClick{
        void onListItemClick(int clickedItem);
    }
    @NonNull
    @Override
    public ViewHolderPackages onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int item = R.layout.package_card;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean fast = false;
        View view = layoutInflater.inflate(item,parent,fast);
        ViewHolderPackages viewHolderPackages= new ViewHolderPackages(view);
        return viewHolderPackages;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolderPackages holder, int position) {
        holder.bind(position);
    }
    @Override
    public int getItemCount() {
        return packages.size();
    }

    public class ViewHolderPackages extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        TextView packagePlan;
        TextView packageId;
        ImageButton icon;

        public ViewHolderPackages(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.package_card_id);
            packagePlan = itemView.findViewById(R.id.packageTextPlan);
            packageId = itemView.findViewById(R.id.packageTextPackageId);
            icon = itemView.findViewById(R.id.iconPackageCard);
            itemView.setOnClickListener(this);
        }

        @SuppressLint("ResourceAsColor")
        public void bind(int position) {
            packagePlan.setText(packages.get(position).getPlan());
            packageId.setText(""+packages.get(position).getId());
            //int color = Color.rgb(123, 52, 164);
            icon.setEnabled(false);
            icon.setImageResource(R.drawable.ic_local_mall_black_24dp);
        }
        @Override
        public void onClick(View v) {
            int clickedItem= getBindingAdapterPosition();
            onClickListener.onListItemClick(clickedItem);
        }

    }
}
