package vn.edu.fpt.fa24;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import vn.edu.fpt.fa24.Adapter.CartAdapter;
import vn.edu.fpt.fa24.Fragments.CheckoutInfoBottom;
import vn.edu.fpt.fa24.Helpers.SessionHelper;
import vn.edu.fpt.fa24.Listeners.CartListener;
import vn.edu.fpt.fa24.Models.Cart.CartModel;
import vn.edu.fpt.fa24.Models.ProductModel;
import vn.edu.fpt.fa24.Callbacks.ResponseCallBack;
import vn.edu.fpt.fa24.Services.CartService;
import vn.edu.fpt.fa24.databinding.ActivityCartBinding;

public class CartActivity extends AppCompatActivity {
    ActivityCartBinding binding;
    CartAdapter mAdapter;
    CartService cartService;
    SessionHelper sessionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartService = new CartService();
        sessionHelper = new SessionHelper(CartActivity.this);

        IntentFilter filter = new IntentFilter(CartListener.ACTION_UPDATE_CART);
        registerReceiver(dataChangedReceiver, filter);

        binding = DataBindingUtil.setContentView(CartActivity.this, R.layout.activity_cart);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CartActivity.this);
        binding.cartView.setLayoutManager(layoutManager);

        setCartItems();
    }

    private final BroadcastReceiver dataChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (CartListener.ACTION_UPDATE_CART.equals(intent.getAction())) {
                setCartItems();
            }
        }
    };

    private void setCartItems() {
        String userId = sessionHelper.getUserId();
        cartService.getCardByUserId(userId, new ResponseCallBack<CartModel>() {
            @Override
            public void onSuccess(CartModel response) {
                runOnUiThread(() -> {
                    ArrayList<ProductModel> products = response.getProducts();

                    mAdapter = new CartAdapter(CartActivity.this, response, getSupportFragmentManager());
                    binding.cartView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                    binding.checkout.setOnClickListener(v -> {
                        CheckoutInfoBottom bottomSheet = new CheckoutInfoBottom(CartActivity.this, response);
                        bottomSheet.show(getSupportFragmentManager(), "ModalBottomSheet");
                    });

                    Log.e("size", String.valueOf(products.size()));

                    if (products.size() == 0) {
                        binding.emptyCart.setVisibility(View.VISIBLE);
                        binding.checkout.setVisibility(View.GONE);
                    } else {
                        binding.emptyCart.setVisibility(View.GONE);
                        binding.checkout.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(() -> {
                    Log.e("get cart error", error);
                    binding.emptyCart.setVisibility(View.VISIBLE);
                    binding.checkout.setVisibility(View.GONE);
                    binding.cartView.setVisibility(View.GONE);
                });
            }
        });
    }
}