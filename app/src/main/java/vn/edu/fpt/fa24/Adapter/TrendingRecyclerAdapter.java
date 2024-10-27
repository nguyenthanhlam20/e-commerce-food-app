package vn.edu.fpt.fa24.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.fpt.fa24.Fragments.ProductInfoBottom;
import vn.edu.fpt.fa24.Models.ProductModel;
import vn.edu.fpt.fa24.R;

public class TrendingRecyclerAdapter extends RecyclerView.Adapter<TrendingRecyclerAdapter.Viewholder> {
    Context mContext;
    ArrayList<ProductModel> trendingList;
    FragmentManager supportFragmentManager;

    public TrendingRecyclerAdapter(ArrayList<ProductModel> trendingList, Context context, FragmentManager supportFragmentManager) {
        mContext = context;
        this.trendingList = trendingList;
        this.supportFragmentManager = supportFragmentManager;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_products_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        ProductModel trendingProducts = trendingList.get(position);
        holder.productName.setText(trendingProducts.getProductName());
        holder.productPrice.setText(trendingProducts.getFormattedUnitPrice());
        Picasso.get().load(trendingProducts.getProductImage())
                .into(holder.productImage);


        if (position == 0) {
            holder.crownImage.setVisibility(View.VISIBLE);
        } else {
            holder.crownImage.setVisibility(View.GONE);
        }

        //opening bottom sheet
        holder.itemView.setOnClickListener(v -> {
            ProductInfoBottom bottomSheet = new ProductInfoBottom(mContext, trendingProducts);
            bottomSheet.show(supportFragmentManager, "ModalBottomSheet");
        });

    }

    @Override
    public int getItemCount() {
        return trendingList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        ImageView productImage;
        CircleImageView crownImage;
        TextView productName, productPrice;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            crownImage = itemView.findViewById(R.id.crown);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
        }
    }
}
