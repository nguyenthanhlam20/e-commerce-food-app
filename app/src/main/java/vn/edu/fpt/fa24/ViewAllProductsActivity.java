package vn.edu.fpt.fa24;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;

import vn.edu.fpt.fa24.Adapter.ViewAllProductGridADapter;
import vn.edu.fpt.fa24.Models.ProductModel;
import vn.edu.fpt.fa24.Callbacks.ResponseCallBack;
import vn.edu.fpt.fa24.Services.ProductService;
import vn.edu.fpt.fa24.databinding.ActivityViewAllProductsBinding;

public class ViewAllProductsActivity extends AppCompatActivity {
    String type;
    ActivityViewAllProductsBinding binding;
    ViewAllProductGridADapter gridAdapter;
    ProductService productService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_all_products);
        type = getIntent().getStringExtra("type");
        productService = new ProductService();

        if (type.equalsIgnoreCase("all")) {
            getAllProducts();
        } else {
            String categoryId = getIntent().getStringExtra("categoryId");
            String categoryName = getIntent().getStringExtra("categoryName");
            getProductsByCategory(categoryId, categoryName);
        }
    }

    private void getProductsByCategory(String categoryId, String categoryName) {
        productService.getProductsByCategory(categoryId, new ResponseCallBack<ArrayList<ProductModel>>() {
            @Override
            public void onSuccess(ArrayList<ProductModel> response) {
                runOnUiThread(() -> {
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(ViewAllProductsActivity.this, 2);
                    gridAdapter = new ViewAllProductGridADapter(ViewAllProductsActivity.this, response);

                    binding.allProductsView.setLayoutManager(gridLayoutManager);
                    binding.allProductsView.setAdapter(gridAdapter);
                    binding.title.setText(categoryName);

                    gridAdapter.notifyDataSetChanged();

                });
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(() -> Toast.makeText(ViewAllProductsActivity.this, "Failed to load products", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void getAllProducts() {
        productService.getProducts(new ResponseCallBack<ArrayList<ProductModel>>() {
            @Override
            public void onSuccess(ArrayList<ProductModel> response) {
                runOnUiThread(() -> {
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(ViewAllProductsActivity.this, 2);
                    binding.allProductsView.setLayoutManager(gridLayoutManager);
                    gridAdapter = new ViewAllProductGridADapter(ViewAllProductsActivity.this, response);
                    binding.allProductsView.setAdapter(gridAdapter);
                    binding.title.setText("All Products");

                    gridAdapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(ViewAllProductsActivity.this, "Failed to load products", Toast.LENGTH_SHORT).show();
                });
            }
        });


    }

}