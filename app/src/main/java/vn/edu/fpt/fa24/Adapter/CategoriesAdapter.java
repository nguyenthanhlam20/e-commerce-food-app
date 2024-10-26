package vn.edu.fpt.fa24.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.fpt.fa24.Models.CategoriesModel;
import vn.edu.fpt.fa24.R;
import vn.edu.fpt.fa24.Services.CategoryService;
import vn.edu.fpt.fa24.ViewAllProductsActivity;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.Viewholder> {
    ArrayList<CategoriesModel> categoriesList;
    Context mContext;
    CategoryService service;

    public CategoriesAdapter(ArrayList<CategoriesModel> categoriesList, Context context) {
        this.categoriesList = categoriesList;
        this.mContext = context;
        this.service = new CategoryService();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        CategoriesModel model = categoriesList.get(position);
        holder.categoryName.setText(model.getCategoryName());
        Picasso.get().load(model.getCategoryImage())
                .into(holder.categoryImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ViewAllProductsActivity.class);
            intent.putExtra("type", "category");
            intent.putExtra("category", model.getCategoryName());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        public TextView categoryName;
        public CircleImageView categoryImage;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            categoryName = (TextView) itemView.findViewById(R.id.categoryName);
            categoryImage = (CircleImageView) itemView.findViewById(R.id.categoryImage);
        }
    }
}
