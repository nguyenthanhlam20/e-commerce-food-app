package vn.edu.fpt.fa24.Fragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Objects;

import vn.edu.fpt.fa24.Adapter.ViewAllProductsAdapter;
import vn.edu.fpt.fa24.Models.TrendingProducts;
import vn.edu.fpt.fa24.R;

public class SearchFragment extends Fragment {


    View mMainView;
    EditText searchBox;
    ImageButton searchBtn;
    RecyclerView searchView;
    ViewAllProductsAdapter mAdapter;
    ArrayList<TrendingProducts> arrayList = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_search, container, false);
        searchBox = (EditText) mMainView.findViewById(R.id.search);
        searchBtn = (ImageButton) mMainView.findViewById(R.id.searchBtn);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        searchView = (RecyclerView) mMainView.findViewById(R.id.searchView);
        searchView.setLayoutManager(linearLayoutManager);
        mAdapter = new ViewAllProductsAdapter(getActivity(),arrayList);
        searchView.setAdapter(mAdapter);

        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener(){

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if(arg1 == EditorInfo.IME_ACTION_SEARCH)
                {
                    // search pressed and perform your functionality.
                    search(searchBox.getText().toString());
                }
                return false;
            }

        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(searchBox.getText().toString());
            }
        });



        return mMainView;
    }
    private void search(String phrase){
//        TrendingProducts productModel = document.toObject(TrendingProducts.class);
//        arrayList.add(productModel);
//        mAdapter.notifyDataSetChanged();
    }
}