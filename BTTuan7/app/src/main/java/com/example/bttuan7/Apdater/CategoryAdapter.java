package com.example.bttuan7.Apdater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bttuan7.Model.Category;
import com.example.bttuan7.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    Context context;
    List<Category>array;

    public CategoryAdapter(Context context, List<Category> array) {
        this.context = context;
        this.array = array;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category category=array.get(position);
        holder.tenSp.setText(category.getName());
        Glide.with(context)
                .load(category.getImages())
                .into(holder.images);
    }

    @Override
    public int getItemCount() {
        return array==null ? 0:array.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView images;
        public TextView tenSp;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            images=(ImageView)itemView.findViewById(R.id.image_cate);
            tenSp=(TextView) itemView.findViewById(R.id.tvNameCategory);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Xử lý người dùng ấn chọn item holder trong MyviewHolder
                    Toast.makeText(context, "Bạn đã chọn category " + tenSp.getText().toString(), Toast.LENGTH_SHORT).show();                }
            });
        }
    }
}
