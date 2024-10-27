package vn.edu.fpt.fa24.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import vn.edu.fpt.fa24.Helpers.SessionHelper;
import vn.edu.fpt.fa24.ImageViewActivity;
import vn.edu.fpt.fa24.Listeners.CartListener;
import vn.edu.fpt.fa24.Models.ProductModel;
import vn.edu.fpt.fa24.R;
import vn.edu.fpt.fa24.Requests.CartRequest;
import vn.edu.fpt.fa24.Callbacks.ResponseCallBack;
import vn.edu.fpt.fa24.Services.CartService;
import vn.edu.fpt.fa24.Services.ProductService;

public class ProductInfoBottom extends BottomSheetDialogFragment {
    private final Context mContext;
    private final ProductModel trendingProduct;
    ImageView imageView;
    Button addToCart;
    TextView productName, productPrice, productDesc;
    ProductService productService;
    CartService cartService;
    SessionHelper session;
    private CartListener listener;

    private String userId;

    public ProductInfoBottom(Context mContext, ProductModel trendingProduct) {
        this.mContext = mContext;
        this.trendingProduct = trendingProduct;
    }

    public void notifyCartChanged(Context context) {
        Intent intent = new Intent(CartListener.ACTION_UPDATE_CART);
        context.sendBroadcast(intent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_info_bottom, container);

        imageView = view.findViewById(R.id.mainImage);
        productName =  view.findViewById(R.id.pName);
        productPrice =  view.findViewById(R.id.pPrice);
        productDesc =  view.findViewById(R.id.pDesc);
        addToCart =  view.findViewById(R.id.addToCart);

        productService = new ProductService();
        cartService = new CartService();
        session = new SessionHelper(requireActivity());
        userId = session.getUserId();

        setProduct();
        setListeners();

        return view;
    }

    private void setListeners() {
        addToCart.setOnClickListener(v -> {
            String productId = trendingProduct.getProductId();
            String price = trendingProduct.getUnitPrice();
            CartRequest request = new CartRequest(userId, productId, "1", price);

            cartService.addToCart(request, new ResponseCallBack<String>() {
                @Override
                public void onSuccess(String response) {
                    notifyCartChanged(mContext);
                    requireActivity().runOnUiThread(() -> Toast.makeText(mContext, "Added to cart", Toast.LENGTH_SHORT).show());
                }

                @Override
                public void onFailure(String error) {
                    requireActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Failed to add product to cart", Toast.LENGTH_SHORT).show());
                }
            });
        });
    }

    private void setProduct() {
        productName.setText(trendingProduct.getProductName());
        productPrice.setText(trendingProduct.getFormattedUnitPrice());
        productDesc.setText(trendingProduct.getDescription());

        Picasso.get().load(trendingProduct.getProductImage()).into(imageView);

        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ImageViewActivity.class);
            intent.putExtra("uri", trendingProduct.getProductImage());
            startActivity(intent);

        });
    }
}
