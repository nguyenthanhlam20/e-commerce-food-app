package vn.edu.fpt.fa24.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.fpt.fa24.Helpers.DateHelper;
import vn.edu.fpt.fa24.Models.Order.OrderHistoryModel;
import vn.edu.fpt.fa24.R;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder> {

    private List<OrderHistoryModel> orderList;
    private OnOrderClickListener onOrderClickListener;
    private DateHelper dateHelper;

    // Interface for click handling
    public interface OnOrderClickListener {
        void onOrderClick(OrderHistoryModel order);
    }

    // Constructor with OnOrderClickListener parameter
    public OrderHistoryAdapter(List<OrderHistoryModel> orderList, OnOrderClickListener onOrderClickListener) {
        this.orderList = orderList;
        this.onOrderClickListener = onOrderClickListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);
        dateHelper = new DateHelper(parent.getContext());
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderHistoryModel order = orderList.get(position);
        holder.tvOrderDate.setText("Order Date: " + dateHelper.parseDate(order.getOrderDate()));
        holder.tvRequireDate.setText("Require Date: " + dateHelper.parseDate(order.getRequireDate()));
        holder.tvShipAddress.setText("Ship Address: " + order.getShipAddress());
        holder.tvShipPrice.setText("Total: $" + order.getShipPrice());
        holder.tvStatus.setText("Status: " + (order.isStatus() ? "Completed" : "Pending"));
        holder.tvOrderId.setText("Order Id: " + String.valueOf(order.getOrderID()));

        // Set click listener on the view holder
        holder.itemView.setOnClickListener(v -> onOrderClickListener.onOrderClick(order));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderDate, tvRequireDate, tvShipAddress, tvShipPrice, tvStatus, tvOrderId;

        OrderViewHolder(View itemView) {
            super(itemView);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvRequireDate = itemView.findViewById(R.id.tvRequireDate);
            tvShipAddress = itemView.findViewById(R.id.tvShipAddress);
            tvShipPrice = itemView.findViewById(R.id.tvShipPrice);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
        }
    }
}
