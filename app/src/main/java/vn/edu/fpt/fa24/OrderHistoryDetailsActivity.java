package vn.edu.fpt.fa24;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import vn.edu.fpt.fa24.Adapter.OrderHistoryDetailsAdapter;
import vn.edu.fpt.fa24.Helpers.DateHelper;
import vn.edu.fpt.fa24.Helpers.JsonHelper;
import vn.edu.fpt.fa24.Models.Order.OrderDetailsModel;
import vn.edu.fpt.fa24.Models.Order.OrderHistoryModel;

public class OrderHistoryDetailsActivity extends AppCompatActivity {

    private TextView tvOrderDate, tvRequireDate, tvShipAddress, tvShipPrice, tvStatus, tvOrderId;
    private RecyclerView recyclerViewOrderDetails;
    private OrderHistoryDetailsAdapter orderDetailsAdapter;
    private JsonHelper<OrderHistoryModel> jsonHelper;
    private DateHelper dateHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_details);

        tvOrderDate = findViewById(R.id.tvOrderDate);
        tvRequireDate = findViewById(R.id.tvRequireDate);
        tvShipAddress = findViewById(R.id.tvShipAddress);
        tvShipPrice = findViewById(R.id.tvShipPrice);
        tvStatus = findViewById(R.id.tvStatus);
        tvOrderId = findViewById(R.id.tvOrderId);
        recyclerViewOrderDetails = findViewById(R.id.recyclerViewOrderDetails);

        jsonHelper = new JsonHelper();
        dateHelper = new DateHelper(this);

        String orderJson = getIntent().getStringExtra("order");
        Log.e("orderJson", orderJson);
        OrderHistoryModel order = jsonHelper.parseRaw(orderJson, OrderHistoryModel.class);

        if (order != null) {
            tvOrderDate.setText("Order Date: " + dateHelper.parseDate(order.getOrderDate()));
            tvRequireDate.setText("Require Date: " + dateHelper.parseDate(order.getRequireDate()));
            tvShipAddress.setText("Ship Address: " + order.getShipAddress());
            tvShipPrice.setText("Total: $" + order.getShipPrice());
            tvStatus.setText("Status: " + (order.isStatus() ? "Completed" : "Pending"));
            tvOrderId.setText("Order Id: " + String.valueOf(order.getOrderID()));

            List<OrderDetailsModel> orderDetails = order.getOrderDetails();
            orderDetailsAdapter = new OrderHistoryDetailsAdapter(orderDetails);
            recyclerViewOrderDetails.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewOrderDetails.setAdapter(orderDetailsAdapter);
        }
    }
}