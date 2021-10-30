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

import com.example.misbrincosapp.model.Function;

import java.util.ArrayList;

public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.ViewHolderFunctions> {
    ArrayList<Function> functions;
    private final ListItemClick onClickListener;

    public  FunctionAdapter(ArrayList<Function> functions, ListItemClick listener){
        this.functions = functions;
        this.onClickListener = listener;
    }
    public interface ListItemClick{
        void onListItemClick(int clickedItem);
    }
    @NonNull
    @Override
    public ViewHolderFunctions onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int item = R.layout.function_card;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean fast = false;
        View view = layoutInflater.inflate(item,parent,fast);
        ViewHolderFunctions viewHolderFunctions= new ViewHolderFunctions(view);
        return viewHolderFunctions;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFunctions holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return functions.size();
    }

    public class ViewHolderFunctions extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cardView;
        TextView titleText;
        ImageButton icon;
        //Entities
        private static final int classValue = 1;
        private static final int sessionValue = 2;
        private static final int studentValue = 3;
        private static final int packageValue = 4;
        private static final int bookingValue = 5;
        private static final int teachersValue = 11;
        //CRUD
        private static final int createValue = 6;
        private static final int showValue = 7;
        private static final int editValue = 8;
        private static final int deleteValue = 9;
        //EXTERNAL FUNCTIONS
        private static final int addPackageToStudent = 10;

        public ViewHolderFunctions( @NonNull View itemView){
            super(itemView);
            cardView = itemView.findViewById(R.id.function_card_id);
            titleText = itemView.findViewById(R.id.textTitleCardFunction);
            icon = itemView.findViewById(R.id.iconCard);
            itemView.setOnClickListener(this);
        }
        @SuppressLint("ResourceAsColor")
        public void bind(int position){
            titleText.setText(functions.get(position).getName());
            int color=Color.rgb(123,52,164);
            switch (functions.get(position).getType()){
                case classValue:
                    icon.setEnabled(false);
                    icon.setImageResource(R.drawable.ic_class_black_24dp);
                    break;
                case sessionValue:
                    icon.setEnabled(false);
                    icon.setImageResource(R.drawable.ic_today_black_24dp);
                    break;
                case studentValue:
                    icon.setEnabled(false);
                    icon.setImageResource(R.drawable.ic_people_black_24dp);
                    break;
                case packageValue:
                    icon.setEnabled(false);
                    icon.setImageResource(R.drawable.ic_local_mall_black_24dp);
                    break;
                case bookingValue:
                    icon.setEnabled(false);
                    icon.setImageResource(R.drawable.ic_book_black_24dp);
                    break;
                case createValue:
                    icon.setEnabled(false);
                    icon.setImageResource(R.drawable.ic_add_circle_outline_black_24dp);
                    break;
                case showValue:
                    icon.setEnabled(false);
                    icon.setImageResource(R.drawable.ic_search_black_24dp);
                    break;
                case editValue:
                    icon.setEnabled(false);
                    icon.setImageResource(R.drawable.ic_edit_black_24dp);
                    break;
                case deleteValue:
                    icon.setEnabled(false);
                    icon.setImageResource(R.drawable.ic_delete_black_24dp);
                    break;
                case addPackageToStudent:
                    icon.setEnabled(true);
                    icon.setImageResource(R.drawable.ic_local_mall_black_24dp);
                    break;
                case teachersValue:
                    icon.setEnabled(true);
                    icon.setImageResource(R.drawable.ic_school_black_24dp);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + functions.get(position).getType());
            }
        }
        @Override
        public void onClick(View v) {
            int clickedItem= getBindingAdapterPosition();
            onClickListener.onListItemClick(clickedItem);
        }
    }
}

