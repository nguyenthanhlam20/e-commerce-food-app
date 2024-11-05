package vn.edu.fpt.fa24;

import static androidx.databinding.DataBindingUtil.setContentView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import vn.edu.fpt.fa24.Adapter.OrderHistoryAdapter;
import vn.edu.fpt.fa24.Callbacks.ResponseCallBack;
import vn.edu.fpt.fa24.Helpers.JsonHelper;
import vn.edu.fpt.fa24.Helpers.SessionHelper;
import vn.edu.fpt.fa24.Models.Order.OrderHistoryModel;
import vn.edu.fpt.fa24.Services.OrderHistoryService;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderHistoryAdapter orderAdapter;
    private OrderHistoryService orderService;
    private SessionHelper sessionHelper;
    private JsonHelper jsonHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        recyclerView = findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sessionHelper = new SessionHelper(this);
        orderService = new OrderHistoryService();
        jsonHelper = new JsonHelper<OrderHistoryModel>();

        fetchOrderHistory();
    }

    private void fetchOrderHistory() {
        String userId = sessionHelper.getUserId();
        orderService.getOrderHistories(userId, new ResponseCallBack<List<OrderHistoryModel>>() {
            @Override
            public void onSuccess(List<OrderHistoryModel> items) {
                runOnUiThread(() -> {
                    orderAdapter = new OrderHistoryAdapter(items, order -> {
                        String orderJson = jsonHelper.toJson(order);  // Convert order to JSON

                        Intent intent = new Intent(OrderHistoryActivity.this, OrderHistoryDetailsActivity.class);
                        intent.putExtra("order", orderJson);  // Pass JSON string to next activity
                        startActivity(intent);
                    });

                    recyclerView.setAdapter(orderAdapter);

                    orderAdapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(() -> Toast.makeText(OrderHistoryActivity.this, "Failed to load order history: " + error, Toast.LENGTH_SHORT).show());
            }
        });
    }
}