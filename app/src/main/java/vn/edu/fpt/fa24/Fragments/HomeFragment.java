package vn.edu.fpt.fa24.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.realm.mongodb.mongo.MongoDatabase;
import vn.edu.fpt.fa24.Adapter.AllProductsAdapter;
import vn.edu.fpt.fa24.Adapter.CategoriesAdapter;
import vn.edu.fpt.fa24.Adapter.RecentHistoryAdapter;
import vn.edu.fpt.fa24.Adapter.TrendingRecyclerAdapter;
import vn.edu.fpt.fa24.CustomDatabase;
import vn.edu.fpt.fa24.DatabaseHelper.DatabaseHelper;
import vn.edu.fpt.fa24.DatabaseHelper.ProductHistory;
import vn.edu.fpt.fa24.Interfaces.HistoryUpdated;
import vn.edu.fpt.fa24.Models.BannerModel;
import vn.edu.fpt.fa24.Models.CategoriesModel;
import vn.edu.fpt.fa24.Models.TrendingProducts;
import vn.edu.fpt.fa24.R;
import vn.edu.fpt.fa24.Services.Callbacks.ResponseCallBack;
import vn.edu.fpt.fa24.Services.CategoryService;
import vn.edu.fpt.fa24.ViewAllProductsActivity;

public class HomeFragment extends Fragment implements HistoryUpdated {
    ProductHistory productHistory;
    LinearLayout noHistoryImage;
    View mMainView;
    Button viewAllBtn;
    RecyclerView mTrendingView, mProductsAcrossView, mCategoriesView, mRecentView;
    MongoDatabase mongoDatabase;
    ImageView bannerImage, midbannerImage;
    TrendingRecyclerAdapter mAdapter;
    NestedScrollView nestedScrollView;
    public static HistoryUpdated historyUpdated;
    AllProductsAdapter mAdapter2;
    AllProductsAdapter mAdapter4;
    CategoriesAdapter mAdapter3;
    RecentHistoryAdapter mRecentAdapter;
    ArrayList<TrendingProducts> trendingList = new ArrayList<>();
    ArrayList<TrendingProducts> abannerHomellProducts = new ArrayList<>();
    ArrayList<TrendingProducts> locals = new ArrayList<>();
    ArrayList<TrendingProducts> recentHistory = new ArrayList<>();
    ArrayList<TrendingProducts> allProducts = new ArrayList<>();
    ArrayList<String> ranks = new ArrayList<>();
    ArrayList<BannerModel> banners = new ArrayList<>();
    Button clearHistory;

    CategoryService categoryService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initialize() {
        mTrendingView = (RecyclerView) mMainView.findViewById(R.id.trendingView);
        mProductsAcrossView = (RecyclerView) mMainView.findViewById(R.id.productsAcrossIndia);
        mCategoriesView = (RecyclerView) mMainView.findViewById(R.id.categoriesView);
        mRecentView = (RecyclerView) mMainView.findViewById(R.id.recentProduct);
        bannerImage = (ImageView) mMainView.findViewById(R.id.bannerHome);
        midbannerImage = (ImageView) mMainView.findViewById(R.id.midBanner);
        clearHistory = (Button) mMainView.findViewById(R.id.clearHistory);
        noHistoryImage = (LinearLayout) mMainView.findViewById(R.id.noHistory);
        viewAllBtn = (Button) mMainView.findViewById(R.id.viewAllBtn);
        nestedScrollView = (NestedScrollView) mMainView.findViewById(R.id.nestedScroll);
        historyUpdated = (HistoryUpdated) this;
        categoryService = new CategoryService();

        midbannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:macrocodes7400@gmail.com")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_SUBJECT, "here is my valueable suggestion for you.");
                if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        bannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:macrocodes7400@gmail.com")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_SUBJECT, "here is my valueable suggestion for you.");
                if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_home, container, false);
        initialize();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getBanner();
                addCategories();
                getTrendingData();
                getAllProducts();

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                mAdapter = new TrendingRecyclerAdapter(trendingList, getContext(), requireActivity().getSupportFragmentManager(), historyUpdated);
                mTrendingView.setLayoutManager(linearLayoutManager);
                mTrendingView.setAdapter(mAdapter);

