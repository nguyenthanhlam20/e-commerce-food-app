package vn.edu.fpt.fa24;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import vn.edu.fpt.fa24.Adapter.ViewAllProductGridADapter;
import vn.edu.fpt.fa24.Adapter.ViewAllProductsAdapter;
import vn.edu.fpt.fa24.Models.TrendingProducts;
import vn.edu.fpt.fa24.databinding.ActivityViewAllProductsBinding;

public class ViewAllProductsActivity extends AppCompatActivity {

    String type;
    int limit =10;
    ActivityViewAllProductsBinding binding;
    private boolean isScrolling = false;
    ArrayList<TrendingProducts> products = new ArrayList<>();
    ArrayList<TrendingProducts> locals = new ArrayList<>();
    ArrayList<TrendingProducts> categoryList = new ArrayList<>();
    private boolean isLastItemReached = false;
    ViewAllProductsAdapter productAdapter;
    ViewAllProductGridADapter gridADapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_all_products);


        type = getIntent().getStringExtra("type");
        if (type.equalsIgnoreCase("all")){

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            binding.allProductsView.setLayoutManager(linearLayoutManager);
            getLocalsProducts();
            productAdapter = new ViewAllProductsAdapter(this,products);
            binding.allProductsView.setAdapter(productAdapter);
            binding.title.setText("Handmade with ❤");

        }else if (type.equalsIgnoreCase("local")){
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
            binding.allProductsView.setLayoutManager(gridLayoutManager);
            locals = getIntent().getParcelableArrayListExtra("list");
            gridADapter = new ViewAllProductGridADapter(this,locals);
            binding.allProductsView.setAdapter(gridADapter);
            binding.title.setText("Support the locals ❤");
        }
        else{
            String category = getIntent().getStringExtra("category");
            getCategoryProducts(category);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
            binding.allProductsView.setLayoutManager(gridLayoutManager);
            gridADapter = new ViewAllProductGridADapter(this,categoryList);
            binding.allProductsView.setAdapter(gridADapter);

            binding.title.setText(category+" ❤");
        }


    }


    private void getCategoryProducts(String category){

//        for (DocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
//
//            TrendingProducts productModel = document.toObject(TrendingProducts.class);
//            categoryList.add(productModel);
//
//
//        }
//        gridADapter.notifyDataSetChanged();

    }
    private void getLocalsProducts() {
//                    TrendingProducts productModel = document.toObject(TrendingProducts.class);
//                    products.add(productModel);
//                    assert productModel != null;

            productAdapter.notifyDataSetChanged();

            RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                        isScrolling = true;
//                                binding.progressBar.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
                    int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();

                    if (isScrolling && (firstVisibleItemPosition + visibleItemCount == totalItemCount) && !isLastItemReached) {
                        isScrolling = false;
                        binding.progressBar.setVisibility(View.GONE);

//                        Query nextQuery = productsRef.orderBy("name", Query.Direction.ASCENDING)
//                                .startAfter(lastVisible).limit(limit);
//                        for (DocumentSnapshot d : Objects.requireNonNull(t.getResult())) {
//                            TrendingProducts productModel = d.toObject(TrendingProducts.class);
//                            products.add(productModel);
//                        }
//                        productAdapter.notifyDataSetChanged();
//                        if(t.getResult().size()!=0)
//                            lastVisible = t.getResult().getDocuments().get(t.getResult().size() - 1);
//
//                        if (t.getResult().size() < limit) {
//                            isLastItemReached = true;
//                            binding.progressBar.setVisibility(View.GONE);
//                        }
                    }
                }
            };
            binding.allProductsView.addOnScrollListener(onScrollListener);

    }


}