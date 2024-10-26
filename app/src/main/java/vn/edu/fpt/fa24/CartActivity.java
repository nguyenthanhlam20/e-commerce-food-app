package vn.edu.fpt.fa24;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import vn.edu.fpt.fa24.Adapter.CartAdapter;
import vn.edu.fpt.fa24.DatabaseHelper.DatabaseHelper;
import vn.edu.fpt.fa24.Models.TrendingProducts;
import vn.edu.fpt.fa24.databinding.ActivityCartBinding;

public class CartActivity extends AppCompatActivity {
    ArrayList<TrendingProducts> arrayList = new ArrayList<>();
    ActivityCartBinding binding;
    CartAdapter mAdapter;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(this);
        arrayList = databaseHelper.getAllData();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_cart);

        mAdapter = new CartAdapter(this,arrayList,databaseHelper,getSupportFragmentManager());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.cartView.setLayoutManager(layoutManager);
        binding.cartView.setAdapter(mAdapter);


        mAdapter.notifyDataSetChanged();
        Log.e("size", String.valueOf(arrayList.size()));

        if (arrayList.size() == 0)
            binding.emptyCart.setVisibility(View.VISIBLE);
        else
            binding.emptyCart.setVisibility(View.GONE);

    }

}