                //products across india adapter
                mProductsAcrossView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                mAdapter2 = new AllProductsAdapter(allProducts, getContext(), requireActivity().getSupportFragmentManager(), historyUpdated);
                mProductsAcrossView.setAdapter(mAdapter2);

                //Recent Product History
                productHistory = new ProductHistory(getContext());
                recentHistory = productHistory.getAllData();
                DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

                mRecentAdapter = new RecentHistoryAdapter(recentHistory, databaseHelper, getContext(), getFragmentManager());
                LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(getContext());
                mRecentView.setLayoutManager(linearLayoutManager4);
                mRecentView.setAdapter(mRecentAdapter);

            }
        }, 1000);
        //trending


        if (recentHistory.size() == 0) {
            noHistoryImage.setVisibility(View.VISIBLE);
        } else {
            noHistoryImage.setVisibility(View.GONE);
        }

        clearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productHistory.deleteRow();
                recentHistory.clear();
                noHistoryImage.setVisibility(View.VISIBLE);
                mRecentAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Product view history cleared", Toast.LENGTH_SHORT).show();
            }
        });

        viewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewAllProductsActivity.class);
                intent.putExtra("type", "all");
                startActivity(intent);
            }
        });

        return mMainView;
    }

    private void getBanner() {
        Picasso.get().load(R.drawable.poster1)
                .placeholder(R.drawable.img3)
                .into(bannerImage);
    }

    private void getLocalProducts() {
        CustomDatabase customDatabase = new CustomDatabase();
//        for (QueryDocumentSnapshot datasnapshot : snapshots){
//            TrendingProducts trendingProducts1 = datasnapshot.toObject(TrendingProducts.class);
//            locals.add(trendingProducts1);
//            mAdapter4.notifyDataSetChanged();
//
//        }
    }

    private void getAllProducts() {
        allProducts.clear();
        CustomDatabase customDatabase = new CustomDatabase();
//        CollectionReference products  = customDatabase.getSettings().collection("Trending");
//        products.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot snapshots) {
//                for (QueryDocumentSnapshot datasnapshot : snapshots){
//                    TrendingProducts trendingProducts1 = datasnapshot.toObject(TrendingProducts.class);
//                    allProducts.add(trendingProducts1);
//                    mAdapter2.notifyDataSetChanged();
//
//                }
//
//            }
//        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                getLocalProducts();
//            }
//        });
    }

    private void addCategories() {
        categoryService.getCategories(new ResponseCallBack<ArrayList<CategoriesModel>>() {
            @Override
            public void onSuccess(ArrayList<CategoriesModel> response) {
                getActivity().runOnUiThread(() -> {
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
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getActivity(), "Failed to load categories", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void getTrendingData() {

//        trendingList.clear();
//        CustomDatabase customDatabase = new CustomDatabase() ;
//        CollectionReference products  = customDatabase.getSettings().collection("Trending");
//        products.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot snapshots) {
//                for (QueryDocumentSnapshot datasnapshot : snapshots){
//                    TrendingProducts trendingProducts1 = datasnapshot.toObject(TrendingProducts.class);
//                    trendingList.add(trendingProducts1);
//                    mAdapter.notifyDataSetChanged();
//
//                }
//
//            }
//        });
    }


    @Override
    public void getUpdateResult(boolean isUpdated) {
        if (isUpdated) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recentHistory.clear();
                    recentHistory.addAll(productHistory.getAllData());
                    mRecentAdapter.notifyDataSetChanged();
                    if (recentHistory.size() > 0)
                        noHistoryImage.setVisibility(View.GONE);
                }
            }, 1000);
        }
    }
}
