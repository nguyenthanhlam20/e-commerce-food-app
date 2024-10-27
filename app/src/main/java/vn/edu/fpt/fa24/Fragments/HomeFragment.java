package vn.edu.fpt.fa24.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.edu.fpt.fa24.Adapter.AllProductsAdapter;
import vn.edu.fpt.fa24.Adapter.CategoriesAdapter;
import vn.edu.fpt.fa24.Adapter.TrendingRecyclerAdapter;
import vn.edu.fpt.fa24.Models.CategoryModel;
import vn.edu.fpt.fa24.Models.ProductModel;
import vn.edu.fpt.fa24.R;
import vn.edu.fpt.fa24.Callbacks.ResponseCallBack;
import vn.edu.fpt.fa24.Services.CategoryService;
import vn.edu.fpt.fa24.Services.ProductService;
import vn.edu.fpt.fa24.ViewAllProductsActivity;

public class HomeFragment extends Fragment {
    View mMainView;
    Button viewAllBtn;
    RecyclerView mTrendingView, mAllProductsView, mCategoriesView;
    ImageView bannerImage, centerBannerImage;
    TrendingRecyclerAdapter mAdapter;
    NestedScrollView nestedScrollView;
    AllProductsAdapter mAdapter2;
    CategoriesAdapter mAdapter3;
    CategoryService categoryService;
    ProductService productService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initialize() {
        mTrendingView = mMainView.findViewById(R.id.trendingView);
        mAllProductsView = mMainView.findViewById(R.id.allProducts);
        mCategoriesView = mMainView.findViewById(R.id.categoriesView);
        bannerImage = mMainView.findViewById(R.id.bannerHome);
        centerBannerImage = mMainView.findViewById(R.id.midBanner);
        viewAllBtn = mMainView.findViewById(R.id.viewAllBtn);
        nestedScrollView = mMainView.findViewById(R.id.nestedScroll);
        categoryService = new CategoryService();
        productService = new ProductService();
        centerBannerImage.setOnClickListener(v -> {

        });

        bannerImage.setOnClickListener(v -> {

        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_home, container, false);
        initialize();

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            getBanner();
            addCategories();
            getTrendingData();
            getAllProducts();
        }, 1000);

        viewAllBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ViewAllProductsActivity.class);
            intent.putExtra("type", "all");
            startActivity(intent);
        });

        return mMainView;
    }

    private void getBanner() {
        Picasso.get().load(R.drawable.poster1)
                .placeholder(R.drawable.img3)
                .into(bannerImage);
    }

    private void getAllProducts() {
        productService.getProducts(new ResponseCallBack<ArrayList<ProductModel>>() {
            @Override
            public void onSuccess(ArrayList<ProductModel> response) {
                requireActivity().runOnUiThread(() -> {
                    mAllProductsView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    mAdapter2 = new AllProductsAdapter(response, getContext(), requireActivity().getSupportFragmentManager());
                    mAllProductsView.setAdapter(mAdapter2);
                });
            }

            @Override
            public void onFailure(String error) {
                requireActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Failed to load categories", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void addCategories() {
        categoryService.getCategories(new ResponseCallBack<ArrayList<CategoryModel>>() {
            @Override
            public void onSuccess(ArrayList<CategoryModel> response) {
                requireActivity().runOnUiThread(() -> {
                    // Set up the RecyclerView and Adapter for horizontal scrolling
                    LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    mCategoriesView.setLayoutManager(linearLayoutManager2);
                    mCategoriesView.setHasFixedSize(true);  // Set fixed size for better performance

                    // Ensure RecyclerView handles its own scrolling if in a nested scroll container
                    mCategoriesView.setNestedScrollingEnabled(false);

                    mAdapter3 = new CategoriesAdapter(response, getContext());
                    mCategoriesView.setAdapter(mAdapter3);
                });
            }

            @Override
            public void onFailure(String error) {
                requireActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Failed to load categories", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void getTrendingData() {
        productService.getTrendingProducts(new ResponseCallBack<ArrayList<ProductModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(ArrayList<ProductModel> response) {
                requireActivity().runOnUiThread(() -> {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    mAdapter = new TrendingRecyclerAdapter(response, getContext(), requireActivity().getSupportFragmentManager());
                    mTrendingView.setLayoutManager(linearLayoutManager);
                    mTrendingView.setAdapter(mAdapter);

                    mAdapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onFailure(String error) {
                requireActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Failed to load categories", Toast.LENGTH_SHORT).show());
            }
        });
    }
}
