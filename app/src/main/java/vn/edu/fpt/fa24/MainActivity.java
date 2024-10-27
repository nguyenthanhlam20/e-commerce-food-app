package vn.edu.fpt.fa24;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

import vn.edu.fpt.fa24.Adapter.HomePageFragmentAdapter;
import vn.edu.fpt.fa24.Callbacks.ResponseCallBack;
import vn.edu.fpt.fa24.Helpers.SessionHelper;
import vn.edu.fpt.fa24.Listeners.CartListener;
import vn.edu.fpt.fa24.Models.Cart.CartItemModel;
import vn.edu.fpt.fa24.Models.Cart.CartModel;
import vn.edu.fpt.fa24.Services.CartService;
import vn.edu.fpt.fa24.databinding.ActivityMainBinding;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {
    HomePageFragmentAdapter mSectionsPagerAdapter;
    ActivityMainBinding binding;
    private MenuItem prevMenuItem;
    CartService cartService;
    SessionHelper sessionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mSectionsPagerAdapter = new HomePageFragmentAdapter(getSupportFragmentManager());
        binding.viewpager.setAdapter(mSectionsPagerAdapter);
        binding.viewpager.setOffscreenPageLimit(0);
        binding.cartBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        });
        cartService = new CartService();
        sessionHelper = new SessionHelper(MainActivity.this);

        setCartItems();
        hideSplashView();
    }

    public void hideSplashView() {
        Handler handler = new Handler();
        handler.postDelayed(() -> binding.splashLayout.setVisibility(View.GONE), 1500);
        handleBottomNav();
    }

    private void setCartItems() {
        String userId = sessionHelper.getUserId();
        cartService.getCardByUserId(userId, new ResponseCallBack<CartModel>() {
            @Override
            public void onSuccess(CartModel response) {
                runOnUiThread(() -> {
                    List<CartItemModel> cartItems = response.getCartItems();
                    if (cartItems.size() == 0) {
                        binding.cartCount.setVisibility(View.GONE);
                    } else {
                        binding.cartCount.setText(String.valueOf(cartItems.size()));
                        binding.cartCount.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(() -> binding.cartCount.setVisibility(View.GONE));
            }
        });
    }

    private void handleBottomNav() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()) {
                case R.id.home:
                    binding.viewpager.setCurrentItem(0);
                    binding.title.setText("Home");
                    break;

                case R.id.profile:
                    binding.viewpager.setCurrentItem(2);
                    binding.title.setText("My Profile");
                    break;

            }
            return false;
        });
        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null)
                    prevMenuItem.setChecked(false);
                else
                    binding.bottomNavigation.getMenu().getItem(0).setChecked(false);

                binding.bottomNavigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = binding.bottomNavigation.getMenu().getItem(position);

                switch (prevMenuItem.getItemId()) {
                    case R.id.home:
                        binding.viewpager.setCurrentItem(0);
                        binding.title.setText("Home");
                        break;

                    case R.id.profile:
                        binding.viewpager.setCurrentItem(2);
                        binding.title.setText("My Profile");
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Register BroadcastReceiver
        IntentFilter filter = new IntentFilter(CartListener.ACTION_UPDATE_CART);
        registerReceiver(dataChangedReceiver, filter);
    }

    private final BroadcastReceiver dataChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (CartListener.ACTION_UPDATE_CART.equals(intent.getAction())) {
                // Implement action when get notified
                setCartItems();
            }
        }
    };
}