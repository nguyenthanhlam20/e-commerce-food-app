package vn.edu.fpt.fa24;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import java.util.HashMap;

import vn.edu.fpt.fa24.Models.TrendingProducts;
import vn.edu.fpt.fa24.databinding.ActivityReportBinding;

public class ReportActivity extends AppCompatActivity {
    TrendingProducts products;
    ActivityReportBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_report);
        products = getIntent().getParcelableExtra("product");


        binding.pName.setText(products.getName());


        binding.reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (binding.emailAddress.getText().toString().isEmpty()){
//                    binding.emailError.setVisibility(View.VISIBLE);
//                }else{
//                    if(!binding.issue.getText().toString().isEmpty()){
//                        binding.emailError.setVisibility(View.GONE);
//                        HashMap<String,Object>map = new HashMap<>();
//                        map.put("id",products.getId());
//                        map.put("name",products.getName());
//                        map.put("amazon",products.getAmazon());
//                        map.put("flipkart",products.getFlipkart());
//                        map.put("userEmail",binding.emailAddress.getText().toString());
//                        map.put("solved","false");
//                        map.put("issue",binding.issue.getText().toString());
//
//                        CustomDatabase customDatabase = new CustomDatabase();
//                        CollectionReference usersRef = customDatabase.getSettings().collection("reports");
//                        usersRef.document(products.getId()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//
//                                finish();
//                                Toast.makeText(ReportActivity.this, "Reported", Toast.LENGTH_SHORT).show();
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull  Exception e) {
//                                Toast.makeText(ReportActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                }
            }
        });

    }
}