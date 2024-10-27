package vn.edu.fpt.fa24.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import vn.edu.fpt.fa24.CartActivity;
import vn.edu.fpt.fa24.Fragments.ProductInfoBottom;
import vn.edu.fpt.fa24.Helpers.SessionHelper;
import vn.edu.fpt.fa24.Listeners.CartListener;
import vn.edu.fpt.fa24.Models.Cart.CartItemModel;
import vn.edu.fpt.fa24.Models.Cart.CartModel;
import vn.edu.fpt.fa24.Models.ProductModel;
import vn.edu.fpt.fa24.R;
import vn.edu.fpt.fa24.Requests.CartRequest;
import vn.edu.fpt.fa24.Requests.DeleteFromCartRequest;
import vn.edu.fpt.fa24.Callbacks.ResponseCallBack;
import vn.edu.fpt.fa24.Services.CartService;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholder> {
    CartActivity mContext;
    FragmentManager fragmentManager;
    CartModel cart;
    CartService cartService;
    SessionHelper session;
    private String userId;

    public CartAdapter(CartActivity cartActivity, CartModel cart, FragmentManager fragmentManager) {
        mContext = cartActivity;
        this.fragmentManager = fragmentManager;
        this.cart = cart;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false);
        cartService = new CartService();
        session = new SessionHelper(mContext);
        userId = session.getUserId();

        return new Viewholder(view);
    }

    public void notifyCartChanged(Context context) {
        Intent intent = new Intent(CartListener.ACTION_UPDATE_CART);
        context.sendBroadcast(intent);
    }


    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        CartItemModel cartItem = cart.getCartItems().get(position);
        ProductModel product = cartItem.getProducts();
        String productId = product.getProductId();
        String price = product.getUnitPrice();
        int quantity = cartItem.getQuantity();
        int cardId = cart.getCartId();

        holder.pName.setText(product.getProductName());
        holder.pPrice.setText(product.getFormattedUnitPrice());
        holder.pDesc.setText(product.getDescription());
        holder.pQuantity.setText("Quantity: " + quantity);
        Picasso.get().load(product.getProductImage()).into(holder.pImage);

        holder.plusBtn.setOnClickListener(v -> {
            CartRequest request = new CartRequest(userId, productId, "1", price);
            cartService.addToCart(request, new ResponseCallBack<String>() {
                @Override
                public void onSuccess(String response) {
                    notifyCartChanged(mContext);
                    mContext.runOnUiThread(() -> Toast.makeText(mContext, "Updated product quantity successfully", Toast.LENGTH_SHORT).show());
                }

                @Override
                public void onFailure(String error) {
                    mContext.runOnUiThread(() -> Toast.makeText(mContext, "Failed to update product quantity", Toast.LENGTH_SHORT).show());
                }
            });
        });
        holder.minusBtn.setOnClickListener(v -> {
            int newQuantity = quantity - 1;
            if (newQuantity > 0) {
                CartRequest request = new CartRequest(userId, productId, String.valueOf(newQuantity), price);
                cartService.updateCart(cardId, request, new ResponseCallBack<String>() {
                    @Override
                    public void onSuccess(String response) {
                        notifyCartChanged(mContext);
                        mContext.runOnUiThread(() -> Toast.makeText(mContext, "Updated product quantity successfully", Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onFailure(String error) {
                        mContext.runOnUiThread(() -> Toast.makeText(mContext, "Failed to update product quantity", Toast.LENGTH_SHORT).show());
                    }
                });
            }
        });
        holder.trashBtn.setOnClickListener(v -> {
            DeleteFromCartRequest deleteRequest = new DeleteFromCartRequest(userId, productId);
            cartService.deleteFromCart(deleteRequest, new ResponseCallBack<String>() {
                @Override
                public void onSuccess(String response) {
                    notifyCartChanged(mContext);
                    mContext.runOnUiThread(() -> Toast.makeText(mContext, "Removed from cart", Toast.LENGTH_SHORT).show());
                }

                @Override
                public void onFailure(String error) {
                    mContext.runOnUiThread(() -> Toast.makeText(mContext, "Failed to remove product from cart", Toast.LENGTH_SHORT).show());
                }
            });
        });

        holder.itemView.setOnClickListener(v -> {
            ProductInfoBottom bottomSheet = new ProductInfoBottom(mContext, product);
            bottomSheet.show(fragmentManager, "ModalBottomSheet");
        });

    }

    @Override
    public int getItemCount() {
        return cart.getProducts().size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        private TextView pName, pDesc, pPrice, pQuantity;
        private ImageView pImage, minusBtn, plusBtn, trashBtn;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            pName = itemView.findViewById(R.id.pName);
            pPrice = itemView.findViewById(R.id.pPrice);
            pDesc = itemView.findViewById(R.id.pDesc);
            pImage = itemView.findViewById(R.id.pImage);
            pQuantity = itemView.findViewById(R.id.pQuantity);
            plusBtn = itemView.findViewById(R.id.plusBtn);
            trashBtn = itemView.findViewById(R.id.trashBtn);
            minusBtn = itemView.findViewById(R.id.minusBtn);
        }
    }
}
