package vn.edu.fpt.fa24.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.edu.fpt.fa24.Fragments.ProductInfoBottom;
import vn.edu.fpt.fa24.Models.ProductModel;
import vn.edu.fpt.fa24.R;
import vn.edu.fpt.fa24.ViewAllProductsActivity;

public class ViewAllProductGridADapter extends RecyclerView.Adapter<ViewAllProductGridADapter.Viewholder> {
    Context context;
    ArrayList<ProductModel> locals = new ArrayList<>();

    public ViewAllProductGridADapter(ViewAllProductsActivity viewAllProductsActivity, ArrayList<ProductModel> locals) {
        context = viewAllProductsActivity;
        this.locals = locals;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.local_product_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        ProductModel model = locals.get(position);

        holder.productPrice.setText(model.getFormattedUnitPrice());
        holder.productName.setText(model.getProductName());
        holder.productDesc.setText(model.getDescription());
        Picasso.get().load(model.getProductImage())
                .into(holder.productImage);
        holder.itemView.setOnClickListener(v -> {
            FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
            ProductInfoBottom bottomSheet = new ProductInfoBottom(context, model);
            bottomSheet.show(manager, "ModalBottomSheet");
        });
    }

    @Override
    public int getItemCount() {
        return locals.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        TextView productName, productPrice, productDesc;
        ImageView productImage;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productDesc = itemView.findViewById(R.id.productDesc2);
            productPrice = itemView.findViewById(R.id.productPrice);
            productImage = itemView.findViewById(R.id.productImage);
        }
    }
}
