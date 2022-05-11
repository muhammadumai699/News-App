package com.example.newsapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private ArrayList<NewsCategoryModel> arrayList;
    private Context context;
    private CategoryClickInterface categoryClickInterface;

    public CategoriesAdapter(ArrayList<NewsCategoryModel> arrayList, Context context, CategoryClickInterface categoryClickInterface) {
        this.arrayList = arrayList;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_categories,parent,false);
        return new CategoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        NewsCategoryModel newsCategoryModel = arrayList.get(position);
        holder.CategoryTV.setText(newsCategoryModel.getCategories());
        Picasso.get().load(newsCategoryModel.getCategoryImageUrl()).into(holder.CategoryIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClickInterface.onCategoryClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface CategoryClickInterface{
        void onCategoryClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView CategoryTV;
        private ImageView CategoryIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CategoryTV = itemView.findViewById(R.id.textCategory);
            CategoryIV = itemView.findViewById(R.id.imageCategory);
        }
    }
}
