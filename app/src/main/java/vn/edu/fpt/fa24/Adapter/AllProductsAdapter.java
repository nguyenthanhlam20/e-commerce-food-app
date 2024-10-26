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

import vn.edu.fpt.fa24.Fragments.ProductInfoBottom;
import vn.edu.fpt.fa24.Interfaces.HistoryUpdated;
import vn.edu.fpt.fa24.Models.TrendingProducts;
import vn.edu.fpt.fa24.R;

public class AllProductsAdapter extends RecyclerView.Adapter<AllProductsAdapter.Viewholder> {
    Context mContext;
    ArrayList<TrendingProducts> trendingList = new ArrayList<TrendingProducts>();
    FragmentManager supportFragmentManager;
    HistoryUpdated historyUpdated;

    public AllProductsAdapter(ArrayList<TrendingProducts> trendingList, Context context, FragmentManager supportFragmentManager, HistoryUpdated historyUpdated) {
        mContext = context;
        this.trendingList=trendingList;
        this.supportFragmentManager=supportFragmentManager;
        this.historyUpdated=historyUpdated;

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  Viewholder holder, int position) {

        TrendingProducts trendingProducts = trendingList.get(position);
        holder.productName.setText(trendingProducts.getName());
        holder.productPrice.setText(trendingProducts.getPrice());
        Picasso.get().load(trendingProducts.getImage())
                .into(holder.productImage);

        //opening bottom sheet
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductInfoBottom bottomSheet = new ProductInfoBottom(mContext, trendingProducts,historyUpdated);
                bottomSheet.show(supportFragmentManager, "ModalBottomSheet");
            }
        });
    }

    @Override
    public int getItemCount() {
        int limit = 4;
        if(trendingList.size() > limit){
            return limit;
        }
        else
        {
            return trendingList.size();
        }

    }

    public static class Viewholder extends RecyclerView.ViewHolder{

        ImageView productImage;
        TextView productName,productPrice;
        public Viewholder(@NonNull  View itemView) {
            super(itemView);
            productImage = (ImageView) itemView.findViewById(R.id.productImage);
            productName = (TextView) itemView.findViewById(R.id.productName);
            productPrice = (TextView) itemView.findViewById(R.id.productPrice);
        }
    }
}
